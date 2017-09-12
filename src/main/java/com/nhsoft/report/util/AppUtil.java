package com.nhsoft.report.util;


import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SuppressWarnings("rawtypes")
public class AppUtil {
	private static final Logger logger = LoggerFactory.getLogger(AppUtil.class);
	public static String getIntegerParmeList(List<Integer> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append(list.get(i));
			} else {
				sb.append(list.get(i) + ",");
			}
			
		}
		sb.append(") ");
		return sb.toString();
	}
	
	public static String getShortParmeList(List<Short> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append(list.get(i));
			} else {
				sb.append(list.get(i) + ",");
			}
			
		}
		sb.append(") ");
		return sb.toString();
	}
	
	public static String getStringParmeList(List<String> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append("'" + list.get(i) + "'");
			} else {
				sb.append("'" + list.get(i) + "',");
			}
			
		}
		sb.append(") ");
		return sb.toString();
	}
	
	public static String getStringParmeArray(String[] array) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1) {
				sb.append("'" + array[i] + "'");
			} else {
				sb.append("'" + array[i] + "',");
			}
			
		}
		sb.append(") ");
		return sb.toString();
	}
	
	public static Gson toBuilderGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.disableHtmlEscaping();
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			
			@Override
			public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
					throws JsonParseException {
				try {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json.getAsString());
				} catch (ParseException e) {
					return null;
				}
			}
		});
		gsonBuilder.registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {
			
			@Override
			public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				try {
					return Integer.parseInt(json.getAsString());
				} catch (Exception e) {
					return null;
				}
			}
			
		});
		gsonBuilder.registerTypeAdapter(BigDecimal.class, new JsonDeserializer<BigDecimal>() {
			
			@Override
			public BigDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				try {
					return new BigDecimal(json.getAsString());
				} catch (Exception e) {
					return null;
				}
			}
			
		});
		return gsonBuilder.create();
	}
	
}
