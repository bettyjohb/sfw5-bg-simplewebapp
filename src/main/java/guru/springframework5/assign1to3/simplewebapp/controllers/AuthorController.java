//***************************************************************************
//Class:  AuthorController 
//
//Controller for Author entity (identified by @Controller / not extension).
//
//When an HTTP request arrives at server, will be handled by Spring provided 
//DispatcherServlet (front controller) that uses Handler Mapping to determine 
//which Controller to invoke (i.e. BookController, etc.).  
//
//@RequestMapping is used to map the HTTP Request Path to specific method on 
//the controller (for instance, can say all URL for that Web App ending with
///hello will go to hello method on controller which might just return a 
//view name "hello" which ViewResolver will turn into hello.jsp. 
//
//Assignment 3 of 1-3 Combo:
//Annotate - See #1-2 (@Controller, @RequestMapping) 
//DI of Repository for access model - See #3
//Controller methods - #4
//*************************************************************************** 
package guru.springframework5.assign1to3.simplewebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import guru.springframework5.assign1to3.simplewebapp.repositories.IAuthorRepository;

@Controller        // #1 - Tell Spring this is a Spring MVC Controller 
public class AuthorController {

	// -----------------------------------------------
	// #3 Attributes  
	// -----------------------------------------------

	// IAuthorRepository - Will be data injected by Spring so ready to use.  
	// Since final and initialized in constructor (below), tells Spring to inject 
	// instance of them when class is constructed.  
	// This repository will write/read the H2 in-memory database.  
	private final IAuthorRepository authorRepository;

	// -----------------------------------------------
	// #3 Constructor - To force DI of AuthorRepository 
	//    
	//    AuthorController is a Spring managed component (controller).  
	//    When Spring creates the instance, it will inject an instance 
	//    of AuthorRepository in the class attribute. 
	// -----------------------------------------------
	public AuthorController(IAuthorRepository aRepos) {
		this.authorRepository = aRepos;   
	}

	// -----------------------------------------------
	// #4 Controller Methods  
	//    Functionality provided to users of the controller/
	// -----------------------------------------------

	/**
	 * Request the set of all authors and return to "authors/list" view to be displayed.
	 *  
	 * The Model that is a parameter of method is updated to hold Set of Authors and 
	 * changes are seen by the calling component (i.e., DispatcherServlet).  The 
	 * return value is the view to use (see below).       
	 *  
	 * @return  authors/list (since using Thymeleaf, will show list.html within books
	 *          folder under resources ("resources/templates/authors/list.html").  
	 *          Recall when JSP, ViewResolver put pre/suffix around core name. 
	 */
	@RequestMapping("/authors")    // #2 - Map method to handle localhost:8080/authors. 
	public String getAuthors(Model model) {
		// Recall AuthorRepository interface extends CrudRepository which provides findAll.
		// Since AuthorRepository was defined as <Author, Long>, find all returns ALL Authors. 
		model.addAttribute("authors", authorRepository.findAll());
		return "authors/list";  
	}

}  // end class AuthorController
