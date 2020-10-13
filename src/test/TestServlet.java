package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
		
		String resp = "{}";
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		
		if (request.getParameter("actiontype").equalsIgnoreCase("batchlist") 
				&& request.getParameterMap().containsKey("from") && request.getParameterMap().containsKey("to"))
		{
			
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

				Date fromDate = sdf.parse(request.getParameter("from"));
				long from = fromDate.getTime();
				
				Date toDate = sdf.parse(request.getParameter("to"));
				long to = toDate.getTime();				
				
				Map<Object, Object> map = TestoutDAO.get_guid_list(from, to);
				
				resp = gson.toJson(map);

//				resp = "{\"guidList\":[{\"guid\":\"9a9cd2fd-78bc-4377-8317-6ec17abc2751\"},{\"guid\":\"9a9cd2fd-78bc-4377-8317-6ec17abc2752\"},{\"guid\":\"9a9cd2fd-78bc-4377-8317-6ec17abc2753\"}]}";
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		else if (request.getParameter("actiontype").equalsIgnoreCase("batch") 
				&& request.getParameterMap().containsKey("guid"))
		{
			try
			{
				String guid = request.getParameter("guid");
				
				Map<Object, Object> m1 = TestoutDAO.get_id_list(guid);
				Map<Object, Object> m2 = BatchTestDAO.get_batch_statistics(guid);
				
				Map<Object, Object> map = new LinkedHashMap<Object, Object>();
				
				map.put("idList", m1.get("idList"));
				map.put("batchStatistics", m2.get("batchStatistics"));
				
				resp = gson.toJson(map);
				
//				resp = "{\"idList\":[{\"id\":121},{\"id\":122},{\"id\":123},{\"id\":124},{\"id\":125}],\"batchStatistics\":{\"DP\":\"71.00\",\"DR\":\"71.00\",\"CP\":\"71.00\",\"CR\":\"71.00\",\"complexity\":\"all\",\"wordErrorType\":\"NON_WORD_ERROR\"}}";
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		else if (request.getParameter("actiontype").equalsIgnoreCase("testresult") 
				&& request.getParameterMap().containsKey("guid") && request.getParameterMap().containsKey("id"))
		{
			try
			{
				String guid = request.getParameter("guid");
				long id = Long.parseLong(request.getParameter("id"));
				
				Map<Object, Object> m1 = TestinDAO.get_content(id);
				Map<Object, Object> m2 = TestoutDAO.get_test_result(guid, id);
				
				Map<Object, Object> contentMap = (Map<Object, Object>) m1.get("content");
				String logString = (String) m2.get("log");
				JsonObject jsonObject = new JsonParser().parse(logString).getAsJsonObject();
				
				Map<Object, Object> logMap = new LinkedHashMap<>();
				
				for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
					logMap.put(entry.getKey(), entry.getValue());
				
				Map<Object, Object> map = new LinkedHashMap<Object, Object>();
				
				for(Object o : contentMap.keySet())
					map.put(o, contentMap.get(o));
				
				for(Object o : logMap.keySet())
					map.put(o, logMap.get(o));
					
				resp = gson.toJson(map);			
				
	//			resp = "{\"originalContent\":\"আমি ভাত খান। তুমি স্কুলে যান।\",\"correctedContent\":\"আমি ভাত খাই। তুমি স্কুলে যাও।\",\"predictedContent\":{\"0\":{\"word\":\"আমি\",\"sequence\":0,\"errorType\":3,\"startIndex\":0,\"length\":3,\"suggestion\":[{\"words\":\"তিনি\",\"score\":-1},{\"words\":\"তিঁনি\",\"score\":-1},{\"words\":\"ইনি\",\"score\":-1},{\"words\":\"উনি\",\"score\":-1},{\"words\":\"আপনি\",\"score\":-1},{\"words\":\"আপনারা\",\"score\":-1},{\"words\":\"তাহারা\",\"score\":-1},{\"words\":\"উনারা\",\"score\":-1},{\"words\":\"তিনি\",\"score\":-1},{\"words\":\"তিঁনি\",\"score\":-1},{\"words\":\"ইনি\",\"score\":-1},{\"words\":\"উনি\",\"score\":-1},{\"words\":\"আপনি\",\"score\":-1},{\"words\":\"আপনারা\",\"score\":-1},{\"words\":\"তাহারা\",\"score\":-1},{\"words\":\"উনারা\",\"score\":-1},{\"words\":\"তিনি\",\"score\":-1},{\"words\":\"তিঁনি\",\"score\":-1},{\"words\":\"ইনি\",\"score\":-1},{\"words\":\"উনি\",\"score\":-1},{\"words\":\"আপনি\",\"score\":-1},{\"words\":\"আপনারা\",\"score\":-1},{\"words\":\"তাহারা\",\"score\":-1},{\"words\":\"উনারা\",\"score\":-1}]},\"1\":{\"word\":\"ভাত\",\"sequence\":0,\"errorType\":0,\"startIndex\":4,\"length\":7},\"2\":{\"word\":\"খান\",\"sequence\":0,\"errorType\":0,\"startIndex\":8,\"length\":11},\"3\":{\"word\":\"তুমি\",\"sequence\":0,\"errorType\":0,\"startIndex\":13,\"length\":17},\"4\":{\"word\":\"স্কুলে\",\"sequence\":0,\"errorType\":0,\"startIndex\":18,\"length\":24},\"5\":{\"word\":\"যান\",\"sequence\":0,\"errorType\":0,\"startIndex\":25,\"length\":28}},\"totalAlignmentMap\":{\"0\":{\"startIndex\":0,\"original\":\"আমি\",\"corrected\":\"আমি\",\"predictedAttributes\":{\"word\":\"আমি\",\"sequence\":0,\"errorType\":3,\"startIndex\":0,\"length\":3,\"suggestion\":[{\"words\":\"তিনি\",\"score\":-1},{\"words\":\"তিঁনি\",\"score\":-1},{\"words\":\"ইনি\",\"score\":-1},{\"words\":\"উনি\",\"score\":-1},{\"words\":\"আপনি\",\"score\":-1},{\"words\":\"আপনারা\",\"score\":-1},{\"words\":\"তাহারা\",\"score\":-1},{\"words\":\"উনারা\",\"score\":-1},{\"words\":\"তিনি\",\"score\":-1},{\"words\":\"তিঁনি\",\"score\":-1},{\"words\":\"ইনি\",\"score\":-1},{\"words\":\"উনি\",\"score\":-1},{\"words\":\"আপনি\",\"score\":-1},{\"words\":\"আপনারা\",\"score\":-1},{\"words\":\"তাহারা\",\"score\":-1},{\"words\":\"উনারা\",\"score\":-1},{\"words\":\"তিনি\",\"score\":-1},{\"words\":\"তিঁনি\",\"score\":-1},{\"words\":\"ইনি\",\"score\":-1},{\"words\":\"উনি\",\"score\":-1},{\"words\":\"আপনি\",\"score\":-1},{\"words\":\"আপনারা\",\"score\":-1},{\"words\":\"তাহারা\",\"score\":-1},{\"words\":\"উনারা\",\"score\":-1}]}},\"4\":{\"startIndex\":4,\"original\":\"ভাত\",\"corrected\":\"ভাত\",\"predictedAttributes\":{\"word\":\"ভাত\",\"sequence\":0,\"errorType\":0,\"startIndex\":4,\"length\":7}},\"8\":{\"startIndex\":8,\"original\":\"খান\",\"corrected\":\"খাই\",\"predictedAttributes\":{\"word\":\"খান\",\"sequence\":0,\"errorType\":0,\"startIndex\":8,\"length\":11}},\"11\":{\"startIndex\":11,\"original\":\"।\",\"corrected\":\"।\"},\"13\":{\"startIndex\":13,\"original\":\"তুমি\",\"corrected\":\"তুমি\",\"predictedAttributes\":{\"word\":\"তুমি\",\"sequence\":0,\"errorType\":0,\"startIndex\":13,\"length\":17}},\"18\":{\"startIndex\":18,\"original\":\"স্কুলে\",\"corrected\":\"স্কুলে\",\"predictedAttributes\":{\"word\":\"স্কুলে\",\"sequence\":0,\"errorType\":0,\"startIndex\":18,\"length\":24}},\"25\":{\"startIndex\":25,\"original\":\"যান\",\"corrected\":\"যাও\",\"predictedAttributes\":{\"word\":\"যান\",\"sequence\":0,\"errorType\":0,\"startIndex\":25,\"length\":28}},\"28\":{\"startIndex\":28,\"original\":\"।\",\"corrected\":\"।\"}},\"DTP\":{\"0\":{\"startIndex\":0,\"original\":\"আমি\",\"corrected\":\"আমি\",\"predictedVerdict\":\"CORRECT\"}},\"DFP\":{},\"DFN\":{},\"CTP\":{\"0\":{\"startIndex\":0,\"original\":\"আমি\",\"corrected\":\"আমি\",\"predictedVerdict\":\"CORRECT\"}}, \"CFP\":{},\"CFN\":{\"8\":{\"startIndex\":8,\"original\":\"খান\",\"corrected\":\"খাই\"},\"25\":{\"startIndex\":25,\"original\":\"যান\",\"corrected\":\"যাও\"}}}";
		
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		out.print(resp);
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
