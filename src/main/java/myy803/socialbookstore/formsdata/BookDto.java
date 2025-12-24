package myy803.socialbookstore.formsdata;

import java.util.List;
import java.util.Objects;

import myy803.socialbookstore.datamodel.UserProfile;
import org.springframework.stereotype.Component;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.BookAuthor;
import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.mappers.BookAuthorMapper;
import myy803.socialbookstore.mappers.BookCategoryMapper;

@Component
public class BookDto {
	private int id;
	private String title;
	private String authors;
	private String category;
	
	private String searchStrategy;

	private List<UserProfile> requestingUsers;
	
	public BookDto() {
		super();
	}

	public BookDto(int offerId, String bookTitle, String bookAuthors, String bookCategory,List<UserProfile> requestingUsers) {
		super();
		this.id = offerId;
		this.title = bookTitle;
		this.authors = bookAuthors;
		this.category = bookCategory;
		this.requestingUsers = requestingUsers;
	}

	public List<UserProfile> getRequestingUsers() {
		return requestingUsers;
	}

	public void setRequestingUsers(List<UserProfile> requestingUsers) {
		this.requestingUsers = requestingUsers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	public String getSearchStrategy() {
		return searchStrategy;
	}

	public void setSearchStrategy(String searchStrategy) {
		this.searchStrategy = searchStrategy;
	}

	@Override
	public String toString() {
		return "BookOfferDto [offerId=" + id + ", bookTitle=" + title + ", bookAuthors=" + authors
				+ ", bookCategory=" + category + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		BookDto bookDto = (BookDto) obj;
		return Objects.equals(id, bookDto.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Book buildBookOffer(BookAuthorMapper bookAuthorMapper, BookCategoryMapper bookCategoryMapper) {
		Book bookOffer = new Book(title);
		
		List<BookCategory> bookCategories = bookCategoryMapper.findByName(category);
		if(bookCategories.isEmpty()) {
			bookCategories = bookCategoryMapper.findByName("Other");
		}

		bookOffer.setCategory(bookCategories.get(0));

		String[] bookAuthorsArray = authors.split(",");
		for(int i = 0; i < bookAuthorsArray.length; i++) {
			List<BookAuthor> bookAuthors = bookAuthorMapper.findByName(bookAuthorsArray[i].trim());
			if(!bookAuthors.isEmpty())
				bookOffer.addAuthor(bookAuthors.get(0));
			else
				bookOffer.addAuthor(new BookAuthor(bookAuthorsArray[i].trim()));
		}
		
		return bookOffer;
	}
	
}
