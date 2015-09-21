package ci6206.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.DispatcherType;


import ci6206.model.Constants;

/**
 * Servlet Filter implementation class AuthorizeFilter
 */
@WebFilter(
		urlPatterns  = {"/home.jsp","/home"},
		servletNames = "home",  
		filterName= "/AuthFilter",
	    dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
		)
public class AuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
    	HttpSession session = ((HttpServletRequest) request).getSession();
    	if(session!=null && session.getAttribute(Constants.USER_ATTR)!=null)
    	{
    		//authenticated
    		// pass the request along the filter chain
    		chain.doFilter(request, response);
    		
    	}
    	else
    	{
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
    		dispatcher.forward(request, response);
    	}

		// pass the request along the filter chain
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
