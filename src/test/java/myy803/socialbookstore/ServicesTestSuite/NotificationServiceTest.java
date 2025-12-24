package myy803.socialbookstore.ServicesTestSuite;

import myy803.socialbookstore.services.*;
import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.Notification;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.mappers.BookMapper;
import myy803.socialbookstore.mappers.NotificationMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
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

class NotificationServiceTest {

    @Mock
    private BookMapper bookMapper;

    @Mock
    private UserProfileMapper userProfileMapper;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookRequestRejection() {
        UserProfile userProfile = new UserProfile();
        Book book = new Book();
        book.setTitle("Test Book");
        int bookId = 1;

        when(bookMapper.findByOfferId(bookId)).thenReturn(book);

        notificationService.bookRequestRejection(userProfile, bookId);

        assertEquals(1, userProfile.getNotifications().size());
        assertEquals("Sorry, Test Book has been taken by another user.",
                userProfile.getNotifications().get(0).getContent());
    }

    @Test
    void testBookRequestAcceptance() {
        UserProfile userProfile = new UserProfile();
        Book book = new Book();
        book.setTitle("Accepted Book");
        int bookId = 1;

        when(bookMapper.findByOfferId(bookId)).thenReturn(book);

        notificationService.bookRequestAcceptance(userProfile, bookId);

        assertEquals(1, userProfile.getNotifications().size());
        assertEquals("Congratulations! You can take Accepted Book",
                userProfile.getNotifications().get(0).getContent());
    }

    @Test
    void testRetrieveUserNotifications() {
        String username = "testUser";
        UserProfile userProfile = new UserProfile();
        List<Notification> originalNotifications = new ArrayList<>();
        originalNotifications.add(new Notification("Notification 1"));
        originalNotifications.add(new Notification("Notification 2"));
        userProfile.setNotifications(originalNotifications);

        when(userProfileMapper.findById(username)).thenReturn(Optional.of(userProfile));

        List<Notification> retrievedNotifications = notificationService.retrieveUserNotifications(username);

        assertEquals(2, retrievedNotifications.size());
        assertEquals("Notification 2", retrievedNotifications.get(0).getContent());
        assertEquals("Notification 1", retrievedNotifications.get(1).getContent());
    }

    @Test
    void testBookOfferDeletedNotification() {
        UserProfile userProfile = new UserProfile();
        Book book = new Book();
        book.setTitle("Deleted Book");
        int bookId = 1;

        when(bookMapper.findByOfferId(bookId)).thenReturn(book);

        notificationService.bookOfferDeletedNotification(userProfile, bookId);

        assertEquals(1, userProfile.getNotifications().size());
        assertEquals("The book Deleted Book has been removed from the owner :-(",
                userProfile.getNotifications().get(0).getContent());
    }

    @Test
    void testRemoveNotification() {
        String username = "testUser";
        int notificationId = 1;
        UserProfile userProfile = new UserProfile();
        Notification notificationToRemove = new Notification("Test Notification");
        userProfile.addNotification(notificationToRemove);

        when(userProfileMapper.findById(username)).thenReturn(Optional.of(userProfile));
        when(notificationMapper.findByNotificationid(notificationId)).thenReturn(notificationToRemove);

        notificationService.removeNotification(notificationId, username);

        verify(userProfileMapper).save(userProfile);
        assertEquals(0, userProfile.getNotifications().size());
    }

    @Test
    void testRetrieveUserNotifications_EmptyList() {
        String username = "testUser";
        UserProfile userProfile = new UserProfile();
        userProfile.setNotifications(new ArrayList<>());

        when(userProfileMapper.findById(username)).thenReturn(Optional.of(userProfile));

        List<Notification> retrievedNotifications = notificationService.retrieveUserNotifications(username);

        assertTrue(retrievedNotifications.isEmpty());
    }
}
