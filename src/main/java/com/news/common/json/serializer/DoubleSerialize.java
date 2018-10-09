package com.news.common.json.serializer;

import java.io.IOException;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DoubleSerialize extends JsonSerializer<Double> {
	
	private DecimalFormat df = new DecimalFormat("##.00");  

	@Override
	public void serialize(Double d, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		gen.writeString(df.format(d));
	}
}
