/**
 * BootStrapData - Create dummy data for the H2 in-memory DB.
 * 
 * Marked @Component so Spring Container will create an instance when it starts up.
 *                      
 * Implements CommandLineRunner so has run() method, within which you create dummy data and 
 * save to the H2 in-memory DB. The run() method uses IAuthorRepository and IBookRepository interfaces 
 * created to do that.  Therefore, need to do "data injection" to initialize those 
 * repository class members so ready to use in run().  
 * [See #1-5]
 * 
 * Assignment 3 of 1-3 Combo: 
 * #A - Include dummy data associating Publisher with Book. 
 */
package guru.springframework5.assign1to3.simplewebapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework5.assign1to3.simplewebapp.domain.Author;
import guru.springframework5.assign1to3.simplewebapp.domain.Book;
import guru.springframework5.assign1to3.simplewebapp.domain.Publisher;
import guru.springframework5.assign1to3.simplewebapp.repositories.IAuthorRepository;
import guru.springframework5.assign1to3.simplewebapp.repositories.IBookRepository;
import guru.springframework5.assign1to3.simplewebapp.repositories.IPublisherRepository;

@Component      // #1 @Component so Spring container detects and will create an instance 
                //    Tell Spring to look either with XML, @, or Java configuration.
                // #2 Implements CommandLineRunner.  When instance created, will invoke run(). 
public class BootStrapData implements CommandLineRunner {

	// -----------------------------------------------
	// Attributes  
	// -----------------------------------------------

	// #3 Include IAuthorRepository, IBookRepository, and IPublisher.  
	//     They will be data injected by Spring so ready to use.  Since final 
	//     and initialized in constructor, tells Spring to inject instance of them
	//     when class is constructed.  
	//     These repositories write/read the H2 in-memory database.  
	private final IAuthorRepository authorRepository;
	private final IBookRepository bookRepository;
	private final IPublisherRepository publisherRepository;
	
	// -----------------------------------------------
	// #4 Constructor 
	//    Since initialize authorRepository, bookRepository, PublisherRepository 
	//    in constructor, tells Spring must inject instance of repositories when 
	//    class is constructed.  
	// -----------------------------------------------
    public BootStrapData(IAuthorRepository aRepos, IBookRepository bRepos, IPublisherRepository pRepos) {
    	this.authorRepository = aRepos;
    	this.bookRepository = bRepos;
    	this.publisherRepository = pRepos;    
    }
    
	// -----------------------------------------------
	// Implementation of CommandLineRunner  
	// -----------------------------------------------

	// #5 Override run() so when container creates instance, run() will create dummy data. 
	@Override
	public void run(String... args) throws Exception {
		
		// --------------------------------------------
		// Create instance of dummy data #1 - Author and Book
		// --------------------------------------------
		Author author1 = new Author ("Eric", "Evans");
		Book book1 = new Book ("Domain Driven Design", "123123");
		// Author will reference book, and book will reference that author. 
		author1.getBooks().add(book1);
		book1.getAuthors().add(author1);

		// --------------------------------------------
		// Create instance of dummy data #2 - Author and Book
		// --------------------------------------------
		Author author2 = new Author ("Rod", "Johnson");
		Book book2 = new Book ("J2EE Development wihtout EJB", "22334455");
		// Author2 will reference book2, and book2 will reference that author2. 
		author2.getBooks().add(book2);
		book2.getAuthors().add(author2);

		// --------------------------------------------
		// Create instance of dummy data #3 - Publisher
		// Make it the publisher of the two books AND
		// add the books to the set of books published by that Publisher. 
		// --------------------------------------------
		Publisher publisher1 = new Publisher("Write Way Printing", "123 One Way", "Los Angeles", "CA", "98765");

        // COMMENT 1:  HAVE TO SAVE PUBLISHER HERE TO AVOID "CRASH" AT COMMENT 3
		publisherRepository.save(publisher1);  // THE GOLDEN FIX!! 
		book1.setPublisher(publisher1);    
		book2.setPublisher(publisher1); 
		publisher1.getBooks().add(book1);
		publisher1.getBooks().add(book2);  

		// --------------------------------------------
		// Save created data to H2 in-memory database using Repository objects. 
		// These repository objects are private attributes initialized via DI above.  
		// Behind the scenes, uses Hibernate to save to H2 database (recall included 
		// H2 when picked components to include in project with Initializr.  
		// --------------------------------------------
		
        // COMMENT 2: 
		// Tried to fix exception at COMMENT 3 by adding this line to save Publisher.  
		// Still threw an exception so saved Publisher at COMMENT 1.   
//		 publisherRepository.save(publisher1); 
		
		authorRepository.save(author1);
		// COMMENT 3:  Exception at next line if don't save Publisher at COMMENT 1
		bookRepository.save(book1);   
		authorRepository.save(author2);
		bookRepository.save(book2);
		publisherRepository.save(publisher1);
		
		// Output some FYI.  Recall, BookRepository implements CrudRepository.  
		// At runtime, Spring provides implementation of CrudRepository (you don't). 
		// One method of CrudRepository is count() which tells how many rows (books) in DB. 
		System.out.println("Started in BootStrapData");
		System.out.println("Number of Books: " + bookRepository.count());   // Will be 2 books!
		System.out.println("Number of Publishers: " + publisherRepository.count());   // Will be 1 publisher!
		System.out.println("Publisher number of books:  " + publisher1.getBooks().size());  // Will be 2 books. 
	}  // end run()

}  // end IPublisherRepository
