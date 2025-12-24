package myy803.socialbookstore.datamodel.recomstrategies;

import java.util.List;

import myy803.socialbookstore.formsdata.BookDto;

public interface RecommendationsStrategy {
	List<BookDto> recommend(String username);
}
