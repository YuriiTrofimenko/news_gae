package org.tyaa.itstepnews1.api;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.tyaa.itstepnews1.entity.News;

import com.googlecode.objectify.ObjectifyService;

public class Bootstrapper implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ObjectifyService.init();
        ObjectifyService.register(News.class);
	}

}
