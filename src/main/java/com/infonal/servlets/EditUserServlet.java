package com.infonal.servlets;

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

	public EditUserServlet() { }

	public EditUserServlet(UserDAO userDAO, RequestDispatcher dispatcher) {
		this.userDAO = userDAO;
		this.dispather = dispatcher;
	}

	@Override
	public void init() throws ServletException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("infonal-context.xml");
		this.userDAO = (UserDAO) ctx.getBean("userDao");
		this.dispather = getServletContext().getRequestDispatcher("/user.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null || "".equals(id)) {
			throw new ServletException("id zorunlu");
		}

		User user = new User();
		user.setId(id);
		user = userDAO.readUser(user);
		request.setAttribute("user", user);

		List<User> users = userDAO.readAllUser();
		request.setAttribute("users", users);

		dispather.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("id");

		if (id == null || "".equals(id)) {
			throw new ServletException("id zorunlu");
		}

		String name = request.getParameter("name");
		String surname = request.getParameter("surname");

		if ((name == null || name.equals(""))) {

			request.setAttribute("error", "isim zorunlu");

			User user = new User();
			user.setId(id);
			user.setName(name);
			user.setSurname(surname);

			request.setAttribute("user", user);
			List<User> users = userDAO.readAllUser();
			request.setAttribute("users", users);

		} else {
			User p = new User();
			p.setId(id);
			p.setName(name);
			p.setSurname(surname);
			userDAO.updateUser(p);

			request.setAttribute("success", "kullanıcı başarıyla güncellendi");

			List<User> users = userDAO.readAllUser();
			request.setAttribute("users", users);

		}

		dispather.forward(request, response);
	}

}
