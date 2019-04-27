package com.crossride.techtrial.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.crossride.techtrial.model.Person;
import com.crossride.techtrial.repositories.PersonRepository;
import com.crossride.techtrial.service.PersonServiceImpl;

public class PersonServiceTest {
	@InjectMocks
	private PersonServiceImpl personService;

	@Mock
	private PersonRepository personRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetALLSuccess() {
		// Create some test data
		List<Person> personList = getEntityListStubData();

		when(personRepository.findAll()).thenReturn(personList);
		assertNotNull(personService.getAll());

		// Make sure personRepository findAll is called.
		verify(personRepository, times(1)).findAll();
	}

	@Test
	public void testFindByIdFound() {
		Person person = getEntityStubData();

		when(personRepository.findById(any(Long.class))).thenReturn(Optional.of(person));
		assertNotNull(personService.findById(any(Long.class)));

		// Make sure personRepository findbyId is called once.
		verify(personRepository, times(1)).findById(any(Long.class));
	}

	@Test
	public void testFindByIdNotFound() {
		Long id = Long.MAX_VALUE;

		when(personRepository.findById(id)).thenReturn(Optional.empty());
		assertNull(personService.findById(id));

		// Make sure personRepository findbyId is called once.
		verify(personRepository, times(1)).findById(id);
	}

	@Test
	public void testSaveWhenSuccess() {
		// Create some test data
		Person person = getEntityStubData();

		when(personRepository.save(any(Person.class))).thenReturn(person);
		assertNotNull(personService.save(person));

		// Make sure personRepository save is called once.
		verify(personRepository, times(1)).save(any(Person.class));
	}

	@Test
	public void testSaveWhenExistsNotSuccess() {
		// Create some test data
		Person person = getEntityStubData();

		when(personRepository.findById(any(Long.class))).thenReturn(Optional.of(person));
		when(personRepository.save(any(Person.class))).thenReturn(person);
		assertNull(personService.save(person));

		// Make sure personRepository save is called once.
		verify(personRepository, times(0)).save(any(Person.class));
		verify(personRepository, times(1)).findById(any(Long.class));
	}

	private List<Person> getEntityListStubData() {
		List<Person> list = new ArrayList<Person>();
		list.add(getEntityStubData());
		return list;
	}

	private Person getEntityStubData() {
		Person entity = new Person();

		entity.setId(1l);
		entity.setEmail("email@address.com");
		entity.setName("Name");
		entity.setRegistrationNumber("registrationNumber");
		return entity;
	}

}
