package spell_checker_log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import repository.Repository;
import repository.RepositoryManager;


public class Spell_checker_logRepository implements Repository {
	Spell_checker_logDAO spell_checker_logDAO = null;
	
	public void setDAO(Spell_checker_logDAO spell_checker_logDAO)
	{
		this.spell_checker_logDAO = spell_checker_logDAO;
	}
	
	
	static Logger logger = Logger.getLogger(Spell_checker_logRepository.class);
	Map<Long, Spell_checker_logDTO>mapOfSpell_checker_logDTOToiD;
	Map<String, Set<Spell_checker_logDTO> >mapOfSpell_checker_logDTOTocontent;
	Map<String, Set<Spell_checker_logDTO> >mapOfSpell_checker_logDTOToresponse;
	Map<Integer, Set<Spell_checker_logDTO> >mapOfSpell_checker_logDTOTounknownWordCount;
	Map<Integer, Set<Spell_checker_logDTO> >mapOfSpell_checker_logDTOToclientCat;
	Map<Integer, Set<Spell_checker_logDTO> >mapOfSpell_checker_logDTOToip;
	Map<Long, Set<Spell_checker_logDTO> >mapOfSpell_checker_logDTOTotime;
	Map<String, Set<Spell_checker_logDTO> >mapOfSpell_checker_logDTOTosession;
	Map<Long, Set<Spell_checker_logDTO> >mapOfSpell_checker_logDTOTolastModificationTime;


	static Spell_checker_logRepository instance = null;  
	private Spell_checker_logRepository(){
		mapOfSpell_checker_logDTOToiD = new ConcurrentHashMap<>();
		mapOfSpell_checker_logDTOTocontent = new ConcurrentHashMap<>();
		mapOfSpell_checker_logDTOToresponse = new ConcurrentHashMap<>();
		mapOfSpell_checker_logDTOTounknownWordCount = new ConcurrentHashMap<>();
		mapOfSpell_checker_logDTOToclientCat = new ConcurrentHashMap<>();
		mapOfSpell_checker_logDTOToip = new ConcurrentHashMap<>();
		mapOfSpell_checker_logDTOTotime = new ConcurrentHashMap<>();
		mapOfSpell_checker_logDTOTosession = new ConcurrentHashMap<>();
		mapOfSpell_checker_logDTOTolastModificationTime = new ConcurrentHashMap<>();

		RepositoryManager.getInstance().addRepository(this);
	}

	public synchronized static Spell_checker_logRepository getInstance(){
		if (instance == null){
			instance = new Spell_checker_logRepository();
		}
		return instance;
	}

	public void reload(boolean reloadAll){
		if(spell_checker_logDAO == null)
		{
			return;
		}
		try {
			List<Spell_checker_logDTO> spell_checker_logDTOs = spell_checker_logDAO.getAllSpell_checker_log(reloadAll);
			for(Spell_checker_logDTO spell_checker_logDTO : spell_checker_logDTOs) {
				Spell_checker_logDTO oldSpell_checker_logDTO = getSpell_checker_logDTOByID(spell_checker_logDTO.iD);
				if( oldSpell_checker_logDTO != null ) {
					mapOfSpell_checker_logDTOToiD.remove(oldSpell_checker_logDTO.iD);
				
					if(mapOfSpell_checker_logDTOTocontent.containsKey(oldSpell_checker_logDTO.content)) {
						mapOfSpell_checker_logDTOTocontent.get(oldSpell_checker_logDTO.content).remove(oldSpell_checker_logDTO);
					}
					if(mapOfSpell_checker_logDTOTocontent.get(oldSpell_checker_logDTO.content).isEmpty()) {
						mapOfSpell_checker_logDTOTocontent.remove(oldSpell_checker_logDTO.content);
					}
					
					if(mapOfSpell_checker_logDTOToresponse.containsKey(oldSpell_checker_logDTO.response)) {
						mapOfSpell_checker_logDTOToresponse.get(oldSpell_checker_logDTO.response).remove(oldSpell_checker_logDTO);
					}
					if(mapOfSpell_checker_logDTOToresponse.get(oldSpell_checker_logDTO.response).isEmpty()) {
						mapOfSpell_checker_logDTOToresponse.remove(oldSpell_checker_logDTO.response);
					}
					
					if(mapOfSpell_checker_logDTOTounknownWordCount.containsKey(oldSpell_checker_logDTO.unknownWordCount)) {
						mapOfSpell_checker_logDTOTounknownWordCount.get(oldSpell_checker_logDTO.unknownWordCount).remove(oldSpell_checker_logDTO);
					}
					if(mapOfSpell_checker_logDTOTounknownWordCount.get(oldSpell_checker_logDTO.unknownWordCount).isEmpty()) {
						mapOfSpell_checker_logDTOTounknownWordCount.remove(oldSpell_checker_logDTO.unknownWordCount);
					}
					
					if(mapOfSpell_checker_logDTOToclientCat.containsKey(oldSpell_checker_logDTO.clientCat)) {
						mapOfSpell_checker_logDTOToclientCat.get(oldSpell_checker_logDTO.clientCat).remove(oldSpell_checker_logDTO);
					}
					if(mapOfSpell_checker_logDTOToclientCat.get(oldSpell_checker_logDTO.clientCat).isEmpty()) {
						mapOfSpell_checker_logDTOToclientCat.remove(oldSpell_checker_logDTO.clientCat);
					}
					
					if(mapOfSpell_checker_logDTOToip.containsKey(oldSpell_checker_logDTO.ip)) {
						mapOfSpell_checker_logDTOToip.get(oldSpell_checker_logDTO.ip).remove(oldSpell_checker_logDTO);
					}
					if(mapOfSpell_checker_logDTOToip.get(oldSpell_checker_logDTO.ip).isEmpty()) {
						mapOfSpell_checker_logDTOToip.remove(oldSpell_checker_logDTO.ip);
					}
					
					if(mapOfSpell_checker_logDTOTotime.containsKey(oldSpell_checker_logDTO.time)) {
						mapOfSpell_checker_logDTOTotime.get(oldSpell_checker_logDTO.time).remove(oldSpell_checker_logDTO);
					}
					if(mapOfSpell_checker_logDTOTotime.get(oldSpell_checker_logDTO.time).isEmpty()) {
						mapOfSpell_checker_logDTOTotime.remove(oldSpell_checker_logDTO.time);
					}
					
					if(mapOfSpell_checker_logDTOTosession.containsKey(oldSpell_checker_logDTO.session)) {
						mapOfSpell_checker_logDTOTosession.get(oldSpell_checker_logDTO.session).remove(oldSpell_checker_logDTO);
					}
					if(mapOfSpell_checker_logDTOTosession.get(oldSpell_checker_logDTO.session).isEmpty()) {
						mapOfSpell_checker_logDTOTosession.remove(oldSpell_checker_logDTO.session);
					}
					
					if(mapOfSpell_checker_logDTOTolastModificationTime.containsKey(oldSpell_checker_logDTO.lastModificationTime)) {
						mapOfSpell_checker_logDTOTolastModificationTime.get(oldSpell_checker_logDTO.lastModificationTime).remove(oldSpell_checker_logDTO);
					}
					if(mapOfSpell_checker_logDTOTolastModificationTime.get(oldSpell_checker_logDTO.lastModificationTime).isEmpty()) {
						mapOfSpell_checker_logDTOTolastModificationTime.remove(oldSpell_checker_logDTO.lastModificationTime);
					}
					
					
				}
				if(spell_checker_logDTO.isDeleted == 0) 
				{
					
					mapOfSpell_checker_logDTOToiD.put(spell_checker_logDTO.iD, spell_checker_logDTO);
				
					if( ! mapOfSpell_checker_logDTOTocontent.containsKey(spell_checker_logDTO.content)) {
						mapOfSpell_checker_logDTOTocontent.put(spell_checker_logDTO.content, new HashSet<>());
					}
					mapOfSpell_checker_logDTOTocontent.get(spell_checker_logDTO.content).add(spell_checker_logDTO);
					
					if( ! mapOfSpell_checker_logDTOToresponse.containsKey(spell_checker_logDTO.response)) {
						mapOfSpell_checker_logDTOToresponse.put(spell_checker_logDTO.response, new HashSet<>());
					}
					mapOfSpell_checker_logDTOToresponse.get(spell_checker_logDTO.response).add(spell_checker_logDTO);
					
					if( ! mapOfSpell_checker_logDTOTounknownWordCount.containsKey(spell_checker_logDTO.unknownWordCount)) {
						mapOfSpell_checker_logDTOTounknownWordCount.put(spell_checker_logDTO.unknownWordCount, new HashSet<>());
					}
					mapOfSpell_checker_logDTOTounknownWordCount.get(spell_checker_logDTO.unknownWordCount).add(spell_checker_logDTO);
					
					if( ! mapOfSpell_checker_logDTOToclientCat.containsKey(spell_checker_logDTO.clientCat)) {
						mapOfSpell_checker_logDTOToclientCat.put(spell_checker_logDTO.clientCat, new HashSet<>());
					}
					mapOfSpell_checker_logDTOToclientCat.get(spell_checker_logDTO.clientCat).add(spell_checker_logDTO);
					
					if( ! mapOfSpell_checker_logDTOToip.containsKey(spell_checker_logDTO.ip)) {
						mapOfSpell_checker_logDTOToip.put(spell_checker_logDTO.ip, new HashSet<>());
					}
					mapOfSpell_checker_logDTOToip.get(spell_checker_logDTO.ip).add(spell_checker_logDTO);
					
					if( ! mapOfSpell_checker_logDTOTotime.containsKey(spell_checker_logDTO.time)) {
						mapOfSpell_checker_logDTOTotime.put(spell_checker_logDTO.time, new HashSet<>());
					}
					mapOfSpell_checker_logDTOTotime.get(spell_checker_logDTO.time).add(spell_checker_logDTO);
					
					if( ! mapOfSpell_checker_logDTOTosession.containsKey(spell_checker_logDTO.session)) {
						mapOfSpell_checker_logDTOTosession.put(spell_checker_logDTO.session, new HashSet<>());
					}
					mapOfSpell_checker_logDTOTosession.get(spell_checker_logDTO.session).add(spell_checker_logDTO);
					
					if( ! mapOfSpell_checker_logDTOTolastModificationTime.containsKey(spell_checker_logDTO.lastModificationTime)) {
						mapOfSpell_checker_logDTOTolastModificationTime.put(spell_checker_logDTO.lastModificationTime, new HashSet<>());
					}
					mapOfSpell_checker_logDTOTolastModificationTime.get(spell_checker_logDTO.lastModificationTime).add(spell_checker_logDTO);
					
				}
			}
			
		} catch (Exception e) {
			logger.debug("FATAL", e);
		}
	}
	
	public List<Spell_checker_logDTO> getSpell_checker_logList() {
		List <Spell_checker_logDTO> spell_checker_logs = new ArrayList<Spell_checker_logDTO>(this.mapOfSpell_checker_logDTOToiD.values());
		return spell_checker_logs;
	}
	
	
	public Spell_checker_logDTO getSpell_checker_logDTOByID( long ID){
		return mapOfSpell_checker_logDTOToiD.get(ID);
	}
	
	
	public List<Spell_checker_logDTO> getSpell_checker_logDTOBycontent(String content) {
		return new ArrayList<>( mapOfSpell_checker_logDTOTocontent.getOrDefault(content,new HashSet<>()));
	}
	
	
	public List<Spell_checker_logDTO> getSpell_checker_logDTOByresponse(String response) {
		return new ArrayList<>( mapOfSpell_checker_logDTOToresponse.getOrDefault(response,new HashSet<>()));
	}
	
	
	public List<Spell_checker_logDTO> getSpell_checker_logDTOByunknown_word_count(int unknown_word_count) {
		return new ArrayList<>( mapOfSpell_checker_logDTOTounknownWordCount.getOrDefault(unknown_word_count,new HashSet<>()));
	}
	
	
	public List<Spell_checker_logDTO> getSpell_checker_logDTOByclient_cat(int client_cat) {
		return new ArrayList<>( mapOfSpell_checker_logDTOToclientCat.getOrDefault(client_cat,new HashSet<>()));
	}
	
	
	public List<Spell_checker_logDTO> getSpell_checker_logDTOByip(int ip) {
		return new ArrayList<>( mapOfSpell_checker_logDTOToip.getOrDefault(ip,new HashSet<>()));
	}
	
	
	public List<Spell_checker_logDTO> getSpell_checker_logDTOBytime(long time) {
		return new ArrayList<>( mapOfSpell_checker_logDTOTotime.getOrDefault(time,new HashSet<>()));
	}
	
	
	public List<Spell_checker_logDTO> getSpell_checker_logDTOBysession(String session) {
		return new ArrayList<>( mapOfSpell_checker_logDTOTosession.getOrDefault(session,new HashSet<>()));
	}
	
	
	public List<Spell_checker_logDTO> getSpell_checker_logDTOBylastModificationTime(long lastModificationTime) {
		return new ArrayList<>( mapOfSpell_checker_logDTOTolastModificationTime.getOrDefault(lastModificationTime,new HashSet<>()));
	}

	
	@Override
	public String getTableName() {
		String tableName = "";
		try{
			tableName = "spell_checker_log";
		}catch(Exception ex){
			logger.debug("FATAL",ex);
		}
		return tableName;
	}
}


