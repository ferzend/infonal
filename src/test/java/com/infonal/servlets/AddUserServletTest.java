package com.infonal.servlets;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import java.io.IOException;

public class AddUserServletTest {

    public void test () throws ServletException, IOException {

//        AddUserServlet servlet = new AddUserServlet();

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "infonal-context.xml");

        AddUserServlet addUserServlet = (AddUserServlet) ctx.getBean("addServlet");

    }
}