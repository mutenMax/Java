/**
 * 
 */
package com.crossride.techtrial.service;

import java.time.LocalDateTime;
import java.util.List;

import com.crossride.techtrial.dto.TopDriverDTO;
import com.crossride.techtrial.model.Ride;

/**
 * RideService for rides.
 * 
 *
 */
public interface RideService {

	public Ride save(Ride ride);

	public Ride findById(Long rideId);

	public List<TopDriverDTO> getTopDrivers(LocalDateTime startTime, LocalDateTime endTime, Long count);

}
