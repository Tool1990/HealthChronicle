package fhws.healthchronicle.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fhws.healthchronicle.managedbeans.SessionBean;

public class SecurityFilter implements Filter
{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		SessionBean session = (SessionBean) ((HttpServletRequest) request).getSession().getAttribute("sessionBean");

		if (session == null || !session.isLoggedIn())
		{
			String contextPath = ((HttpServletRequest) request).getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath + "/faces/index.xhtml");
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		// TODO Auto-generated method stub
	}

}
