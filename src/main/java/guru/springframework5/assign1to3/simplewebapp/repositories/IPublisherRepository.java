package guru.springframework5.assign1to3.simplewebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import guru.springframework5.assign1to3.simplewebapp.domain.Publisher;

/**
 * IPublisherRepository - Interface implements CrudRepository to provide CRUD options  
 *                        for Publisher entity objects with primary key of type Long. 
 *                        <Publisher, Long>
 *                     
 * CrudRepository interface provides:  save, saveAll, findById, existsById, 
 *                                     findAll, findById, count, deleteById, delete, etc.
 *                                     
 * At runtime, Spring provides implementations for these methods (we don't have to). 
 * This is the redundant JDBC / SQL source we can skip. 
 */
public interface IPublisherRepository extends CrudRepository<Publisher, Long> {

	// No methods to declare.  Just use default methods provided by CRUDRepoistory.
	// Spring provides implementation at runtime.  You do NOT need IMPL class. 
	
}  // end interface IPublisherRepository
