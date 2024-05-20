package com.bamba.secureapp.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bamba.secureapp.dto.UserDTO;
import com.bamba.secureapp.service.LoginService;



@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
	
	private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);
	private LoginService loginService;
	private final String LOGIN_PAGE = "index.jsp";
	private final String WELCOME_PAGE = "welcome";
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		loginService = new LoginService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		loadIndex(null, req, resp);
	}

	private void loadIndex(String message, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("message", message);
		req.getRequestDispatcher(WELCOME_PAGE).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			logger.info("Tentative de connexion avec {} et {}", email, password);
			try {
				Optional<UserDTO> userDtoOption = loginService.logException(email, password);
				
				UserDTO userDto = userDtoOption.get();
				req.getSession().setAttribute("username", userDto.getEmail());
				resp.sendRedirect(WELCOME_PAGE);
			} catch (Exception e) {
				String message = "informations de connexion incorrect.";
				logger.error("{}", message);
				loadIndex(message, req, resp);
			}
				
	}
}