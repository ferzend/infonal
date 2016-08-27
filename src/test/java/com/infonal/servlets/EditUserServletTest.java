package com.infonal.servlets;

import com.infonal.dao.UserDAO;
import com.infonal.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EditUserServletTest {

    @Mock
    HttpServletResponse response;

    @Mock
    HttpServletRequest request;

    @Mock
    ServletContext servletContext;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    UserDAO userDAO;

    @Test(expected = ServletException.class)
    public void shouldThrowServletExceptionWhenIdIsEmpty_doGet () throws ServletException, IOException {
        EditUserServlet servlet = new EditUserServlet(userDAO,requestDispatcher);
        servlet.doGet(request, response);
    }

    @Test
    public void shouldForward () throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("dummyId");
        EditUserServlet servlet = new EditUserServlet(userDAO, requestDispatcher);
        servlet.doGet(request, response);

        verify(requestDispatcher).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));

    }

    @Test(expected = ServletException.class)
    public void shouldThrowServletExceptionWhenIdIsEmpty_DoPost () throws ServletException, IOException {
        EditUserServlet servlet = new EditUserServlet(userDAO,requestDispatcher);
        servlet.doPost(request, response);
    }

    @Test
    public void shouldNotUpdateUserWhenNameIsNull () throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("dummyId");
        when(request.getParameter("name")).thenReturn(null);
        EditUserServlet servlet = new EditUserServlet(userDAO, requestDispatcher);
        servlet.doPost(request, response);

        verify(requestDispatcher).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
        verify(userDAO,times(0)).updateUser(any(User.class));

    }

    @Test
    public void shouldUpdateUserWhenNameIsNotNull () throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("dummyId");
        when(request.getParameter("name")).thenReturn("dummyName");
        EditUserServlet servlet = new EditUserServlet(userDAO, requestDispatcher);
        servlet.doPost(request, response);

        verify(requestDispatcher).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
        verify(userDAO,times(1)).updateUser(any(User.class));

    }

}