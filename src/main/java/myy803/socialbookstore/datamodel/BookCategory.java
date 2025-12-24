package myy803.socialbookstore.datamodel;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="book_categories")
@Getter @Setter //@NoArgsConstructor @RequiredArgsConstructor
@ToString
public class BookCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="category_id")
	private int categoryId;
	
	@Column(name="name")
	private String name;

	@OneToMany(mappedBy="category")
	private List<Book> bookOffers;
	
	public BookCategory() {
		super();
	}

	public int getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public List<Book> getBookOffers() {
		return bookOffers;
	}


	public void setBookOffers(List<Book> bookOffers) {
		this.bookOffers = bookOffers;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookCategory other = (BookCategory) obj;
		return categoryId == other.categoryId;
	}

	public boolean checkName(String categoryName) {
		return name.equals(categoryName);
	}
}
