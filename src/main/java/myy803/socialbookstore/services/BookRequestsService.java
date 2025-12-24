package myy803.socialbookstore.services;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.UserProfileDto;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.mappers.BookMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookRequestsService {

    @Autowired
    UserService userService;

    @Autowired
    BookCategoryMapper bookCategoryMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private NotificationService notificationService;



    public void addRequestingUser(int bookId, String username){

        Optional<Book> requestedBook = bookMapper.findById(bookId);
        Optional<UserProfile> userProfile = userProfileMapper.findById(username);
        requestedBook.get().addRequestingUser(userProfile.get());
        bookMapper.save(requestedBook.get());
    }

    public List<BookDto> retrieveRequests(String username){

        Optional<UserProfile> userProfile = userProfileMapper.findById(username);
        return userProfile.get().buildBookRequestsDtos();
    }

    public List<UserProfileDto> retrieveRequestingUserProfiles(int bookId){

        Optional<Book> book = bookMapper.findById(bookId);
        return book.get().getRequestingUserProfileDtos();
    }

    public void deleteBookRequest(int bookId, String username){
        Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
        UserProfile userProfile = optUserProfile.get();

        bookMapper.findByOfferId(bookId).removeRequestingUser(userProfile);
        userProfileMapper.save(userProfile);
    }

    public void acceptBookRequest(int bookId, String selected_user, String owner) {
        Optional<UserProfile> userBeenAccepted = userProfileMapper.findById(selected_user);
        Optional<UserProfile> ownerOfBook = userProfileMapper.findById(owner);

        notificationService.bookRequestAcceptance(userBeenAccepted.get(), bookId);
        userProfileMapper.save(userBeenAccepted.get());

        Book book = bookMapper.findByOfferId(bookId);
        book.removeRequestingUser(userBeenAccepted.get());

        List<UserProfile> requestingUsers = book.getRequestingUsers();
        for (UserProfile requestingUser : new ArrayList<>(requestingUsers)) {
            if (!requestingUser.equals(userBeenAccepted.get())) {
                notificationService.bookRequestRejection(requestingUser, bookId);
                userProfileMapper.save(requestingUser);
            }
        }
        ownerOfBook.get().deleteBookOffer(book);
        userProfileMapper.save(ownerOfBook.get());
    }

}
