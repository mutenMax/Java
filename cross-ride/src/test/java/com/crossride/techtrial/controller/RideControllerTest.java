package com.crossride.techtrial.controller;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.crossride.techtrial.AbstractTest;
import com.crossride.techtrial.controller.RideController;
import com.crossride.techtrial.dto.TopDriverDTO;
import com.crossride.techtrial.model.Person;
import com.crossride.techtrial.model.Ride;
import com.crossride.techtrial.service.RideService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RideControllerTest extends AbstractTest {

	@Mock
	private RideService rideService;

	@InjectMocks
	private RideController rideController;

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
	}

	@Test
	public void testFindRideByIdWhenFound() throws Exception {
		// Create some test data
		Ride ride = getEntityStubData();
		String startTime = ride.getStartTime().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		String endTime = ride.getEndTime().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));

		// Stub the RideService.findById method return value
		when(rideService.findById(any(Long.class))).thenReturn(ride);

		mockMvc.perform(get("/api/ride/{ride-id}", any(Long.class))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.startTime", is(startTime)))
				.andExpect(jsonPath("$.endTime", is(endTime))).andExpect(jsonPath("$.distance", is(30)));

		verify(rideService, times(1)).findById(any(Long.class));
		verifyNoMoreInteractions(rideService);
	}

	@Test
	public void testFindRideByIdWhenNotFound() throws Exception {

		// Stub the RideService.findById method return value
		when(rideService.findById(any(Long.class))).thenReturn(null);
		mockMvc.perform(get("/api/ride/{ride-id}", any(Long.class))).andExpect(status().isNotFound());

		verify(rideService, times(1)).findById(any(Long.class));
		verifyNoMoreInteractions(rideService);
	}

	@Test
	public void testCreateNewRideSuccess() throws Exception {
		Ride ride = getEntityStubData();
		doReturn(ride).when(rideService).save(any(Ride.class));

		mockMvc.perform(post("/api/ride").contentType(MediaType.APPLICATION_JSON).content(mapToJson(ride)))
				.andExpect(status().isCreated()); // Verify that the HTTP status code is 201 (CREATED).

		verify(rideService, times(1)).save(any(Ride.class));
		verifyNoMoreInteractions(rideService);
	}

	@Test
	public void testCreateNewRideNotSuccess() throws Exception {
		Ride ride = getEntityStubData();
		when(rideService.save(any(Ride.class))).thenReturn(null);

		mockMvc.perform(post("/api/ride").contentType(MediaType.APPLICATION_JSON).content(mapToJson(ride)))
				.andExpect(status().isBadRequest());

		verify(rideService, times(1)).save(any(Ride.class));
		verifyNoMoreInteractions(rideService);
	}

	@Test
	public void testGetTopDrivers() throws Exception {
		List<TopDriverDTO> topDriversList = new ArrayList<>();

		Long count = new Long(5);
		String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		String endTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));

		doReturn(topDriversList).when(rideService).getTopDrivers(any(LocalDateTime.class), any(LocalDateTime.class),
				any(Long.class));

		mockMvc.perform(get("/api/top-rides/").param("max", String.valueOf(count)).param("startTime", startTime)
				.param("endTime", endTime)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

		verify(rideService, times(1)).getTopDrivers(any(LocalDateTime.class), any(LocalDateTime.class),
				any(Long.class));
		verifyNoMoreInteractions(rideService);
	}

	private Ride getEntityStubData() {
		Ride entity = new Ride();

		Person driver = new Person(1L, "Driver1", "one@address.com", "Reg-1");
		Person rider = new Person(2L, "Rider1", "two@address.com", "Reg-2");

		entity.setId(1l);
		entity.setStartTime(LocalDateTime.now());
		entity.setEndTime(LocalDateTime.now().plusMinutes(1));
		entity.setDistance(30L);
		entity.setDriver(driver);
		entity.setRider(rider);
		return entity;
	}

}
