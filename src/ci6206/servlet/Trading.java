package ci6206.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ci6206.dao.StockDAO;
import ci6206.model.Stock;

/**
 * Servlet implementation class Trading
 * @author Michael Tan
 */
@WebServlet(name="trading", urlPatterns={"/trading"})
public class Trading extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Trading() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
    	//request.setAttribute("title", "Home");
    	
    	//RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
		//dispatcher.forward(request, response);
    	String beginStr = request.getParameter("srch");
    	
    	StockDAO stockDO = new StockDAO();
    	ArrayList<Stock>list = stockDO.GetStocksStartWith(beginStr);
    	for(int i=0; i<list.size();i++)
    	{
	    	response.getWriter().append(list.get(i).getName()).append(" at ").append(String.valueOf(list.get(i).getPrice())).append(" \n");
    	}
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		processRequest(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
