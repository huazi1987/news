package com.news.common.json.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * long类型转换日期格式，精确单位秒，要显示正确日期还需要*1000
 * UTC时区转换为IST时区
 * @Description:
 * @author wanghz
 * @date 2018年1月4日
 */
public class SecondToDateSerializer extends JsonSerializer<Long>{
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

	@Override
	public void serialize(Long l, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
		gen.writeString((l!=null && l>0)?dateFormat.format(l.longValue() * 1000):"");
	}
}