package myy803.socialbookstore.datamodel;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="notifications")
public class Notification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationid;

    @Column(name="content")
    private String content;
    @Column(name="time")
    private LocalDateTime sentTime;

    public Notification(String content) {
        this.content = content;
        this.sentTime = LocalDateTime.now();
    }

    public Notification() {}


    public void setNotificationid(int notificationid) {
        this.notificationid = notificationid;
    }

    public int getNotificationid() {
        return notificationid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }
}
