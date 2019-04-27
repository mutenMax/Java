/**
 * 
 */
package com.crossride.techtrial.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crossride.techtrial.AbstractTest;
import com.crossride.techtrial.controller.PersonController;
import com.crossride.techtrial.model.Person;
import com.crossride.techtrial.service.PersonService;

/**
 * @author kshah
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest extends AbstractTest {

	@Mock
	private PersonService personService;

	@InjectMocks
	private PersonController personController;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
	}

	@Test
	public void testGetPersons() throws Exception {
		List<Person> persons = getEntityListStubData();

		// Stub the PersonService.getAll method return value
		when(personService.getAll()).thenReturn(persons);

		mockMvc.perform(get("/api/person")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("Name"))).andExpect(jsonPath("$[0].email", is("email@address.com")))
				.andExpect(jsonPath("$[0].registrationNumber", is("registrationNumber")));

		// Verify the PersonService.getAll method was invoked once
		verify(personService, times(1)).getAll();
		verifyNoMoreInteractions(personService);
	}

	@Test
	public void testGetPersonByIdWhenFound() throws Exception {
		Person person = getEntityStubData();

		// Stub the PersonService.findById method return value
		when(personService.findById(any(Long.class))).thenReturn(person);

		mockMvc.perform(get("/api/person/{person-id}", any(Long.class))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Name")))
				.andExpect(jsonPath("$.email", is("email@address.com")))
				.andExpect(jsonPath("$.registrationNumber", is("registrationNumber")));

		// Verify the PersonService.findById method was invoked once
		verify(personService, times(1)).findById(any(Long.class));
		verifyNoMoreInteractions(personService);
	}

	@Test
	public void testGetPersonByIdWhenNotFound() throws Exception {
		when(personService.findById(any(Long.class))).thenReturn(null);
		mockMvc.perform(get("/api/person/{person-id}", any(Long.class))).andExpect(status().isNotFound());

		// Verify the PersonService.findById method was invoked once
		verify(personService, times(1)).findById(any(Long.class));
		verifyNoMoreInteractions(personService);
	}

	@Test
	public void testRegisterWhenSuccess() throws Exception {
		// Create some test data
		Person person = getEntityStubData();
		doReturn(person).when(personService).save(any(Person.class));

		mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).content(mapToJson(person)))
				.andExpect(status().isCreated()); // Verify that the HTTP status code is 201 (CREATED).

		verify(personService, times(1)).save(person);
		verifyNoMoreInteractions(personService);
	}

	@Test
	public void testRegisterWhenExistsNotSuccess() throws Exception {
		Person person = getEntityStubData();
		when(personService.save(any(Person.class))).thenReturn(null);

		mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).content(mapToJson(person)))
				.andExpect(status().isBadRequest());

		verify(personService, times(1)).save(any(Person.class));
		verifyNoMoreInteractions(personService);
	}

	protected List<Person> getEntityListStubData() {
		List<Person> list = new ArrayList<Person>();
		list.add(getEntityStubData());
		return list;
	}

	protected Person getEntityStubData() {
		Person entity = new Person();

		entity.setId(1l);
		entity.setEmail("email@address.com");
		entity.setName("Name");
		entity.setRegistrationNumber("registrationNumber");
		return entity;
	}

}
