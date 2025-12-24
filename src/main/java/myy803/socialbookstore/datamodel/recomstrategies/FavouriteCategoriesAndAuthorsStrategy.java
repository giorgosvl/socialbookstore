package myy803.socialbookstore.datamodel.recomstrategies;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.mappers.UserProfileMapper;

@Component
public class FavouriteCategoriesAndAuthorsStrategy extends TemplateRecommendationsStrategy {

	protected List<BookDto> retrieveRecommendedBooks(UserProfile userProfile) {

		Set<BookDto> bookDtoSet = new LinkedHashSet<>();
		bookDtoSet.addAll(userProfile.getBooksFromFavouriteAuthors());
		bookDtoSet.addAll(userProfile.getBooksOfFavouriteCategories());
		return new ArrayList<>(bookDtoSet);
	}

}
