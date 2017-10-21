package com.nhsoft.report.util;

import com.google.gson.Gson;
import com.nhsoft.module.report.dto.SystemBookProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class ServiceDeskUtil {
	private static final Logger logger = LoggerFactory.getLogger(ServiceDeskUtil.class);

	protected static Log LOG = LogFactory.getLog(ServiceDeskUtil.class);
	
	private static String url;
	
	private static RestTemplate restTemplate;
	private static Map<String, SystemBookProxy> map = new HashMap<String, SystemBookProxy>();
	
	@Value("${serviceDesk.url}")
	public void setUrl(String url) {
		
		ServiceDeskUtil.url = url;
	}
	
	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		ServiceDeskUtil.restTemplate = restTemplate;
	}
	
	private static String constructHTTPGetParam(Map<String, Object> map) {
		Set<String> keySet = map.keySet();
		Iterator<String> keyIterator = keySet.iterator();
		StringBuffer sb = new StringBuffer();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			String value = map.get(key).toString();
			if (sb.toString().isEmpty()) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append(key).append("=").append(value);
		}
		return sb.toString();
	}
	
	public static void clear(String systemBookCode){map.remove(systemBookCode);}
	
	
	public static SystemBookProxy getSystemBookProxy(String systemBookCode) {
		SystemBookProxy systemBookProxy = map.get(systemBookCode);
		if(systemBookProxy != null){
			return systemBookProxy;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("centerId", systemBookCode);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		try {
			
			ResponseEntity<String> responseEntity = restTemplate.exchange(url
					+ "api/systemBookProxy/get/{centerId}", HttpMethod.GET, requestEntity, String.class, map);
			if (responseEntity.getBody() == null) {
				return null;
			}
			String result = new String(responseEntity.getBody().getBytes("ISO-8859-1"), "utf-8");
			Gson gson = AppUtil.toBuilderGson();
			systemBookProxy = gson.fromJson(result, SystemBookProxy.class);
			
			if(systemBookProxy != null){
				map.put(systemBookCode, systemBookProxy);
			}
			return systemBookProxy;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static void main(String[] args) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_HTML);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("centerId", "4344");
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.exchange("http://csb.nhsoft.cn/ServiceDesk/"
					+ "api/systemBookProxy/get/{centerId}", HttpMethod.GET, requestEntity, String.class, map);
			String result = new String(responseEntity.getBody().getBytes("ISO-8859-1"), "utf-8");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
