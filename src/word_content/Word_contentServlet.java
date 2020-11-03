package word_content;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
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

import com.google.gson.Gson;

import bangla.dao.AnnotatedWordRepository;
import bangla.dao.DictionaryRepository;
import bangla.dao.GlobalDictionaryRepository;
import bangla.dao.GradedPronoun;
import bangla.dao.NamedEntityRepository;
import bangla.dao.NaturalErrorRepository;
import bangla.dao.SadhuCholitMixture;
import bangla.dao.SubjectVerbRepository;
import bangla.grammarchecker.GrammerCheckerFactory;
import bangla.spellchecker.SpellCheckingDto;
import repository.RepositoryManager;
import spell_checker_log.Spell_checker_logDAO;
import spell_checker_log.Spell_checker_logDTO;

/**
 * Servlet implementation class Word_contentServlet
 */
@WebServlet("/bangla_spell_and_grammar")
@MultipartConfig
public class Word_contentServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
    public static Logger logger = Logger.getLogger(Word_contentServlet.class);
	//private Object gson;
   	
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
    	System.out.println("Start loading web servers and servlets");
    	GrammerCheckerFactory.initializeRegisteredCheckers();
    	RepositoryManager.getInstance().addRepository(AnnotatedWordRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(DictionaryRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(NamedEntityRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(NaturalErrorRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(GradedPronoun.getInstance(),true);
    	RepositoryManager.getInstance().addRepository(SubjectVerbRepository.getInstance(),true);
    	RepositoryManager.getInstance().addRepository(SadhuCholitMixture.getInstance(),true);
    	RepositoryManager.getInstance().addRepository(GlobalDictionaryRepository.getInstance(), true);
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
		Spell_checker_logDTO spell_checking_dto = new Spell_checker_logDTO();
		spell_checking_dto.time = System.currentTimeMillis();
		String text_data = request.getParameter("content");
		//String permission = request.getParameter("PermissionToStoreData");
		String service = request.getParameter("Service");
		if (text_data.length() == 0 || text_data.length() > 15000) {
			logger.debug("End of request processing: " );
			response.sendError(400, "could not understand input request text is either empty or noninterpretable");
			return;
		}
		HashMap<Integer, SpellCheckingDto> results= null;
		if(service.toLowerCase().equals("spellandgrammarchecking")) {
			bangla.SpellAndGrammarChecker spgChecker = new bangla.SpellAndGrammarChecker();
			results = spgChecker.check(text_data,3);
		}
		
		//List<String> tokenized_data = sp_checker.getTokenizedWord(text_data);
		if(results == null ) {
			logger.debug("End of request processing: " );
			response.sendError(400, "could not understand input request text is either empty or noninterpretable");
			return;
		}
		String final_result = this.gson.toJson(results);
		// the following lines for IP address.
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		if (ipAddress == null) {  
		    ipAddress = request.getRemoteAddr();  
		}
		System.out.println(ipAddress);
		spell_checking_dto.ip = 0;  
		for (byte b: InetAddress.getByName(request.getRemoteAddr()).getAddress())  
		{  
			spell_checking_dto.ip = spell_checking_dto.ip << 8 | (b & 0xFF);  
		}
		spell_checking_dto.content = text_data;
		spell_checking_dto.response = final_result;
		spell_checking_dto.session = request.getSession().toString();
		spell_checking_dto.clientCat = 1;
		spell_checking_dto.unknownWordCount = results.get(2147483647).sequence;
		results.remove(2147483647);
		Spell_checker_logDAO spell_dao = new Spell_checker_logDAO();
		
		try {
			spell_dao.add(spell_checking_dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json;charset=UTF-8");
		//response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		logger.debug("End of request processing: " );
		out.print(final_result);
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

