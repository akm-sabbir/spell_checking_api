package word_content;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import repository.Repository;
import repository.RepositoryManager;


public class Word_contentRepository implements Repository {
	Word_contentDAO word_contentDAO = null;
	
	public void setDAO(Word_contentDAO word_contentDAO)
	{
		this.word_contentDAO = word_contentDAO;
	}
	
	
	static Logger logger = Logger.getLogger(Word_contentRepository.class);
	Map<Long, Word_contentDTO>mapOfWord_contentDTOToiD;
	Map<String, Set<Word_contentDTO> >mapOfWord_contentDTOToword;
	Map<String, Set<Word_contentDTO> >mapOfWord_contentDTOTolanguage;
	Map<Long, Set<Word_contentDTO> >mapOfWord_contentDTOTolastModificationTime;


	static Word_contentRepository instance = null;  
	private Word_contentRepository(){
		mapOfWord_contentDTOToiD = new ConcurrentHashMap<>();
		mapOfWord_contentDTOToword = new ConcurrentHashMap<>();
		mapOfWord_contentDTOTolanguage = new ConcurrentHashMap<>();
		mapOfWord_contentDTOTolastModificationTime = new ConcurrentHashMap<>();

		RepositoryManager.getInstance().addRepository(this);
	}

	public synchronized static Word_contentRepository getInstance(){
		if (instance == null){
			instance = new Word_contentRepository();
		}
		return instance;
	}

	public void reload(boolean reloadAll){
		if(word_contentDAO == null)
		{
			return;
		}
		try {
			List<Word_contentDTO> word_contentDTOs = word_contentDAO.getAllWord_content(reloadAll);
			for(Word_contentDTO word_contentDTO : word_contentDTOs) {
				Word_contentDTO oldWord_contentDTO = getWord_contentDTOByID(word_contentDTO.iD);
				if( oldWord_contentDTO != null ) {
					mapOfWord_contentDTOToiD.remove(oldWord_contentDTO.iD);
				
					if(mapOfWord_contentDTOToword.containsKey(oldWord_contentDTO.word)) {
						mapOfWord_contentDTOToword.get(oldWord_contentDTO.word).remove(oldWord_contentDTO);
					}
					if(mapOfWord_contentDTOToword.get(oldWord_contentDTO.word).isEmpty()) {
						mapOfWord_contentDTOToword.remove(oldWord_contentDTO.word);
					}
					
					if(mapOfWord_contentDTOTolanguage.containsKey(oldWord_contentDTO.language)) {
						mapOfWord_contentDTOTolanguage.get(oldWord_contentDTO.language).remove(oldWord_contentDTO);
					}
					if(mapOfWord_contentDTOTolanguage.get(oldWord_contentDTO.language).isEmpty()) {
						mapOfWord_contentDTOTolanguage.remove(oldWord_contentDTO.language);
					}
					
					if(mapOfWord_contentDTOTolastModificationTime.containsKey(oldWord_contentDTO.lastModificationTime)) {
						mapOfWord_contentDTOTolastModificationTime.get(oldWord_contentDTO.lastModificationTime).remove(oldWord_contentDTO);
					}
					if(mapOfWord_contentDTOTolastModificationTime.get(oldWord_contentDTO.lastModificationTime).isEmpty()) {
						mapOfWord_contentDTOTolastModificationTime.remove(oldWord_contentDTO.lastModificationTime);
					}
					
					
				}
				/*
				if(word_contentDTO.isDeleted == false) 
				{
					
					mapOfWord_contentDTOToiD.put(word_contentDTO.iD, word_contentDTO);
				
					if( ! mapOfWord_contentDTOToword.containsKey(word_contentDTO.word)) {
						mapOfWord_contentDTOToword.put(word_contentDTO.word, new HashSet<>());
					}
					mapOfWord_contentDTOToword.get(word_contentDTO.word).add(word_contentDTO);
					
					if( ! mapOfWord_contentDTOTolanguage.containsKey(word_contentDTO.language)) {
						mapOfWord_contentDTOTolanguage.put(word_contentDTO.language, new HashSet<>());
					}
					mapOfWord_contentDTOTolanguage.get(word_contentDTO.language).add(word_contentDTO);
					
					if( ! mapOfWord_contentDTOTolastModificationTime.containsKey(word_contentDTO.lastModificationTime)) {
						mapOfWord_contentDTOTolastModificationTime.put(word_contentDTO.lastModificationTime, new HashSet<>());
					}
					mapOfWord_contentDTOTolastModificationTime.get(word_contentDTO.lastModificationTime).add(word_contentDTO);
					
				}*/
			}
			
		} catch (Exception e) {
			logger.debug("FATAL", e);
		}
	}
	
	public List<Word_contentDTO> getWord_contentList() {
		List <Word_contentDTO> word_contents = new ArrayList<Word_contentDTO>(this.mapOfWord_contentDTOToiD.values());
		return word_contents;
	}
	
	
	public Word_contentDTO getWord_contentDTOByID( long ID){
		return mapOfWord_contentDTOToiD.get(ID);
	}
	
	
	public List<Word_contentDTO> getWord_contentDTOByword(String word) {
		return new ArrayList<>( mapOfWord_contentDTOToword.getOrDefault(word,new HashSet<>()));
	}
	
	
	public List<Word_contentDTO> getWord_contentDTOBylanguage(String language) {
		return new ArrayList<>( mapOfWord_contentDTOTolanguage.getOrDefault(language,new HashSet<>()));
	}
	
	
	public List<Word_contentDTO> getWord_contentDTOBylastModificationTime(long lastModificationTime) {
		return new ArrayList<>( mapOfWord_contentDTOTolastModificationTime.getOrDefault(lastModificationTime,new HashSet<>()));
	}

	
	@Override
	public String getTableName() {
		String tableName = "";
		try{
			tableName = "word_content";
		}catch(Exception ex){
			logger.debug("FATAL",ex);
		}
		return tableName;
	}

	@Override
	public boolean isUpdateNeeded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void set_size() {
		// TODO Auto-generated method stub
		
	}
}


