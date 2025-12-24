package myy803.socialbookstore.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.*;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.UserProfileDto;

@Entity
@Table(name="books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="offer_id")
	private int offerId;
	
	@Column(name="title")
	private String title;
		
//	 many to one category
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}
	)
	@JoinColumn(name = "category_id")
	private BookCategory category;
	
	// many to many authors
	//
	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(
                    name = "book_offer_id", referencedColumnName = "offer_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "book_author_id", referencedColumnName = "author_id"
            )
    )
	private List<BookAuthor> bookAuthors;
	
	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(
            name = "books_requesting_users",
            joinColumns = @JoinColumn(
                    name = "book_offer_id", referencedColumnName = "offer_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "requesting_user_id", referencedColumnName = "username"
            )
    )
	private List<UserProfile> requestingUsers;
	
	public Book() {
		super();
	}

	public Book(String title) {
		this.title = title;
		bookAuthors = new ArrayList<BookAuthor>();
		requestingUsers = new ArrayList<UserProfile>();
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BookCategory getCategory() {
		return category;
	}

	public void setCategory(BookCategory category) {
		this.category = category;
	}

	public List<BookAuthor> getBookAuthors() {
		return bookAuthors;
	}

	public void setBookAuthors(List<BookAuthor> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	public List<UserProfile> getRequestingUsers() {
		return requestingUsers;
	}

	public void setRequestingUsers(List<UserProfile> requestingUsers) {
		this.requestingUsers = requestingUsers;
	}

	public BookDto buildDto() {
		String bookAuthorsList = buildAuthorsList();
		return new BookDto(offerId, title, bookAuthorsList, category.getName(),requestingUsers);
	}

	public void addAuthor(BookAuthor bookAuthor) {
		bookAuthors.add(bookAuthor);
	}

	public boolean belongsTo(String categoryName) {
		return category.checkName(categoryName);
	}

	public boolean writtenBy(String authors) {
		String bookAuthorsList = buildAuthorsList();		
		return bookAuthorsList.equals(authors);
	}
	
	private String buildAuthorsList() {
		String bookAuthorsNames = "";
		for(int i = 0; i < bookAuthors.size(); i++) {
			bookAuthorsNames += bookAuthors.get(i).getName();
			if(i < bookAuthors.size()-1)
				bookAuthorsNames += ", ";
		}
		return bookAuthorsNames;
	}

	public boolean authorsListIncludes(String authors) {
		String[] requiredAuthorsNames = authors.split(",");
		for(int i = 0; i < requiredAuthorsNames.length; i++) {
			boolean authorFound = false;
			for(int j = 0; j < bookAuthors.size(); j++) {
				if(bookAuthors.get(j).getName().equals(requiredAuthorsNames[i].trim()))
					authorFound = true;
			}
			if(!authorFound) return false;
		}
		return true;
	}

	public void addRequestingUser(UserProfile userProfile) {
		requestingUsers.add(userProfile);		
	}
	public void removeRequestingUser(UserProfile userProfile){ requestingUsers.remove(userProfile); }

	public List<UserProfileDto> getRequestingUserProfileDtos() {
		List<UserProfileDto> requestingUserProfileDtos = new ArrayList<UserProfileDto>();
		
		for(UserProfile requestingUser : requestingUsers) {
			requestingUserProfileDtos.add(requestingUser.buildProfileDto());
		}
		
		return requestingUserProfileDtos;
	}
}
