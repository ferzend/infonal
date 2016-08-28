package com.infonal.servlets;

import com.infonal.CONSTANTS;
import com.infonal.converter.UserConverter;
import com.infonal.dao.UserDAO;
import com.infonal.model.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {

	private UserDAO userDAO;

	private RequestDispatcher dispather;

	private UserConverter userConverter;

	public EditUserServlet() { }

	public EditUserServlet(UserDAO userDAO, RequestDispatcher dispatcher) {
		this.userDAO = userDAO;
		this.dispather = dispatcher;
		userConverter = new UserConverter();
	}

	@Override
	public void init() throws ServletException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("infonal-context.xml");
		this.userDAO = (UserDAO) ctx.getBean("userDao");
		this.dispather = getServletContext().getRequestDispatcher("/user.jsp");
		userConverter = new UserConverter();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter(CONSTANTS.REQUEST_ATT_ID);
		if (id == null || "".equals(id)) {
			throw new ServletException("id zorunlu");
		}

		User user = new User();
		user.setId(id);
		user = userDAO.readUser(user);
		request.setAttribute(CONSTANTS.REQUEST_ATT_USER, user);

		List<User> users = userDAO.readAllUser();
		request.setAttribute(CONSTANTS.REQUEST_ATT_USERS, users);

		dispather.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = userConverter.toUser(request);

		if (user.getId() == null) {
			throw new ServletException("id zorunlu");
		}

		if (user.getName() == null) {

			request.setAttribute(CONSTANTS.REQUEST_ATT_ERROR, "isim zorunlu");

			request.setAttribute(CONSTANTS.REQUEST_ATT_USER, user);
			List<User> users = userDAO.readAllUser();
			request.setAttribute(CONSTANTS.REQUEST_ATT_USERS, users);

		} else {
			userDAO.updateUser(user);
			request.setAttribute(CONSTANTS.REQUEST_ATT_SUCCESS, "kullanıcı başarıyla güncellendi");
			List<User> users = userDAO.readAllUser();
			request.setAttribute(CONSTANTS.REQUEST_ATT_USERS, users);
		}

		dispather.forward(request, response);
	}

}
