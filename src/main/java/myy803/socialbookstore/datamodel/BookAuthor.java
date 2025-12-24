package myy803.socialbookstore.datamodel;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="book_authors")
@Getter @Setter //@NoArgsConstructor @RequiredArgsConstructor
@ToString
public class BookAuthor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="author_id")
	private int authorId;
	
	@Column(name="name")
	private String name;


	@ManyToMany(mappedBy="bookAuthors")
    private List<Book> books = new ArrayList<Book>();
	
	public BookAuthor() {}

	public BookAuthor(String name) {
		this.name = name;
	}
 
	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookAuthor other = (BookAuthor) obj;
		return authorId == other.authorId;
	}
}
