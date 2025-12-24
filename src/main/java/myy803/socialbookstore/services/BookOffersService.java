package myy803.socialbookstore.services;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.mappers.BookAuthorMapper;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.mappers.BookMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookOffersService {
    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    BookCategoryMapper bookCategoryMapper;

    @Autowired
    private BookAuthorMapper bookAuthorMapper;

    @Autowired
    private BookCategoryMapper bookCategoriesMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private NotificationService notificationService;


    public List<BookDto> retrieveBookOffers(String username){

        Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
        UserProfile userProfile = optUserProfile.get();
        return userProfile.buildBookOffersDtos();
    }

    public List<BookCategory> retrieveBookCategories(){
        return bookCategoryMapper.findAll();
    }

    public void saveBookOffer(BookDto bookOfferDto,String username){
        Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
        UserProfile userProfile = optUserProfile.get();
        userProfile.addBookOffer(bookOfferDto.buildBookOffer(bookAuthorMapper, bookCategoriesMapper));
        userProfileMapper.save(userProfile);
    }

    public void deleteBookOffer(int bookId, String username){
        Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
        UserProfile userProfile = optUserProfile.get();
        Book book = bookMapper.findByOfferId(bookId);

        List<UserProfile> requestingUsers = book.getRequestingUsers();
        for(UserProfile requestingUser : requestingUsers){
            notificationService.bookOfferDeletedNotification(requestingUser,bookId);
            userProfileMapper.save(requestingUser);
        }
        userProfile.deleteBookOffer(book);
        userProfileMapper.save(userProfile);

    }

}
