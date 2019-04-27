/**
 * 
 */
package com.crossride.techtrial.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crossride.techtrial.dto.TopDriverDTO;
import com.crossride.techtrial.model.Ride;
import com.crossride.techtrial.repositories.RideRepository;

/**
 *
 */
@Service
@Transactional(readOnly = true)
public class RideServiceImpl implements RideService {

	@Autowired
	private RideRepository rideRepository;

	@Transactional
	public Ride save(Ride ride) {
		Ride rideExists = null;
		if (ride.getId() != null)
			rideExists = findById(ride.getId());
		// Persist only if Rider is new and endTime is grater than startTime
		if ((rideExists == null) && (ride.getEndTime().isAfter(ride.getStartTime())))
			return rideRepository.save(ride);
		else
			return null;
	}

	public Ride findById(Long rideId) {
		Optional<Ride> optionalRide = rideRepository.findById(rideId);
		if (optionalRide.isPresent()) {
			return optionalRide.get();
		} else
			return null;
	}

	@Override
	public List<TopDriverDTO> getTopDrivers(LocalDateTime startTime, LocalDateTime endTime, Long count) {
		return rideRepository.getTopDrivers(PageRequest.of(0, count.intValue()), startTime, endTime);
	}

}
