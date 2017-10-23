package com.nhsoft.module.report;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class MyJacksonObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1728618651865933486L;

	public MyJacksonObjectMapper() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//
//			@Override
//			public void serialize(Object arg0, JsonGenerator jsonGenerator, SerializerProvider arg2) throws IOException,
//					JsonProcessingException {
//				jsonGenerator.writeString("");
//			}
//		});
		//NULL的属性不序列化
		setSerializationInclusion(Include.NON_NULL);
		setDateFormat(dateFormat);
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

	}
	
	
	
		
}
