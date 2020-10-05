package word_content;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.*;
import org.apache.log4j.Logger;

import bangla.dao.AnnotatedWordRepository;
import bangla.dao.DictionaryRepository;
import bangla.dao.NamedEntityRepository;
import bangla.dao.NaturalErrorRepository;
import repository.RepositoryManager;

/**
 * Servlet implementation class Word_contentServlet
 */
@WebServlet("/api/wordcontent")
@MultipartConfig
public class Word_contentServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public static Logger logger = Logger.getLogger(Word_contentServlet.class);
   	
    /**
     * @see HttpServlet#HttpServlet()
     * 
     */
    
    public Word_contentServlet() 
	{
        super();
    	try
    	{
			//approval_module_mapDTO = approval_module_mapDAO.getApproval_module_mapDTOByTableName("word_content");
			//word_contentDAO = new Word_contentDAO(tableName, tempTableName, approval_module_mapDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }   
    public void init(ServletConfig confit) {
    	
    	System.out.println("this is the start of the server");
    	System.out.print("Start loading web servers and servlets");
    	RepositoryManager.getInstance().addRepository(AnnotatedWordRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(DictionaryRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(NamedEntityRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(NaturalErrorRepository.getInstance(), true);
    	return;
    }
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		//LoginDTO loginDTO = (LoginDTO)request.getSession().getAttribute(SessionConstants.USER_LOGIN);
		//UserDTO userDTO = UserRepository.getUserDTOByUserID(loginDTO);
		//System.out.println(" calling the doPost operation");
		String URL = "word_content/word_contentInPlaceEdit.jsp";
		logger.info("Recieved a request to process: " );
		String text_data = request.getParameter("identity");
		if (text_data.length() == 0) {
			logger.debug("End of request processing: " );
			response.sendError(400, "could not understand input request text is either empty or noninterpretable");
			return;
		}
		bangla.SpellAndGrammarChecker spgChecker = new bangla.SpellAndGrammarChecker();
//		String results = spgChecker.check(text_data);
		String results = spgChecker.check(text_data, 3);		//	3: both option
		
		//List<String> tokenized_data = sp_checker.getTokenizedWord(text_data);
		if(results == null ) {
			logger.debug("End of request processing: " );
			response.sendError(400, "could not understand input request text is either empty or noninterpretable");
			return;
		}
		
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json;charset=UTF-8");
		//response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		logger.debug("End of request processing: " );
		out.print(results);
		out.flush();
		
	}
	
		
	@Override
	  protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	      setAccessControlHeaders(resp);
	      resp.setStatus(HttpServletResponse.SC_OK);
	  }

	  private void setAccessControlHeaders(HttpServletResponse resp) {
	      resp.setHeader("Access-Control-Allow-Origin", "*");
	      resp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
	      resp.setHeader("Access-Control-Allow-Methods"," GET, POST, OPTIONS");
		  //resp.setHeader("Access-Control-Allow-Headers","Content-Type");
		  //resp.setHeader("Content-Type", "application/json");
	  }
		
}

