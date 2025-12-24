package myy803.socialbookstore.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.socialbookstore.datamodel.BookAuthor;

public interface BookAuthorMapper extends JpaRepository<BookAuthor, String> {
	List<BookAuthor> findByName(String Name);
}
