package myy803.socialbookstore.datamodel.recomstrategies;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.mappers.BookDtoMapper;
import myy803.socialbookstore.mappers.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TrendingStrategy extends TemplateRecommendationsStrategy {


    @Override
    protected List<BookDto> retrieveRecommendedBooks(UserProfile userProfile) {
        List<BookDto> bookDtoSet =  new ArrayList<>();
        List<BookDto> sortedTrendingBooks = new ArrayList<>();

        bookDtoSet.addAll(userProfile.getBooksFromFavouriteAuthors());
        bookDtoSet.addAll(userProfile.getBooksOfFavouriteCategories());

        List<BookDto> sortedBooks = bookDtoSet.stream()
                                .sorted(Comparator.comparingInt(
                                (BookDto bookDto)->bookDto.getRequestingUsers()
                                        != null ? bookDto.getRequestingUsers().size() : 0)
                                        .reversed()).toList();

        for (BookDto book : sortedBooks) {
            if (book.getRequestingUsers().size() > 1) {
                System.err.println("Book's requesting users: "+book.getRequestingUsers().size());
                sortedTrendingBooks.add(book);
            }
        }
        return sortedTrendingBooks;
    }

}
