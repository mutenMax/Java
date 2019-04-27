package com.crossride.techtrial;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crossride.techtrial.exceptions.GlobalExceptionHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class AbstractTest {

	protected static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	/*
	 * Pattern used to format date
	 */
	protected static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 * 
	 * @param obj
	 *            The Object to map.
	 * @return A String of JSON.
	 * @throws JsonProcessingException
	 *             Thrown if an error occurs while mapping.
	 */
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper.writeValueAsString(obj);
	}

	/**
	 * Maps a String of JSON into an instance of a Class of type T. Uses a Jackson
	 * ObjectMapper.
	 * 
	 * @param json
	 *            A String of JSON.
	 * @param clazz
	 *            A Class of type T. The mapper will attempt to convert the JSON
	 *            into an Object of this Class type.
	 * @return An Object of type T.
	 * @throws JsonParseException
	 *             Thrown if an error occurs while mapping.
	 * @throws JsonMappingException
	 *             Thrown if an error occurs while mapping.
	 * @throws IOException
	 *             Thrown if an error occurs while mapping.
	 */
	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.registerModule(new JavaTimeModule());

		return objectMapper.readValue(json, clazz);
	}

}
