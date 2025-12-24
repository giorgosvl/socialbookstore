package myy803.socialbookstore.datamodel.searchstrategies;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.formsdata.SearchDto;
import myy803.socialbookstore.mappers.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AllSearchStrategy extends TemplateSearchStrategy {

    @Autowired
    private BookMapper bookMapper;


    protected List<Book> makeInitialListOfBooks(SearchDto searchDto) {
        List<Book> books = bookMapper.findAllBooks();
        return books;
    }

    protected boolean checkIfAuthorsMatch(SearchDto searchDto, Book book) {
        return true;
    }
}
