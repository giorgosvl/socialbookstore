package myy803.socialbookstore.ServicesTestSuite;

import myy803.socialbookstore.services.*;
import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.UserProfileDto;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.mappers.BookMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRequestsServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private BookCategoryMapper bookCategoryMapper;

    @Mock
    private UserProfileMapper userProfileMapper;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private BookRequestsService bookRequestsService;

    private UserProfile mockUserProfile;
    private Book mockBook;

    @BeforeEach
    void setUp() {
        mockUserProfile = mock(UserProfile.class);
        mockBook = mock(Book.class);
    }

    @Test
    void testAddRequestingUser_Success() {
        int bookId = 1;
        String username = "testUser";

        when(bookMapper.findById(bookId)).thenReturn(Optional.of(mockBook));
        when(userProfileMapper.findById(username)).thenReturn(Optional.of(mockUserProfile));

        bookRequestsService.addRequestingUser(bookId, username);

        verify(mockBook).addRequestingUser(mockUserProfile);
        verify(bookMapper).save(mockBook);
    }

    @Test
    void testRetrieveRequests_Success() {
        String username = "testUser";
        List<BookDto> mockBookDtos = new ArrayList<>();

        when(userProfileMapper.findById(username)).thenReturn(Optional.of(mockUserProfile));
        when(mockUserProfile.buildBookRequestsDtos()).thenReturn(mockBookDtos);

        List<BookDto> result = bookRequestsService.retrieveRequests(username);

        assertEquals(mockBookDtos, result);
    }

    @Test
    void testRetrieveRequestingUserProfiles_Success() {
        int bookId = 1;
        List<UserProfileDto> mockUserProfileDtos = new ArrayList<>();

        when(bookMapper.findById(bookId)).thenReturn(Optional.of(mockBook));
        when(mockBook.getRequestingUserProfileDtos()).thenReturn(mockUserProfileDtos);

        List<UserProfileDto> result = bookRequestsService.retrieveRequestingUserProfiles(bookId);

        assertEquals(mockUserProfileDtos, result);
    }

    @Test
    void testDeleteBookRequest_Success() {
        int bookId = 1;
        String username = "testUser";

        when(userProfileMapper.findById(username)).thenReturn(Optional.of(mockUserProfile));
        when(bookMapper.findByOfferId(bookId)).thenReturn(mockBook);

        bookRequestsService.deleteBookRequest(bookId, username);

        verify(mockBook).removeRequestingUser(mockUserProfile);
        verify(userProfileMapper).save(mockUserProfile);
    }

    @Test
    void testAcceptBookRequest_Success() {
        int bookId = 1;
        String selectedUser = "selectedUser";
        String owner = "owner";

        UserProfile selectedUserProfile = mock(UserProfile.class);
        UserProfile ownerProfile = mock(UserProfile.class);
        List<UserProfile> requestingUsers = new ArrayList<>();
        requestingUsers.add(mock(UserProfile.class));

        when(userProfileMapper.findById(selectedUser)).thenReturn(Optional.of(selectedUserProfile));
        when(userProfileMapper.findById(owner)).thenReturn(Optional.of(ownerProfile));
        when(bookMapper.findByOfferId(bookId)).thenReturn(mockBook);
        when(mockBook.getRequestingUsers()).thenReturn(requestingUsers);

        bookRequestsService.acceptBookRequest(bookId, selectedUser, owner);

        verify(notificationService).bookRequestAcceptance(selectedUserProfile, bookId);
        verify(userProfileMapper).save(selectedUserProfile);
        verify(notificationService).bookRequestRejection(requestingUsers.get(0), bookId);
        verify(userProfileMapper).save(requestingUsers.get(0));
        verify(ownerProfile).deleteBookOffer(mockBook);
        verify(userProfileMapper).save(ownerProfile);
    }

    @Test
    void testAddRequestingUser_BookNotFound() {
        int bookId = 1;
        String username = "testUser";

        when(bookMapper.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            bookRequestsService.addRequestingUser(bookId, username);
        });
    }

    @Test
    void testAddRequestingUser_UserNotFound() {
        int bookId = 1;
        String username = "testUser";

        when(bookMapper.findById(bookId)).thenReturn(Optional.of(mockBook));
        when(userProfileMapper.findById(username)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            bookRequestsService.addRequestingUser(bookId, username);
        });
    }
}
