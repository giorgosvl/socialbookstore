package myy803.socialbookstore.datamodel.recomstrategies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.mappers.UserProfileMapper;

@Component
public class FavouriteAuthorsStrategy extends TemplateRecommendationsStrategy {

	protected List<BookDto> retrieveRecommendedBooks(UserProfile userProfile) {
		List<BookDto> bookDtos = userProfile.getBooksFromFavouriteAuthors();
		return bookDtos;
	}
}
