// ***************************************************************************
// Publisher 
// 
// JPA entity object containing name and address information for a single 
// publisher.  This object will be persisted into the database and, therefore,
// will require mapping [@Entity, @Id, possibly @Table, @Column, etc.] which 
// is what turns this POJO into a JPA entity.
// 
// Assignment 2 of 1-3 Combo:
// To be a JPA Entity, must do #1-#4
//    #1- Mark as @Entity 
//	  #2-3- Set up Id property with @Id and @GeneratedValue
//    #4- Override equals() [so based on state not ref], hashcode() [when do equals], and toString()
//       [Need for Hibernate and also for Collections like Set]
//
// A simple class where address is separate Strings (not an Address class)
// 
// Assignment 3 of 1-3 Combo: 
// Include 1-many mapping (Publisher has many Books; Book has one Publisher).
//    #A- Add "books" attribute
//    #B- Getter/Setter
// *************************************************************************** 
package guru.springframework5.assign1to3.simplewebapp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity         // #1 - Annotate with @Entity to identify as JPA entity for DB
public class Publisher {

	// -----------------------------------------------
	// Attributes  
	// -----------------------------------------------

	@Id          // #2 - Annotate with @Id to identify as key for Publisher class
	@GeneratedValue(strategy = GenerationType.AUTO)  // #3 - DB will generate key 
	private Long id;		

	private String name;
	private String addressLine1;
	private String city;
	private String state;
	private String zip;

    // #A - 1-Many relationship - Publisher is 1, Can have many Books   
	@OneToMany        
	@JoinColumn(name = "publisher_id")  // will need a Publisher Id on the Book record   
	private Set<Book> books = new HashSet<Book>();  // set to empty Set so no null error 
	
	// -----------------------------------------------
	// Constructors  
	// -----------------------------------------------

	/**
	 * Default constructor (required of JPA entity objects)
	 */
	public Publisher() {
		super();
	}

	/**
	 * Constructor 
	 * 
	 * Do NOT include "id" as parameter.  It is a generated value that 
	 * Hibernate will inject with setter after using default constructor.
	 * Do NOT include "books" as parameter. 
     */	 
	public Publisher(String name, String addressLine1, String city, String state, String zip) {
		super();
		this.name = name;
		this.addressLine1 = addressLine1;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	// -----------------------------------------------
	// Getters / Setters
	// Used by Spring / Hibernate to do Dependency Injection (DI)
	// -----------------------------------------------
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	// -----------------------------------------------
	// #4 Methods that override Java default functionality.  
	//    
	//    Override equals and hashcode so works with Hibernate and Collections like Set
	//    which need to know if instances are logically equal. 
	// -----------------------------------------------

	/**
	 * Provides a reader friendly, string representation of Publisher object.   
	 * 
	 * This method overrides that default functionality provided by the 
	 * java.lang.Object.toString() method.  Useful for logging and/or 
	 * debugging. 
	 * 
	 * @return String containing JSON style representation of Publisher object. 
	 */
	@Override
	public String toString() {
		return "Publisher{" +
	           "id=" + id + 
			   ", name=" + name + '\'' + 
			   ", addressLine1=" + addressLine1 + '\'' +
			   ", city=" + city + '\'' +
			   ", state=" + state + '\'' +
			   ", zip=" + zip + '\'' +
			   ", books=" + books + '\'' +
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
	@Override
	public boolean equals (Object o)
	{
		// Check if both object reference variables reference the same object in memory.
		if (o == this)
			return true;
		
		if (o == null)
			return false;
		
		// Works even if "o" is a derived class of Publisher. 
		if ( !(o instanceof Publisher) )
			return false;
		
		Publisher po = (Publisher)o;

		// Id (type Long)  
		// This is the primary key, so most important to determine if same object.
		if (this.id == null) 
		{
			// False if only this.id is null. Not equal.
			if (po.id != null)
				return false;
		} 
		else if (po.id == null)
		{
			// False if only po.id is null.
			if (this.id != null)
				return false;
		} 
		else 
		{
			// Both have non-null id to compare!
			if (!this.id.equals(po.id))
				return false;
		}
	
	
		// We could stop here.  However, I will choose to compare all fields as well.
		// Name 
		if (this.name == null) 
		{
			// False if only this.name has null name. Not equal.
			if (po.name != null)
				return false;
		} 
		else if (po.name == null)
		{
			// False if only po.name has null value.
			if (this.name != null)
				return false;
		} 
		else 
		{
			// Both have non-null names to compare!
			if (!this.name.equals(po.name))
				return false;
		}

		// Address Line 1 
		if (this.addressLine1 == null) 
		{
			// False if only this.addressLine1 has null name. Not equal.
			if (po.addressLine1 != null)
				return false;
		} 
		else if (po.addressLine1 == null)
		{
			// False if only po.addressLine1 has null value.
			if (this.addressLine1 != null)
				return false;
		} 
		else 
		{
			// Both have non-null names to compare!
			if (!this.addressLine1.equals(po.addressLine1))
				return false;
		}
	
		
		// City 
		if (this.city == null) 
		{
			// False if only this.city has null name. Not equal.
			if (po.city != null)
				return false;
		} 
		else if (po.city == null)
		{
			// False if only po.city has null name.
			if (this.city != null)
				return false;
		} 
		else 
		{
			// Both have non-null names to compare!
			if (!this.city.equals(po.city))
				return false;
		}

		// State 
		if (this.state == null) 
		{
			// False if only this.state has null name. Not equal.
			if (po.state != null)
				return false;
		} 
		else if (po.state == null)
		{
			// False if only po.state has null name.
			if (this.state != null)
				return false;
		} 
		else 
		{
			// Both have non-null names to compare!
			if (!this.state.equals(po.state))
				return false;
		}

		// Zip 
		if (this.zip == null) 
		{
			// False if only this.zip has null name. Not equal.
			if (po.zip != null)
				return false;
		} 
		else if (po.zip == null)
		{
			// False if only po.zip has null name.
			if (this.zip != null)
				return false;
		} 
		else 
		{
			// Both have non-null names to compare!
			if (!this.zip.equals(po.zip))
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
	@Override
	public int hashCode()
	{
		final int prime = 17;
		int result = 1;
		
		// In this algorithm, based on Joshua Bloch's blog, if an attribute is an 
		// Object (i.e., String) return 0 if null or call hashCode() on it.
		// NOTE:  String.hashCode() returns the same int value for strings of the 
		// same value (i.e., "one" and "one") though they are different actual String objects).
		result = result * prime + ( (id == null) ? 0 : id.hashCode());
		result = result * prime + ( (name == null) ? 0 : name.hashCode());
		result = result * prime + ( (addressLine1 == null) ? 0 : addressLine1.hashCode());
		result = result * prime + ( (city == null) ? 0 : city.hashCode());
		result = result * prime + ( (state == null) ? 0 : state.hashCode());
		result = result * prime + ( (zip == null) ? 0 : zip.hashCode());
		return result;
		
	}  // end hashCode()

}  // end class Publisher
