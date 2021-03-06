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

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {


	private UserDAO userDAO;

	@Override
	public void init() throws ServletException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("infonal-context.xml");

		this.userDAO = (UserDAO) ctx.getBean("userDao");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null || "".equals(id)) {
			throw new ServletException("id zorunlu");
		}

		User p = new User();
		p.setId(id);
		userDAO.deleteUser(p);

		request.setAttribute("success", "kullanıcı silindi");
		List<User> users = userDAO.readAllUser();
		request.setAttribute("users", users);

		RequestDispatcher rd = getServletContext().getRequestDispatcher("/user.jsp");
		rd.forward(request, response);
	}

}
