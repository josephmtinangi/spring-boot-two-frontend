package com.example.frontend;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = FileNotFoundException.class)
	public void handle(FileNotFoundException ex, HttpServletResponse response) throws IOException {
		System.out.println("handling file not found exception");
		response.sendError(404, ex.getMessage());
	}
	
	
	@ExceptionHandler(value = IOException.class)
    public void handle(IOException ex, HttpServletResponse response) throws IOException {
		System.out.println("handling io exception");
		response.sendError(500, ex.getMessage());
	}

}
