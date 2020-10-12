package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class TestServlet extends HttpServlet 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5222793251610509039L;

	@Override
	public void init() throws ServletException
	{
		System.out.println("Servlet : " + this.getServletName() + " has started ...");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		
		
		
		
		out.print("Salam iftekhar ...");
		out.flush();		
	}

	@Override
	public void destroy()
	{
		System.out.println("Servlet : " + this.getServletName() + " has stopped ...");
	}
	
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException 
	{
	    setAccessControlHeaders(resp);
	    resp.setStatus(HttpServletResponse.SC_OK);
	}

	private void setAccessControlHeaders(HttpServletResponse resp) 
	{
	    resp.setHeader("Access-Control-Allow-Origin", "*");
	    resp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
	    resp.setHeader("Access-Control-Allow-Methods"," GET, POST, OPTIONS");
	  //resp.setHeader("Access-Control-Allow-Headers","Content-Type");
	  //resp.setHeader("Content-Type", "application/json");
	}	
	
}
