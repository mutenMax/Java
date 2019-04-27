package com.crossride.techtrial.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.crossride.techtrial.dto.TopDriverDTO;
import com.crossride.techtrial.model.Person;
import com.crossride.techtrial.model.Ride;
import com.crossride.techtrial.repositories.RideRepository;
import com.crossride.techtrial.service.RideServiceImpl;

public class RiderServiceTest {

	@InjectMocks
	private RideServiceImpl rideService;

	@Mock
	private RideRepository rideRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveWhenEndDateLessThanStartDateNotSuccess() {
		// Create some test data
		Ride ride = getEntityStubData();

		// Set endTime less that startTime
		ride.setEndTime(LocalDateTime.now().minusMinutes(10));

		when(rideRepository.save(any(Ride.class))).thenReturn(ride);
		assertNull(rideService.save(ride));

		// Make sure rideRepository save is not called and control returns after endTime
		// check.
		verify(rideRepository, times(0)).save(any(Ride.class));
	}

	@Test
	public void testSaveWhenEndDateGreaterThanStartDateSuccess() {
		// Create some test data
		Ride ride = getEntityStubData();

		when(rideRepository.save(any(Ride.class))).thenReturn(ride);
		assertNotNull(rideService.save(ride));

		// Make sure rideRepository save is called once.
		verify(rideRepository, times(1)).save(any(Ride.class));
	}

	@Test
	public void testSaveWhenExistsNotSuccess() {
		// Create some test data
		Ride ride = getEntityStubData();

		when(rideRepository.findById(any(Long.class))).thenReturn(Optional.of(ride));
		when(rideRepository.save(any(Ride.class))).thenReturn(ride);
		assertNull(rideService.save(ride));

		// Make sure rideRepository save is called once.
		verify(rideRepository, times(0)).save(any(Ride.class));
		verify(rideRepository, times(1)).findById(any(Long.class));
	}

	@Test
	public void testFindByIdNotFound() {
		Long id = Long.MAX_VALUE;

		when(rideRepository.findById(id)).thenReturn(Optional.empty());
		assertNull(rideService.findById(id));

		// Make sure rideRepository findbyId is called once.
		verify(rideRepository, times(1)).findById(id);
	}

	@Test
	public void testFindByIdFound() {
		Ride ride = getEntityStubData();

		when(rideRepository.findById(any(Long.class))).thenReturn(Optional.of(ride));
		assertNotNull(rideService.findById(any(Long.class)));

		// Make sure rideRepository findbyId is called once.
		verify(rideRepository, times(1)).findById(any(Long.class));
	}

	@Test
	public void testgetTopDriversSuccess() {
		Integer count = 5;
		List<TopDriverDTO> topDriverList = new ArrayList<>();

		when(rideRepository.getTopDrivers(any(PageRequest.of(0, count.intValue()).getClass()), any(LocalDateTime.class),
				any(LocalDateTime.class))).thenReturn(topDriverList);
		assertNotNull(rideService.getTopDrivers(LocalDateTime.now(), LocalDateTime.now(), count.longValue()));

		// Make sure rideRepository getTopDrivers is called once.
		verify(rideRepository, times(1)).getTopDrivers(any(Pageable.class), any(LocalDateTime.class),
				any(LocalDateTime.class));
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
