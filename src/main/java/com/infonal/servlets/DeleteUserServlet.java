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

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {


	private UserDAO userDAO;

	private RequestDispatcher rd;

	private UserConverter userConverter;

	@Override
	public void init() throws ServletException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("infonal-context.xml");
		this.userDAO = (UserDAO) ctx.getBean("userDao");
		rd = getServletContext().getRequestDispatcher("/user.jsp");
		userConverter = new UserConverter();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = userConverter.toUser(request);

		if (user.getId() == null ) {
			throw new ServletException("id zorunlu");
		}
		userDAO.deleteUser(user);

		request.setAttribute(CONSTANTS.REQUEST_ATT_SUCCESS, "kullanıcı silindi");
		List<User> users = userDAO.readAllUser();
		request.setAttribute(CONSTANTS.REQUEST_ATT_USERS, users);

		rd.forward(request, response);
	}

}
