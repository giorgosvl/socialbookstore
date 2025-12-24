package myy803.socialbookstore.ServicesTestSuite;

import myy803.socialbookstore.datamodel.*;
import myy803.socialbookstore.formsdata.UserProfileDto;
import myy803.socialbookstore.mappers.*;
import myy803.socialbookstore.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BookCategoryMapper bookCategoryMapper;

    @Mock
    private UserProfileMapper userProfileMapper;

    @Mock
    private BookAuthorMapper bookAuthorMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("plainPassword");

        when(bCryptPasswordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        userService.saveUser(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(userMapper, times(1)).save(user);
    }

    @Test
    void testIsUserPresent_UserExists() {
        User user = new User();
        user.setUsername("testUser");

        when(userMapper.findByUsername("testUser")).thenReturn(Optional.of(user));

        assertTrue(userService.isUserPresent(user));
    }

    @Test
    void testIsUserPresent_UserNotExists() {
        User user = new User();
        user.setUsername("testUser");

        when(userMapper.findByUsername("testUser")).thenReturn(Optional.empty());

        assertFalse(userService.isUserPresent(user));
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        User user = new User();
        user.setUsername("testUser");

        when(userMapper.findByUsername("testUser")).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("testUser");

        assertEquals(user, result);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userMapper.findByUsername("unknownUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("unknownUser"));
    }

    @Test
    void testRetrieveCategories() {
        List<BookCategory> categories = Arrays.asList(new BookCategory(), new BookCategory());
        when(bookCategoryMapper.findAll()).thenReturn(categories);

        List<BookCategory> result = userService.retrieveCategories();

        assertEquals(2, result.size());
        verify(bookCategoryMapper, times(1)).findAll();
    }
    @Test
    void testRetrieveUserProfile_UserExists() {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername("testUser");
        UserProfileDto userProfileDto = userProfile.buildProfileDto(); // Call the real method
        when(userProfileMapper.findById("testUser")).thenReturn(Optional.of(userProfile));

        UserProfileDto result = userService.retrieveUserProfile("testUser");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        verify(userProfileMapper, times(1)).findById("testUser");
    }


    @Test
    void testRetrieveUserProfile_UserNotExists() {
        when(userProfileMapper.findById("unknownUser")).thenReturn(Optional.empty());

        UserProfileDto result = userService.retrieveUserProfile("unknownUser");

        assertNotNull(result);
        assertEquals("unknownUser", result.getUsername());
    }

    @Test
    void testSaveUserProfile_UserExists() {
        UserProfile userProfile = new UserProfile();
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername("testUser");

        when(userProfileMapper.findById("testUser")).thenReturn(Optional.of(userProfile));

        userService.saveUserProfile(userProfileDto);

        verify(userProfileMapper, times(1)).save(userProfile);
    }

    @Test
    void testSaveUserProfile_UserNotExists() {
        UserProfile userProfile = new UserProfile();
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername("newUser");

        when(userProfileMapper.findById("newUser")).thenReturn(Optional.empty());

        userService.saveUserProfile(userProfileDto);

        verify(userProfileMapper, times(1)).save(any(UserProfile.class));
    }

}
