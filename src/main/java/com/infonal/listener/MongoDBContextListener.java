package com.infonal.listener;

import com.mongodb.MongoClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MongoDBContextListener implements ServletContextListener {

	ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("infonal-context.xml");

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		MongoClient mongo = (MongoClient) springContext.getBean("mongoClient");

		mongo.close();
		System.out.println("MongoClient closed successfully");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		MongoClient mongo = (MongoClient) springContext.getBean("mongoClient");

		sce.getServletContext().setAttribute("MONGO_CLIENT", mongo);
	}

}
