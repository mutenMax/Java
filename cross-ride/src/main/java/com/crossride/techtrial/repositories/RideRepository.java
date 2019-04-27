/**
 * 
 */
package com.crossride.techtrial.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.crossride.techtrial.dto.TopDriverDTO;
import com.crossride.techtrial.model.Ride;

/**
 *
 */
@RestResource(exported = false)
public interface RideRepository extends CrudRepository<Ride, Long> {

	@Query(value = "SELECT " + "new com.crossride.techtrial.dto.TopDriverDTO(r.driver.name, r.driver.email, "
			+ "round(sum((unix_timestamp(r.endTime) - unix_timestamp(r.startTime))/60)), "
			+ "round(max((unix_timestamp(r.endTime) - unix_timestamp(r.startTime))/60)), " + "avg(r.distance)) "
			+ "from Ride r " + "where r.startTime >= :startTime " + "and r.endTime <= :endTime "
			+ "group by r.driver.id " + "order by count(r.driver.id) desc")
	List<TopDriverDTO> getTopDrivers(Pageable page, @Param("startTime") LocalDateTime startTime,
			@Param("endTime") LocalDateTime endTime);

}
