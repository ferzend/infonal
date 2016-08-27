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

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

	private UserDAO userDAO;

	@Override
	public void init() throws ServletException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("infonal-context.xml");
		this.userDAO = (UserDAO) ctx.getBean("userDao");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/user.jsp");

		if ((name == null || name.equals(""))) {

			request.setAttribute("error", "isim zorunlu");

		} else {

			User p = new User();
			p.setSurname(surname);
			p.setName(name);

			userDAO.createUser(p);
			request.setAttribute("success", "kullanıcı eklendi");
			List<User> users = userDAO.readAllUser();
			request.setAttribute("users", users);

		}
		rd.forward(request, response);
	}

}
