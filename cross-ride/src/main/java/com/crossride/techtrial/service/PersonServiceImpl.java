/**
 * 
 */
package com.crossride.techtrial.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crossride.techtrial.model.Person;
import com.crossride.techtrial.repositories.PersonRepository;

/**
 *
 */
@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.crossride.techtrial.service.PersonService#getAll()
	 */
	@Override
	public List<Person> getAll() {
		List<Person> personList = new ArrayList<>();
		personRepository.findAll().forEach(personList::add);
		return personList;

	}

	@Transactional
	public Person save(Person p) {
		Person personExists = null;
		if (p.getId() != null)
			personExists = findById(p.getId());
		// Persist only if Person is new.
		if (personExists == null)
			return personRepository.save(p);
		else
			return null;
	}

	@Override
	public Person findById(Long personId) {
		Optional<Person> dbPerson = personRepository.findById(personId);
		return dbPerson.orElse(null);
	}

}
