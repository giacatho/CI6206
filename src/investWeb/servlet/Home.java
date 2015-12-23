package investWeb.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import investWeb.dao.UserDao;
import investWeb.model.Constants;
import investWeb.model.User;

/**
 * Servlet implementation class Home
 */
@WebServlet(name="home", urlPatterns={"/home"})
public class Home extends HttpServlet {
	Logger logger = Logger.getLogger(Home.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setAttribute("title", "Home");
    	UserDao dao = new UserDao();
    	try {
    		dao.OpenConnection();
        	ArrayList<User> userList = dao.getUserForRanking();
        	request.setAttribute(Constants.USER_LIST,userList);
    	} catch (Exception e) {
    		logger.error(e.fillInStackTrace());
    	} finally {
    		dao.CloseConnection();
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
		dispatcher.forward(request, response);
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
