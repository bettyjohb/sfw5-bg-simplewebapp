// ***************************************************************************
// Class:  BookController 
// 
// Controller for Book entity (identified by @Controller / not extension).
//
// When an HTTP request arrives at server, will be handled by Spring provided 
// DispatcherServlet (front controller) that uses Handler Mapping to determine 
// which Controller to invoke (i.e. BookController, etc.).  
//
// @RequestMapping is used to map the HTTP Request Path to specific method on 
// the controller (for instance, can say all URL for that Web App ending with
// /hello will go to hello method on controller which might just return a 
// view name "hello" which ViewResolver will turn into hello.jsp. 
//
// Assignment 3 of 1-3 Combo:
//   Annotate - See #1-2 (@Controller, @RequestMapping) 
//   DI of Repository for access model - See #3
//   Controller methods - #4
// *************************************************************************** 
package guru.springframework5.assign1to3.simplewebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import guru.springframework5.assign1to3.simplewebapp.repositories.IBookRepository;

@Controller            // #1 - Tell Spring this is a Spring MVC Controller that can be invoked 
public class BookController {

	// -----------------------------------------------
	// #3 Attributes  
	// -----------------------------------------------

	// IBookRepository - Will be data injected by Spring so ready to use.  
	// Since final and initialized in constructor (below), tells Spring to inject 
	// instance of them when class is constructed.  
	// This repository will write/read the H2 in-memory database.  
	private final IBookRepository bookRepository;
	
	// -----------------------------------------------
	// #3 Constructor 
	//    HOW DI works here!
	//    BookController is a Spring managed component (controller).  
	//    When Spring creates the instance, it will inject an instance 
	//    of BookRepository in the class attribute. 
	// -----------------------------------------------
    public BookController(IBookRepository bRepos) {
    	this.bookRepository = bRepos;   
    }

	// -----------------------------------------------
	// #4 Controller Methods  
	//    Functionality provided to users of the controller/
	// -----------------------------------------------

	/**
	 * Request the set of all books and return to "books/list" view to be displayed.
	 *  
	 * The Model that is a parameter of method is updated to hold Set of Books and 
	 * changes are seen by the calling component (i.e., DispatcherServlet).  The 
	 * return value is the view to use (see below).       
	 *  
	 * @return  books/list (since using Thymeleaf, will show list.html within books
	 *          folder under resources ("resources/templates/books/list.html").  
	 *          Recall when JSP, ViewResolver put pre/suffix around core name. 
	 */
	@RequestMapping("/books")    // #2 - Map method to handle URL ending with path /books. 
	public String getBooks(Model model) {
		// Recall BookRepository interface extends CrudRepository which provides findAll.
		// Since BookRepository was defined as <Book, Long>, find all returns ALL Books. 
		model.addAttribute("books", bookRepository.findAll());
		return "books/list";  
	}
}  // end BookController 
