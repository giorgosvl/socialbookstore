package myy803.socialbookstore.mappers;

import java.util.List;

import myy803.socialbookstore.datamodel.BookAuthor;
import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import org.springframework.data.jpa.repository.JpaRepository;

import myy803.socialbookstore.datamodel.Book;
import org.springframework.data.jpa.repository.Query;

public interface BookMapper extends JpaRepository<Book, Integer> {
	List<Book> findByTitle(String title);
	List<Book> findByTitleContaining(String title);
	Book findByOfferId(int id);
	@Query("SELECT b FROM Book b")
	List<Book> findAllBooks();

}
