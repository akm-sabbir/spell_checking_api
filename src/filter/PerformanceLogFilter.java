//package filter;
//import java.io.IOException;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import common.PerformanceLog;
//import common.PerformanceLogDAO;
//import login.LoginDTO;
//import sessionmanager.SessionConstants;
//import util.CurrentTimeFactory;
//
//
//public class PerformanceLogFilter implements Filter{
//
//
//	
//	@Override
//	public void destroy() {
//		
//	}
//	
//
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
//			throws IOException, ServletException {
//		
//		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
//		
//		LoginDTO loginDTO = (LoginDTO)httpServletRequest.getSession().getAttribute(SessionConstants.USER_LOGIN);
//		
//		
//		
//		long requestTime = System.currentTimeMillis();
//		
//		
//		
//		
//		CurrentTimeFactory.initializeCurrentTimeFactory();
//		
//		filterChain.doFilter(request, response);
//		
//		CurrentTimeFactory.destroyCurrentTimeFactory();
//		long responseTime = System.currentTimeMillis();
//		int totalServiceTime = (int)(responseTime - requestTime);
//		
//		PerformanceLog activityLog = new PerformanceLog();
//		activityLog.requestTime = requestTime;
//		activityLog.totalServiceTime = totalServiceTime;
//		activityLog.URI = ((HttpServletRequest) request).getRequestURI();
//		activityLog.userID = (loginDTO!=null?loginDTO.userID:0);
//		activityLog.ipAddress = httpServletRequest.getRemoteAddr();
//		activityLog.portNumber = httpServletRequest.getRemotePort();
//		new PerformanceLogDAO().insert(activityLog);
//	}
//
//	@Override
//	public void init(FilterConfig arg0) throws ServletException {
//	}
//
//}
