package com.crossride.techtrial.exceptions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crossride.techtrial.AbstractTest;
import com.crossride.techtrial.controller.PersonController;
import com.crossride.techtrial.exceptions.GlobalExceptionHandler;
import com.crossride.techtrial.model.Person;

public class GlobalExceptionHandlerTest extends AbstractTest {

	@Mock
	private PersonController personController;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(personController).setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}

	@Test
	public void testWhenInvalidRequest() throws Exception {
		Person person = getEntityStubData();
		// Set emailAddress to raise exception
		person.setEmail(null);
		when(personController.register(any(Person.class)))
				.thenThrow(new RuntimeException("Exception Test From GlobalExceptionHandlerTest "));

		mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).content(mapToJson(person)))
				.andExpect(status().is(400)).andReturn();

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