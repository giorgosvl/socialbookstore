package myy803.socialbookstore.services;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.Notification;
import myy803.socialbookstore.datamodel.User;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.mappers.BookMapper;
import myy803.socialbookstore.mappers.NotificationMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private NotificationMapper notificationMapper;


    public void bookRequestRejection(UserProfile userProfile, int bookId){
        userProfile.addNotification(new Notification("Sorry, "+bookMapper.findByOfferId(bookId).getTitle()+" has been taken by another user."));
    }

    public void bookRequestAcceptance(UserProfile userProfile, int bookId){
        userProfile.addNotification(new Notification("Congratulations! You can take "+bookMapper.findByOfferId(bookId).getTitle()));
    }

    public List<Notification> retrieveUserNotifications(String username){
        Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
        UserProfile userProfile = optUserProfile.get();
        List<Notification> notifications = userProfile.getNotifications();
        Collections.reverse(notifications);

        return notifications;
    }
    public void  bookOfferDeletedNotification(UserProfile userProfile, int bookId){
        userProfile.addNotification(new Notification("The book "+bookMapper.findByOfferId(bookId).getTitle()+" has been removed from the owner :-("));
    }

    public void removeNotification(int notificationId ,String username){
        Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
        UserProfile userProfile = optUserProfile.get();
        userProfile.deleteNotification(notificationMapper.findByNotificationid(notificationId));
        userProfileMapper.save(userProfile);
    }

}
