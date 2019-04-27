/**
 * 
 */
package com.crossride.techtrial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crossride.techtrial.model.Person;
import com.crossride.techtrial.service.PersonService;

/**
 * 
 */

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;

	// Post for INSERTS Only

	/**
	 * Web service endpoint to create a single Person entity. The HTTP request body
	 * is expected to contain a Person object in JSON format. The Person is
	 * persisted in the data repository.
	 * 
	 * If created successfully, the persisted Person is returned as JSON with HTTP
	 * status 201.
	 * 
	 * 
	 * @param person
	 *            The Person object to be created.
	 * @return A ResponseEntity containing a single Person object, if created
	 *         successfully, and a HTTP status code as described in the method
	 *         comment.
	 */
	@PostMapping(path = "/api/person")
	public ResponseEntity<Person> register(@RequestBody Person p) {

		Person createdPerson = personService.save(p);
		if (createdPerson != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
		return ResponseEntity.badRequest().build();
	}

	/**
	 * Web service endpoint to fetch all Person entities. The service returns the
	 * collection of Person entities as JSON.
	 * 
	 * @return A ResponseEntity containing a List of Person objects.
	 */

	@GetMapping(path = "/api/person")
	public ResponseEntity<List<Person>> getAllPersons() {
		return ResponseEntity.ok(personService.getAll());
	}

	/**
	 * Web service endpoint to fetch single Person entity by a primary key
	 * identifier.
	 * 
	 * If found, the Person is returned as JSON with HTTP status 200.
	 * 
	 * If not found, the service returns an empty response body with HTTP status
	 * 404.
	 * 
	 * @param id
	 *            A Long URL path variable containing the Person primary key
	 *            identifier
	 * 
	 * @return A ResponseEntity containing a single Person object, and HTTP Status
	 *         code as defined in the method comment.
	 * 
	 */
	@GetMapping(path = "/api/person/{person-id}")
	public ResponseEntity<Person> getPersonById(@PathVariable(name = "person-id", required = true) Long personId) {
		Person person = personService.findById(personId);
		if (person != null) {
			return ResponseEntity.ok(person);
		}
		return ResponseEntity.notFound().build();
	}

}
