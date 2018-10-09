package com.news.common.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapper extends ObjectMapper {
	
	private static final long serialVersionUID = 1L;
	private static final JacksonMapper instance = new JacksonMapper();
	
	private JacksonMapper() {}
	
	public static JacksonMapper getInstance(){
		return instance;
	}
	
	@Override
	public String writeValueAsString(Object value){
		try {
			return super.writeValueAsString(value);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
