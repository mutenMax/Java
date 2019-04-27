/**
 * 
 */
package com.crossride.techtrial.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.crossride.techtrial.model.Person;

/**
 * Person repository for basic operations on Person entity.
 * 
 */
@RestResource(exported = false)
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
	Optional<Person> findById(Long id);
}
