package myy803.socialbookstore.datamodel.searchstrategies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.SearchDto;
import myy803.socialbookstore.mappers.BookMapper;

@Component
public class ApproximateSearchStrategy extends TemplateSearchStrategy {
	
	@Autowired
	protected BookMapper bookMapper;
	
	protected List<Book> makeInitialListOfBooks(SearchDto searchDto) {
		List<Book> books = bookMapper.findByTitleContaining(searchDto.getTitle());
		return books;
	}
	
	protected boolean checkIfAuthorsMatch(SearchDto searchDto, Book book) {
		boolean authorsMatch;
		authorsMatch = book.authorsListIncludes(searchDto.getAuthors());
		return authorsMatch;
	}
}
