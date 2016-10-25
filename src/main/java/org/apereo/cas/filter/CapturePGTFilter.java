package org.apereo.cas.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class CapturePGTFilter
 */

public class CapturePGTFilter implements Filter {
	// We do not care about thread safety since this is for testing only.
	private static Map<String,String> pgtMap = new HashMap<String,String>();
	private Logger logger = Logger.getLogger("default");
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("1");
		if (request.getParameterMap().containsKey("requestPgtId")) {
			System.out.println("2");
			String pgtIou = request.getParameter("pgtIou");
			
			System.out.println("Getting pgtId for pgtIou: " + pgtIou);
			logger.fine("Getting pgtId for pgtIou: " + pgtIou);
			
			String pgtId = pgtMap.get(pgtIou);
			System.out.println("pgtId for pgtIou was: " + pgtId);
			logger.fine("pgtId for pgtIou was: " + pgtId);
			
			response.getWriter().write(pgtId);

			// No need to pass call along the filter chain as this is a specialized case.
			return;
		} else {
			System.out.println("3");
			if (request.getParameterMap().containsKey("pgtIou")) {
				System.out.println("4");
				System.out.println("Found pgtIou");
				logger.fine("Found pgtIou");
	
				String pgtIou = request.getParameter("pgtIou");
				String pgtId = request.getParameter("pgtId");
	
				System.out.println("pgtIou is " + pgtIou + " pgtId is " + pgtId);
				logger.fine("pgtIou is " + pgtIou + " pgtId is " + pgtId);
				pgtMap.put(pgtIou, pgtId);
			}
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// NO-OP Placeholder
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// NO-OP Placeholder
	}
}