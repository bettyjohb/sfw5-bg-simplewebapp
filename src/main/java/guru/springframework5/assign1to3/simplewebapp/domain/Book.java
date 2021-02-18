// ***************************************************************************
// Class:  Book 
// 
// JPA entity (POJO) object containing information for a single book.
// This object will be persisted into the database and, therefore, will require 
// mapping [@Entity, @Id, possibly @Table, @Column, etc.] which is what turns 
// this POJO into a JPA entity.
//
// Assignment 2 of 1-3 Combo:
// Annotate - See #1-#3 (@Entity, @Id, @GeneratedValue)] 
//            #4- Define relation between Book and Author as many-many / *-*
//            #5- Override equals / hashCode for correct equality behavior 
//                for Hibernate and Collections (like Set). 
//
// Assignment 3 of 1-3 Combo
// Include 1-many mapping (Publisher has many Books; Book has one Publisher).
//    #A- Add "publisher" attribute
//    #B- Setter/Getter
// *************************************************************************** 
package guru.springframework5.assign1to3.simplewebapp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity 		// #1 - Annotate with @Entity to identify as JPA entity for DB
public class Book {
	// -----------------------------------------------
	// Attributes  
	// -----------------------------------------------

	@Id               // #2 - Annotate with @Id to identify as key for Book class
	@GeneratedValue(strategy = GenerationType.AUTO)  // #3 - DB will generate key
	private Long id;  // Primary Key 
	
	private String title;
	private String isbn;
	
	// #4-Book/2nd Side - Create a Many-Many mapping of Authors-Books / Books-Authors
	//    This uses three DB tables - 
	//         author table - Author information
	//         book table   - Book information
	//         author_book   - association/join table to manage many-many relationships)
	//    Do @ManyToMany
    //    Do @JoinTable(name = <association table name>,
	//                  joinColumns = @JoinColunn(name = <assoc col of owning side>),
	//                  inverseColumns = @JoinColumn(name = <assoc col of non-owning side>)) 
	@ManyToMany 
	@JoinTable(name = "author_book", 
	           joinColumns = @JoinColumn(name = "book_id"), // In Book class so owning is book_id
	           inverseJoinColumns = @JoinColumn(name = "author_id"))  // non-owning 
	private Set<Author> authors = new HashSet<Author>();  // All authors that contributed to this book
	                                                      // Create empty Hashtable so run() getBooks does not return null


	// #A - Book/2nd Side - Create 1-many relationship (Book 1 Publisher; Publisher many Books).  
	//      Therefore, Many Books share 1 Publisher (or Many-1) though each book only tracks its own Publisher
	//      INVERSE OF THE PUBLISHER
	@ManyToOne   // From the Book perspective. 
	private Publisher publisher; 
	
	// -----------------------------------------------
	// Constructors 
	// -----------------------------------------------

	// Default constructor (required by JPA entity objects)
    public Book () {
        super();	
    }
    
	// Constructor
	// Do NOT include "id" as parameter.  It is a generated value that 
	// Hibernate will inject with setter after using default constructor.
    // Do NOT include "authors" to keep it simple for now. 
	public Book(String title, String isbn) {
		super();
		this.title = title;
		this.isbn = isbn;
	}
	

	// -----------------------------------------------
	// Getters / Setters
	// Used by Spring / Hibernate to do Dependency Injection (DI)
	// -----------------------------------------------

	// Id attribute - Used as the primary key
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	// Book title
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	// Book ISBN
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Publisher getPublisher() {
		return publisher;
	}
	
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
	
	// -----------------------------------------------
	// Methods that override Java default functionality.  
	// -----------------------------------------------

	/**
	 * Provides a reader friendly, string representation of Book object.   
	 * 
	 * This method overrides that default functionality provided by the 
	 * java.lang.Object.toString() method.  Useful for logging and/or 
	 * debugging. 
	 * 
	 * @return String containing JSON style representation of Book object. 
	 */
	@Override
	public String toString() {
		return "Book{" +
			   "id=" + id + 
			   ", title=" + title + '\'' + 
			   ", isbne=" + isbn +  '\'' +
			   // Remove for now - makes circular reference and loops 
			   // ", authors=" + authors + 
			   "}";
	}  // end toString()

	/**
	 * Determines if two objects are logically equal.
	 * 
	 * Logically equal means that they have the same "state" (instance 
	 * variables have the same values).     
	 * 
	 * This is needed by Hibernate and Collections (like Sets) to determine equality. 
	 * 
	 * This method overrides that default functionality provided by the 
	 * java.lang.Object.equals(Object) method that determines if the 
	 * object reference variables are the same (i.e. if they reference the
	 * same object in memory).   
	 * 
	 * @return boolean true if logically equal; false otherwise. 
	 */
	public boolean equals (Object o)
	{
		// Check if both object reference variables reference the same object in memory.
		if (o == this)
			return true;
		
		if (o == null)
			return false;
		
		// Works even if "o" is a derived class of Book. 
		if ( !(o instanceof Book) )
			return false;
		
		Book bo = (Book)o;
	
		// Id (type Long)  
		// This is the primary key, so most important to determine if same object.
		if (this.id == null) 
		{
			// False if only this.id is null. Not equal.
			if (bo.id != null)
				return false;
		} 
		else if (bo.id == null)
		{
			// False if only ao.id is null.
			if (this.id != null)
				return false;
		} 
		else 
		{
			// Both have non-null id to compare!
			if (!this.id.equals(bo.id))
				return false;
		}
	
		// We could stop here.  However, I will choose to compare all fields as well.
		// Title 
		if (this.title == null) 
		{
			// False if only this.title has null name. Not equal.
			if (bo.title != null)
				return false;
		} 
		else if (bo.title == null)
		{
			// False if only bo.title has null name.
			if (this.title != null)
				return false;
		} 
		else 
		{
			// Both have non-null names to compare!
			if (!this.title.equals(bo.title))
				return false;
		}
	
		// isbn
		if (this.isbn == null) 
		{
			// False if only this.isbn is null. Not equal.
			if (bo.isbn != null)
				return false;
		} 
		else if (bo.isbn == null)
		{
			// False if only bo.isbn is null.
			if (this.isbn != null)
				return false;
		} 
		else 
		{
			// Both have non-null values to compare!
			if (!this.isbn.equals(bo.isbn))
				return false;
		}
	
		// We made it!!!  Objects are equal!!
		return true;
		
	}  // end equals(Object)
	
	
	/**
	 * Calculates the object's hash code.
	 * 
	 * The hash code need not be a specific value.  However, the following must be true:
	 * a) IMPORTANT - If two objects are equal per the equals(Object) method, then calling 
	 * hashCode() on each object MUST return the same int.  
	 * b) While it is NOT required that if two objects are unequal per equals(Object) calling 
	 * hashCode() produces distinct values, doing so will increase performance on 
	 * collections (i.e., a hash table).       
	 * 
	 * This method overrides the default functionality provided by the 
	 * java.lang.Object.hashCode() method.  This method must be overridden any 
	 * time the equals method is overridden.
	 *  
	 * @return int hash code value 
	 */
	public int hashCode()
	{
		final int prime = 17;
		int result = 1;
		
		// In this algorithm, based on Joshua Bloch's blog, if an attribute is an 
		// Object (i.e., String) return 0 if null or call hashCode() on it.
		// NOTE:  String.hashCode() returns the same int value for strings of the 
		// same value (i.e., "one" and "one") though they are different actual String objects).
		result = result * prime + ( (id == null) ? 0 : id.hashCode());
		result = result * prime + ( (title == null) ? 0 : title.hashCode());
		result = result * prime + ( (isbn == null) ? 0 : isbn.hashCode());
		
		return result;
		
	}  // end hashCode()

}  // end class Book 
