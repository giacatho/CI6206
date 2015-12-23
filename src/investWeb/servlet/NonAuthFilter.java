package investWeb.servlet;

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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import investWeb.model.Constants;

import javax.servlet.DispatcherType;

/**
 * Servlet Filter implementation class AuthorizeFilter
 */
@WebFilter(
		urlPatterns  = {"/login","/register"},
		servletNames = {"login","register"},  
		filterName= "/NonAuthFilter",
	    dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
		)
public class NonAuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public NonAuthFilter() {
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
		// place your code here
    	HttpSession session = ((HttpServletRequest) request).getSession();
    	if(session!=null && session.getAttribute(Constants.USER_ATTR)!=null)
    	{
    		// type login and register in url, but have authenticated
    		// go to Home
    		HttpServletResponse httpResponse = (HttpServletResponse) response;
    		httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/home");
    	}
    	else
    	{
    		chain.doFilter(request, response);
    	}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
