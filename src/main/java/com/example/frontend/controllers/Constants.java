package com.example.frontend.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.client.HttpClients;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.frontend.MyResponseErrorHandler;
import com.example.frontend.security.UserContext;

@Component
public class Constants {

	public static String files_upload_path;

	public static String base_url;

	@Value("${BASE_URL}")
	public void setBaseURL(String baseURL) {
		base_url = baseURL;
	}

	@Value("${ATTACHMENTS_URL}")
	public void setAttachmentURL(String attachmentsURL) {
		files_upload_path = attachmentsURL;
	}

	private static String httpRequest(String url, LinkedMultiValueMap<String, Object> params, HttpMethod method,
			Map<String, String> headersMap) {

		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				HttpClients.createDefault());

		RestTemplate restTemplate = new RestTemplate(requestFactory);

		restTemplate.setErrorHandler(new MyResponseErrorHandler());

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();

		if (headersMap != null) {
			headers.setAll(headersMap);
		}

		HttpEntity<?> request = new HttpEntity<>(params, headers);

		HttpEntity<String> response = restTemplate.exchange(url, method, request, String.class);

		return response.getBody();
	}

	public static String antHttpRequest(String url, LinkedMultiValueMap<String, String> requestData, HttpMethod method,
			Map<String, String> headersMap) {

		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		RestTemplate restTemplate = new RestTemplate(requestFactory);

		restTemplate.setErrorHandler(new MyResponseErrorHandler());

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();

		if (headersMap != null) {
			headers.setAll(headersMap);
		}

		HttpEntity<?> request = new HttpEntity<>(requestData, headers);

		HttpEntity<String> response = restTemplate.exchange(url, method, request, String.class);

		return response.getBody();
	}

	public static String postFormData(String url, LinkedMultiValueMap<String, Object> params) {
		Map<String, String> headersMap = new HashMap<>();
		headersMap.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE.toString());
		headersMap.put("Authorization", "Bearer " + getJwtToken());

		return httpRequest(url, params, HttpMethod.POST, headersMap);
	}

	public static String putFormData(String url, LinkedMultiValueMap<String, Object> params) {
		Map<String, String> headersMap = new HashMap<>();
		headersMap.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE.toString());
		headersMap.put("Authorization", "Bearer " + getJwtToken());

		return httpRequest(url, params, HttpMethod.PUT, headersMap);
	}

	public static JSONObject getJsonObject(String url) {

		Map<String, String> headersMap = new HashMap<>();
		headersMap.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE.toString());
		headersMap.put("Authorization", "Bearer " + getJwtToken());

		String response = httpRequest(url, null, HttpMethod.GET, headersMap);

		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject = new JSONObject(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public static String postMultipartFormData(String url, LinkedMultiValueMap<String, Object> params,
			ByteArrayResource file) {

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.setErrorHandler(new MyResponseErrorHandler());

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();

		Map<String, String> map = new HashMap<String, String>();

		map.put("Content-Type", MediaType.MULTIPART_FORM_DATA.toString());
		map.put("Authorization", "Bearer " + getJwtToken());

		headers.setAll(map);
		params.add("file", file);

		HttpEntity<?> request = new HttpEntity<>(params, headers);

		HttpEntity<String> response = restTemplate.postForEntity(url, request, String.class);

		return response.getBody();
	}

	public static String putMultipartFormData(String url, LinkedMultiValueMap<String, Object> params) {

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.setErrorHandler(new MyResponseErrorHandler());

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();

		Map<String, String> map = new HashMap<String, String>();

		map.put("Content-Type", MediaType.MULTIPART_FORM_DATA.toString());
		map.put("Authorization", "Bearer " + getJwtToken());

		headers.setAll(map);

		HttpEntity<?> request = new HttpEntity<>(params, headers);

		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

		return response.getBody();
	}

	public static BigInteger commaRemover(String data) {

		BigInteger formattedAmount = new BigInteger(data.replaceAll(",", ""));
		return formattedAmount;
	}

	public static String loginRequest(String url, LinkedMultiValueMap<String, Object> params) {

		Map<String, String> headersMap = new HashMap<>();

		headersMap.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE.toString());

		return httpRequest(url, params, HttpMethod.POST, headersMap);
	}

	public static String getJwtToken() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			UserContext userContext = (UserContext) authentication.getPrincipal();
			return userContext.getToken();
		}

		return null;
	}

	public static JSONObject jsonObjectCreater(LinkedMultiValueMap<String, Object> LinkedValueObject)
			throws JSONException {

		JSONObject createdJsonObject = new JSONObject();

		if (!LinkedValueObject.equals(null)) {
			createdJsonObject.put("id", "");

			for (String k : LinkedValueObject.keySet()) {
				if (k.equals("jumuiyaId")) {
					createdJsonObject.put(k, LinkedValueObject.get(k));
				} else {
					createdJsonObject.put(k, LinkedValueObject.getFirst(k));
				}
			}
		}
		return createdJsonObject;
	}

	public static String getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) auth.getPrincipal(); // get logged in username
		return userContext.toString();

	}

	public static String generateReport(String url, HttpServletResponse res, Object object) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

		restTemplate.setErrorHandler(new MyResponseErrorHandler());

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		headers.add("Authorization", "Bearer " + getJwtToken());

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class, "1");

		if (response.getStatusCode() == HttpStatus.OK) {

			if (response.getBody() != null) {

				InputStream inputStream = new ByteArrayInputStream(response.getBody());

				try {

					if (object.equals("pdf")) {
						res.setContentType("application/pdf");
						res.addHeader("Content-Disposition", "inline");
					}

					if (object.equals("excel")) {

						res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
						res.addHeader("Content-Disposition", "attachment; filename=ripoti.xlsx");
					}

					IOUtils.copy(inputStream, res.getOutputStream());

					return "success";

				} catch (IOException e) {

					e.printStackTrace();
				}

			}

		}

		return "fail";
	}
}
