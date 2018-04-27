package org.tyaa.itstepnews1.dao;

import com.googlecode.objectify.*;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.tyaa.itstepnews1.entity.News;

public class DAO {
	
	//Получение всех заказов в виде списка
	public static void getAllOrders(List<News> _emptyOrdersList) throws Exception {
		
		_emptyOrdersList.addAll(ofy().load().type(News.class).list());
	}
	
	public static void createOrder(String _title, String _content) {
        
        News news = new News(_title, _content);
        ofy().save().entity(news).now();
	}
}
