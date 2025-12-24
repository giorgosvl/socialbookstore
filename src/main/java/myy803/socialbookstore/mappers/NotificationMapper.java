package myy803.socialbookstore.mappers;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationMapper extends JpaRepository<Notification, Integer> {
    Notification findByNotificationid(int id);
}
