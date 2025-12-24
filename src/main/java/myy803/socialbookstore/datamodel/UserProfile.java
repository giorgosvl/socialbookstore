package myy803.socialbookstore.datamodel;
 
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="user_profiles")
public class UserProfile {
	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="age")
	private int age;

	@ManyToMany(fetch = FetchType.EAGER, 
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinTable(
            name = "profiles_authors",
            joinColumns = @JoinColumn(
                    name = "user_profile_id", referencedColumnName = "username"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "book_author_id", referencedColumnName = "author_id"
            )
    )
	private List<BookAuthor> favouriteBookAuthors;

	@ManyToMany(fetch = FetchType.EAGER, 
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinTable(
            name = "profiles_categories",
            joinColumns = @JoinColumn(
                    name = "user_profile_id", referencedColumnName = "username"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "book_category_id", referencedColumnName = "category_id"
            )
    )
	private List<BookCategory> favouriteBookCategories;

	@OneToMany(cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "profile_username")
	private List<Book> bookOffers;

	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "profile_notification")
	private List<Notification> notifications;


	@ManyToMany(mappedBy="requestingUsers")
	private List<Book> requestedBooks;

	public UserProfile() {
		super();
		
		favouriteBookAuthors = new ArrayList<BookAuthor>();
		favouriteBookCategories = new ArrayList<BookCategory>();
		bookOffers = new ArrayList<Book>();
		requestedBooks = new ArrayList<Book>();
		notifications = new ArrayList<>();
	}
	
	public UserProfile(String username, String fullName, int age, 
			List<BookAuthor> favouriteBookAuthors,
			List<BookCategory> favouriteBookCategories, 
			List<Book> bookOffers,
		    List<Notification> notifications) {
		super();
		this.username = username;
		this.fullName = fullName;
		this.age = age;
		this.favouriteBookAuthors = favouriteBookAuthors;
		this.favouriteBookCategories = favouriteBookCategories;
		this.bookOffers = bookOffers;
		this.notifications = notifications;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public List<BookAuthor> getFavouriteBookAuthors() {
		return favouriteBookAuthors;
	}

	public void setFavouriteBookAuthors(List<BookAuthor> favouriteBookAuthors) {
		this.favouriteBookAuthors = favouriteBookAuthors;
	}

	public List<BookCategory> getFavouriteBookCategories() {
		return favouriteBookCategories;
	}

	public void setFavouriteBookCategories(List<BookCategory> favouriteBookCategories) {
		this.favouriteBookCategories = favouriteBookCategories;
	}

	public List<Book> getBookOffers() {
		return bookOffers;
	}

	public void setBookOffers(List<Book> bookOffers) {
		this.bookOffers = bookOffers;
	}

	public void addBookAuthor(BookAuthor bookAuthor) {
		boolean ret = favouriteBookAuthors.add(bookAuthor);
	}

	public void addBookCategory(BookCategory bookCategory) {
		boolean ret = favouriteBookCategories.add(bookCategory);
	}



	public List<BookDto> buildBookOffersDtos() {
		ArrayList<BookDto> bookOffersDtos = new ArrayList<BookDto>();

		for(Book bookOffer : bookOffers) {
			bookOffersDtos.add(bookOffer.buildDto());
		}
			
		return bookOffersDtos;
	}

	public List<BookDto> buildBookRequestsDtos() {
		ArrayList<BookDto> bookRequestsDtos = new ArrayList<BookDto>();

		for(Book bookRequest : requestedBooks) {
			bookRequestsDtos.add(bookRequest.buildDto());
		}
			
		return bookRequestsDtos;
	}
	public UserProfileDto buildProfileDto() {
		String favoriteAuthors = "";
		String [] favoriteCategories = new String[favouriteBookCategories.size()];
			
		for(int i = 0; i < favouriteBookAuthors.size(); i++) {
			favoriteAuthors += favouriteBookAuthors.get(i).getName();
			if(i < favouriteBookAuthors.size()-1)
				favoriteAuthors += ", ";
		}
		
		for(int i = 0; i < favouriteBookCategories.size(); i++) {
			favoriteCategories[i] = favouriteBookCategories.get(i).getName();
		}
		
		return new UserProfileDto(username, fullName, age, favoriteAuthors, favoriteCategories);
	}

	public void clear() {
		favouriteBookAuthors.removeAll(favouriteBookAuthors);
		favouriteBookCategories.removeAll(favouriteBookCategories);
	}

	public void addBookOffer(Book bookOffer) {
		bookOffers.add(bookOffer);
	}

	public void deleteBookOffer(Book bookOffer){ bookOffers.remove(bookOffer); }

	public void addNotification(Notification notification){ notifications.add(notification); }

	public void deleteNotification(Notification notification){ notifications.remove(notification); }

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}



	public List<BookDto> getBooksOfFavouriteCategories() {
		ArrayList<BookDto> bookDtos = new ArrayList<BookDto>();
		List<BookCategory> bookCategories = getFavouriteBookCategories();
		for(BookCategory category: bookCategories) {
			List<Book> books = category.getBookOffers();
			for(Book book : books)
				bookDtos.add(book.buildDto());
		}
		return bookDtos;
	}

	public List<BookDto>  getBooksFromFavouriteAuthors() {
		ArrayList<BookDto> bookDtos = new ArrayList<BookDto>();
		List<BookAuthor> favouriteBookAuthors = getFavouriteBookAuthors();
		for(BookAuthor favouriteAuthor : favouriteBookAuthors) {
			List<Book> books = favouriteAuthor.getBooks();
			for(Book book : books)
				bookDtos.add(book.buildDto());
		}
		return bookDtos;
	}
}
