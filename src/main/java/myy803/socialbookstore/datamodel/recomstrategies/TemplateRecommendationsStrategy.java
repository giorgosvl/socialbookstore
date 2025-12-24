package myy803.socialbookstore.datamodel.recomstrategies;

import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.mappers.UserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class TemplateRecommendationsStrategy implements RecommendationsStrategy {

    @Autowired
    private UserProfileMapper userProfileMapper;

    public List<BookDto> recommend(String username){
        UserProfile userProfile = userProfileMapper.findByUsername(username);
        List<BookDto> bookDtos = retrieveRecommendedBooks(userProfile);

        return bookDtos;
    }
    protected abstract List<BookDto> retrieveRecommendedBooks(UserProfile userProfile);

}
