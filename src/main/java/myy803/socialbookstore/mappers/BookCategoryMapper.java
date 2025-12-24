package myy803.socialbookstore.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.socialbookstore.datamodel.BookCategory;

public interface BookCategoryMapper extends JpaRepository<BookCategory, String> {
	List<BookCategory> findByName(String name);
}
