package myy803.socialbookstore.datamodel;
 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.UserProfileDto;

@Entity
@Table(name="user_profiles")
public class UserProfile2 {
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
	private Set<BookAuthor> favouriteBookAuthors;
//	private List<BookAuthor> favouriteBookAuthors;

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
	private Set<BookCategory> favouriteBookCategories;
	//private List<BookCategory> favouriteBookCategories;

	@OneToMany(cascade = CascadeType.ALL, 
			fetch = FetchType.EAGER)
	@JoinColumn(name = "profile_username")
	private List<Book> bookOffers;

	public UserProfile2() {
		super();
		favouriteBookAuthors = new HashSet<BookAuthor>();
		favouriteBookCategories = new HashSet<BookCategory>();
		//favouriteBookAuthors = new ArrayList<BookAuthor>();
		//favouriteBookCategories = new ArrayList<BookCategory>();
	}
	
	public UserProfile2(String username, String fullName, int age, 
			Set<BookAuthor> favouriteBookAuthors,
			Set<BookCategory> favouriteBookCategories, 
			//List<BookAuthor> favouriteBookAuthors,
			//List<BookCategory> favouriteBookCategories, 
			List<Book> bookOffers) {
		super();
		this.username = username;
		this.fullName = fullName;
		this.age = age;
		this.favouriteBookAuthors = favouriteBookAuthors;
		this.favouriteBookCategories = favouriteBookCategories;
		this.bookOffers = bookOffers;
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

	//public List<BookAuthor> getFavouriteBookAuthors() {
	//	return favouriteBookAuthors;
	//}

//	public void setFavouriteBookAuthors(List<BookAuthor> favouriteBookAuthors) {
//		this.favouriteBookAuthors = favouriteBookAuthors;
//	}
//
//	public List<BookCategory> getFavouriteBookCategories() {
//		return favouriteBookCategories;
//	}
//
//	public void setFavouriteBookCategories(List<BookCategory> favouriteBookCategories) {
//		this.favouriteBookCategories = favouriteBookCategories;
//	}
//	
	
	public List<Book> getBookOffers() {
		return bookOffers;
	}

	public Set<BookAuthor> getFavouriteBookAuthors() {
		return favouriteBookAuthors;
	}

	public void setFavouriteBookAuthors(Set<BookAuthor> favouriteBookAuthors) {
		this.favouriteBookAuthors = favouriteBookAuthors;
	}

	public Set<BookCategory> getFavouriteBookCategories() {
		return favouriteBookCategories;
	}

	public void setFavouriteBookCategories(Set<BookCategory> favouriteBookCategories) {
		this.favouriteBookCategories = favouriteBookCategories;
	}

	public void setBookOffers(List<Book> bookOffers) {
		this.bookOffers = bookOffers;
	}

	public void addBookAuthor(BookAuthor bookAuthor) {
		boolean ret = favouriteBookAuthors.add(bookAuthor);
		System.err.println("addBookCategory returns " + bookAuthor.getName() + " : " + ret);
	}

	public void addBookCategory(BookCategory bookCategory) {
		boolean ret = favouriteBookCategories.add(bookCategory);
		System.err.println("addBookCategory returns " + bookCategory.getName() + " : " + ret);
	}

	public List<BookDto> buildBookOffersDtos() {
		ArrayList<BookDto> bookOffersDtos = new ArrayList<BookDto>();

		for(Book bookOffer : bookOffers) {
			bookOffersDtos.add(bookOffer.buildDto());
		}
			
		return bookOffersDtos;
	}

	public UserProfileDto buildProfileDto() {
		String favoriteAuthors = "";
		String [] favoriteCategories = new String[favouriteBookCategories.size()];
	
//		for(BookAuthor author : favouriteBookAuthors) {
//			favoriteAuthors += author.getName();
//		}
			
		Iterator<BookAuthor> authorsIterator = favouriteBookAuthors.iterator();
		while (authorsIterator.hasNext()) {
			favoriteAuthors += authorsIterator.next().getName();
			if(authorsIterator.hasNext()) favoriteAuthors += ", ";
		}
		
		Iterator<BookCategory> iterator = favouriteBookCategories.iterator();
		int counter = 0;
		while (iterator.hasNext()) {
			favoriteCategories[counter++] = iterator.next().getName();
		}
		
//		for(int i = 0; i < favouriteBookCategories.size(); i++) {
//			favoriteCategories[i] = favouriteBookCategories.get(i).getName();
//		}
		
		return new UserProfileDto(username, fullName, age, favoriteAuthors, favoriteCategories);
	}

	public void clear() {
		favouriteBookAuthors.removeAll(favouriteBookAuthors);
		favouriteBookCategories.removeAll(favouriteBookCategories);
	}
	
}
