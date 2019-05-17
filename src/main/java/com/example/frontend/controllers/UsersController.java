package com.example.frontend.controllers;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/users")
public class UsersController {

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index(Model model, HttpSession session) {

		model.addAttribute("successMessage", session.getAttribute("successMessage"));
		model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
		session.removeAttribute("successMessage");
		session.removeAttribute("errorMessage");

		String url = Constants.base_url + "/users";

		model.addAttribute("data", Constants.getJsonObject(url).getJSONArray("data"));

		model.addAttribute("attachments_path", Constants.files_upload_path);

		return "users/index";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String create(Model model) {

		return "users/create";
	}

	@RequestMapping(path = "", method = RequestMethod.POST)
	public String store(@RequestParam LinkedMultiValueMap<String, Object> requestData, Model model,
			@RequestParam(required = false) MultipartFile file, HttpSession session) {

		model.addAttribute("successMessage", session.getAttribute("successMessage"));
		model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
		session.removeAttribute("successMessage");
		session.removeAttribute("errorMessage");

		String url = Constants.base_url + "/users";
		String results = null;

		if (file != null && !file.isEmpty()) {
			ByteArrayResource photoResource;
			try {
				photoResource = new ByteArrayResource(file.getBytes()) {

					@Override
					public String getFilename() {

						return file.getOriginalFilename();
					}
				};

				results = Constants.postMultipartFormData(url, requestData, photoResource);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			results = Constants.postFormData(url, requestData);
		}

		JSONObject response = new JSONObject(results);
		System.out.println("response = " + response);

		if (response.get("code").equals("OK")) {
			session.setAttribute("successMessage", response.get("message"));
		} else {
			session.setAttribute("errorMessage", response.get("message"));
		}

		return "redirect:/users";
	}

}
