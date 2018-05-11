/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.itstepnews1.api;

import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tyaa.itstepnews1.dao.DAO;
import org.tyaa.itstepnews1.entity.News;
import org.tyaa.itstepnews1.globals.GlobalVariables;
import org.tyaa.itstepnews1.model.Result;

/**
 *
 * @author student
 */
@WebServlet(name = "NewsServlet", urlPatterns = {"/news"})
public class NewsServlet extends HttpServlet {
	
	/*static {
		
		ObjectifyService.register(News.class);
	}*/

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        if (request.getParameterMap().containsKey("action")) {
            
            try (PrintWriter out = response.getWriter()) {

                Gson gson = new Gson();
                
                switch(request.getParameter("action")){
                
                    case "create" : {
                        
                        String titleString = request.getParameter("title");
                        String contentString = request.getParameter("content");
                        
                        //News news = new News(titleString, contentString);
                        
                        ObjectifyService.run(new VoidWork() {
                		    public void vrun() {
                		    	try {
									DAO.createOrder(titleString, contentString);
								} catch (Exception ex) {
									
									Result result = new Result(ex.getMessage());
		                            String resultJsonString = gson.toJson(result);
		                            out.print(resultJsonString);
								}
                		    }
                		});
                        //GlobalVariables.news.add(news);
                        //Result result = new Result(GlobalVariables.news);
                        ArrayList<String> data = new ArrayList<>();
                        data.add("created");
                        Result result = new Result(data);
                        String resultJsonString = gson.toJson(result);
                        out.print(resultJsonString);
                        break;
                    }
                    case "fetch-all-news" : {
                        
                    	List<News> news = new ArrayList<>();
                    	
                    	try {
                    		
                    		ObjectifyService.run(new VoidWork() {
                    		    public void vrun() {
                    		    	try {
    									DAO.getAllOrders(news);
    								} catch (Exception ex) {
    									
    									Result result = new Result(ex.getMessage());
    		                            String resultJsonString = gson.toJson(result);
    		                            out.print(resultJsonString);
    								}
                    		    }
                    		});
                    	} catch (Exception ex) {
                            
                            Result result = new Result(ex.getMessage());
                            String resultJsonString = gson.toJson(result);
                            out.print(resultJsonString);
                    	}
                        //Result result = new Result(GlobalVariables.news);
                    	Result result = new Result(news);
                        String resultJsonString = gson.toJson(result);
                        out.print(resultJsonString);
                        break;
                    }
                    case "delete-news" : {
                        
                    	String newsId = request.getParameter("news-id");
                    	
                    	try {
                    		
                    		ObjectifyService.run(new VoidWork() {
                    		    public void vrun() {
                    		    	try {
    									DAO.deleteOrder(Long.parseLong(newsId));
    								} catch (Exception ex) {
    									
    									Result result = new Result(ex.getMessage());
    		                            String resultJsonString = gson.toJson(result);
    		                            out.print(resultJsonString);
    								}
                    		    }
                    		});
                    	} catch (Exception ex) {
                            
                            Result result = new Result(ex.getMessage());
                            String resultJsonString = gson.toJson(result);
                            out.print(resultJsonString);
                    	}
                    	ArrayList<String> data = new ArrayList<>();
                        data.add("deleted");
                        Result result = new Result(data);
                        String resultJsonString = gson.toJson(result);
                        out.print(resultJsonString);
                        break;
                    }
                }
            }
        }
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
