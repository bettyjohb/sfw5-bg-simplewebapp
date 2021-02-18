// ***************************************************************************
// Author 
// 
// JPA entity object containing first and last name, as well as the set of 
// Books written by a single Author. This object will be persisted into the 
// database and, therefore, will require mapping [@Entity, @Id, possibly 
// @Table, @Column, etc.] which is what turns this POJO into a JPA entity.
// 
// Annotate - See #1-#5 (@Entity, @Id, @GeneratedValue)]
// Define relation between Book and Author as many-many / *-*  - See #4
// *************************************************************************** 
package guru.springframework5.assign1to3.simplewebapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

@Entity 		// #1 - Annotate with @Entity to identify as JPA entity for DB  
public class Author {

	// -----------------------------------------------
	// Attributes  
	// -----------------------------------------------

	@Id             // #2 - Annotate with @Id to identify as key for Author class
	@GeneratedValue(strategy = GenerationType.AUTO)  // #3 - DB will generate key 
	private Long id;		
	
	private String firstName;
	private String lastName;
	
	// #4-Author Side - Create a Many-Many mapping of Authors-Books / Books-Authors
	//    This uses three DB tables - authors, books, and author-books (association table)
	//    Do @ManyToMany(...) says attribute books allows Author to have many Books
	//                        and "authors" is attribute in Book class that maps 
	//                        back to Author class so a Book can have many Authors.
	//                        Therefore many-many. 
	//    Need to finish many-many setup on Book side within Book class
	@ManyToMany(mappedBy = "authors")  
	private Set<Book> books = new HashSet<Book>();           

	// -----------------------------------------------
	// Constructors  
	// -----------------------------------------------

	/**
	 * Default constructor (required of JPA entity objects)
	 */
	public Author() {
		super();
	}

	/**
	 * Constructor 
	 * 
	 * Do NOT include "id" as parameter.  It is a generated value that 
	 * Hibernate will inject with setter after using default constructor.
	 * Do NOT include "books" as parameter.  Keep it simple for now. 
     */	 
	public Author(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
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

	// First Name
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	// Last Name
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// Set of Books written by this Author. 
	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	// -----------------------------------------------
	// #5 Methods that override Java default functionality.  
	// -----------------------------------------------

	/**
	 * Provides a reader friendly, string representation of Author object.   
	 * 
	 * This method overrides that default functionality provided by the 
	 * java.lang.Object.toString() method.  Useful for logging and/or 
	 * debugging. 
	 * 
	 * @return String containing JSON style representation of Author object. 
	 */
	@Override
	public String toString() {
		return "Author{" +
	           "id=" + id + 
			   ", firstName=" + firstName + '\'' + 
			   ", lastName=" + lastName + '\'' +
			   // Remove for now - makes circular reference and loops 
			   // ", books=" + books +
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
		
		// Works even if "o" is a derived class of Author. 
		if ( !(o instanceof Author) )
			return false;
		
		Author ao = (Author)o;

		// Id (type Long)  
		// This is the primary key, so most important to determine if same object.
		if (this.id == null) 
		{
			// False if only this.id is null. Not equal.
			if (ao.id != null)
				return false;
		} 
		else if (ao.id == null)
		{
			// False if only ao.id is null.
			if (this.id != null)
				return false;
		} 
		else 
		{
			// Both have non-null id to compare!
			if (!this.id.equals(ao.id))
				return false;
		}
 

		// We could stop here.  However, I will choose to compare all fields as well.
		// First name 
		if (this.firstName == null) 
		{
			// False if only this.firstName has null name. Not equal.
			if (ao.firstName != null)
				return false;
		} 
		else if (ao.firstName == null)
		{
			// False if only ao.firstName has null name.
			if (this.firstName != null)
				return false;
		} 
		else 
		{
			// Both have non-null names to compare!
			if (!this.firstName.equals(ao.firstName))
				return false;
		}

		// Last Name
		if (this.lastName == null) 
		{
			// False if only this.lastName has null name. Not equal.
			if (ao.lastName != null)
				return false;
		} 
		else if (ao.lastName == null)
		{
			// False if only ao.lastName has null name.
			if (this.lastName != null)
				return false;
		} 
		else 
		{
			// Both have non-null names to compare!
			if (!this.lastName.equals(ao.lastName))
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
		result = result * prime + ( (firstName == null) ? 0 : firstName.hashCode());
		result = result * prime + ( (lastName == null) ? 0 : lastName.hashCode());
		
		return result;
		
	}  // end hashCode()

}  // end class Author
