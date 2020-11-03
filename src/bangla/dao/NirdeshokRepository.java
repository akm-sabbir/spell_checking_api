package bangla.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import bangla.grammarchecker.NirdeshokErrorChecker;
import dbm.DBMR;
import repository.Repository;
import repository.RepositoryManager;

public class NirdeshokRepository implements Repository {
	static Logger logger = Logger.getLogger(NirdeshokRepository.class);
	static NirdeshokRepository instance = null;
	public static final String tableName="nirdeshok";
	
    private NirdeshokRepository() {
    	RepositoryManager.getInstance().addRepository(this);
    }
    
    public static NirdeshokRepository getInstance() {
    	if (instance == null){
			synchronized (tableName) {
				if(instance == null)
					instance = new NirdeshokRepository();
			}
			
		}
		return instance;
    }
    public List<GrammarDto> getNirdeshok(String sql, List<String> columns){
		List<GrammarDto> data_DTO = new ArrayList<>();
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		GrammarDto  dto= null;
		try{
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			stmt.setQueryTimeout(20);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				dto = new GrammarDto();
				dto.marker = rs.getString(columns.get(0));
				data_DTO.add(dto);

			}				
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{ 
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e){}
			
			try{ 
				if (connection != null){ 
					DBMR.getInstance().freeConnection(connection); 
				} 
			}catch(Exception ex2){}
		}
		return data_DTO;
	}
	@Override
	public void reload(boolean realoadAll) {
		String sql = "SELECT indicator ";
		sql += " FROM nirdeshok " ;
		String[] columns = {"indicator"};
		List<String> column_ = Arrays.asList(columns);
		List<GrammarDto> nirdeshokList = getNirdeshok(sql, column_);
		NirdeshokErrorChecker.loadNirdeshok(nirdeshokList);
	}

	@Override
	public String getTableName() {
		return tableName;
	}

}
