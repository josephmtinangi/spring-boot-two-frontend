package com.example.frontend.security;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.frontend.controllers.Constants;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		String url = Constants.base_url + "/login";

		String username = (String) auth.getPrincipal();
		String password = (String) auth.getCredentials();

		LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("username", username);
		params.add("password", password);

		Long id = null;
		String usernameString = null;
		String avatar = null;
		String token = null;

		String response = Constants.loginRequest(url, params);

		JSONObject responseJson;
		try {
			responseJson = new JSONObject(response);

			id = responseJson.getJSONObject("userDetails").getLong("id");
			usernameString = responseJson.getJSONObject("userDetails").getString("username");
			token = responseJson.getString("token");
			avatar = responseJson.getString("avatar");
			if (!responseJson.getBoolean("success")) {
				throw new BadCredentialsException(responseJson.getString("message"));

			}

		} catch (JSONException e) {

			e.printStackTrace();
		}

		UserContext userContext = new UserContext(id, usernameString, token);

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		session.setAttribute("username", usernameString);

		session.setAttribute("id", id);

		session.setAttribute("avatar", Constants.files_upload_path + "/" + avatar);

		return new UsernamePasswordAuthenticationToken(userContext, token, Collections.emptyList());
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
