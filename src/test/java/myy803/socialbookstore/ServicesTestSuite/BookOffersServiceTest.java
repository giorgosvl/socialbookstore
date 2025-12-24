package myy803.socialbookstore.ServicesTestSuite;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.mappers.BookAuthorMapper;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.mappers.BookMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
import myy803.socialbookstore.services.BookOffersService;
import myy803.socialbookstore.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookOffersServiceTest {

    @InjectMocks
    private BookOffersService bookOffersService;

    @Mock
    private UserProfileMapper userProfileMapper;

    @Mock
    private BookCategoryMapper bookCategoryMapper;

    @Mock
    private BookAuthorMapper bookAuthorMapper;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveBookOffers_UserExists() {
        UserProfile userProfile = mock(UserProfile.class);
        List<BookDto> mockBookOffers = new ArrayList<>();
        mockBookOffers.add(new BookDto());

        when(userProfileMapper.findById("testUser")).thenReturn(Optional.of(userProfile));
        when(userProfile.buildBookOffersDtos()).thenReturn(mockBookOffers);

        List<BookDto> result = bookOffersService.retrieveBookOffers("testUser");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userProfileMapper, times(1)).findById("testUser");
        verify(userProfile, times(1)).buildBookOffersDtos();
    }

    @Test
    void testRetrieveBookCategories() {
        List<BookCategory> mockCategories = new ArrayList<>();
        mockCategories.add(new BookCategory());
        when(bookCategoryMapper.findAll()).thenReturn(mockCategories);

        List<BookCategory> result = bookOffersService.retrieveBookCategories();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookCategoryMapper, times(1)).findAll();
    }

    @Test
    void testSaveBookOffer_UserExists() {
        UserProfile userProfile = mock(UserProfile.class);
        BookDto bookDto = mock(BookDto.class);
        Book book = new Book();

        when(userProfileMapper.findById("testUser")).thenReturn(Optional.of(userProfile));
        when(bookDto.buildBookOffer(bookAuthorMapper, bookCategoryMapper)).thenReturn(book);

        bookOffersService.saveBookOffer(bookDto, "testUser");

        verify(userProfileMapper, times(1)).findById("testUser");
        verify(bookDto, times(1)).buildBookOffer(bookAuthorMapper, bookCategoryMapper);
        verify(userProfile, times(1)).addBookOffer(book);
        verify(userProfileMapper, times(1)).save(userProfile);
    }

    @Test
    void testDeleteBookOffer_UserExists() {
        UserProfile userProfile = mock(UserProfile.class);
        UserProfile requestingUser = mock(UserProfile.class);
        Book book = mock(Book.class);
        List<UserProfile> requestingUsers = List.of(requestingUser);

        when(userProfileMapper.findById("testUser")).thenReturn(Optional.of(userProfile));
        when(bookMapper.findByOfferId(1)).thenReturn(book);
        when(book.getRequestingUsers()).thenReturn(requestingUsers);

        bookOffersService.deleteBookOffer(1, "testUser");

        verify(bookMapper, times(1)).findByOfferId(1);
        verify(notificationService, times(1)).bookOfferDeletedNotification(requestingUser, 1);
        verify(userProfileMapper, times(1)).save(requestingUser);
        verify(userProfile, times(1)).deleteBookOffer(book);
        verify(userProfileMapper, times(1)).save(userProfile);
    }
}
