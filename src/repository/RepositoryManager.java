package repository;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


import dbm.*;


/**
 *
 * <p>Title: </p>
 *
 * <p>Description:
 * 1. Call the getInstance() method from main before creating other thread
 * 2. From each repository call RepositoryManager.getInstance().addRepository(this) once at the end of its constructor</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class RepositoryManager extends Thread 
{
    boolean running=false;
    static RepositoryManager repositoryManager = null;
    public static Logger logger = Logger.getLogger(RepositoryManager.class);
    
   
    
    public static long lastModifyTime ;
    private ArrayList<Repository> registeredRepository;
    private int registeredRepositoryLength;
    public static final int REPOSITORY_LOADING_GRACE_TIME=30000;
    
    public static final int REPOSITORY_ARRAY_SIZE=100;
    
    public static final int REPOSITORY_CHECK_SHORT_DELAY_TIME= 15000; // 15 secs
    public static final int REPOSITORY_CHECK_LONG_DELAY_TIME= 180000; // 15 secs
    public static int switchCount = 0;
    public static int limitingSwitch = 12;
    private RepositoryManager()
    {
    	super("Repository Manager");
        registeredRepository = new ArrayList<Repository>(REPOSITORY_ARRAY_SIZE);
        registeredRepositoryLength = 0;
        running=true;
        setDaemon(true);
        
    }

    public static RepositoryManager getInstance()
    {
        if (repositoryManager == null)
        {
            CreateRepoManager();
            repositoryManager.start();
        }
        return repositoryManager;
    }
    public synchronized void startMachine() {
    	repositoryManager.start();
    }
    public synchronized void addRepository(Repository  p_repository)
    {
        if(p_repository==null)return;
        registeredRepository.add(p_repository);
        registeredRepositoryLength++;
    }
    public synchronized void addRepository(Repository  p_repository, boolean reloadAll)
    {
        if(p_repository==null)return;
        registeredRepository.add(p_repository);
        registeredRepositoryLength++;
        if(reloadAll)
          p_repository.reload(true);
    }
    private synchronized static void CreateRepoManager()
    {
        if (repositoryManager == null)
        {
            repositoryManager = new RepositoryManager();
        }
    }
    public synchronized void setSwitchCount() {
    	switchCount += 1;
    	switchCount = switchCount % limitingSwitch;
    	return;
    }
   
    public void run()
    {
        Connection connection = null;
        Statement statement = null;

        Set<String> repositoryTableName = new HashSet<String>();
//        Set<String> repositoryName = new HashSet<String>();
        
        while (running) {
            try {
               long tempTime = System.currentTimeMillis()-REPOSITORY_LOADING_GRACE_TIME;
               /*
               try {
            	   connection = DBMR.getInstance().getConnection();
             
            	   statement = connection.createStatement(); //"select tableName, lastModifyTime from vbSequencer where lastModifyTime > ?");
              

                String sqlQuery ="select table_name, table_LastModificationTime from vbSequencer where table_LastModificationTime >=" + (lastModifyTime) ;//+" and table_LastModificationTime<="+System.currentTimeMillis();
//                System.out.println("sqlQuery " + sqlQuery);
                ResultSet resultSet;
              
                	resultSet = statement.executeQuery(sqlQuery);
                	while (resultSet.next()) {
                    	repositoryTableName.add(resultSet.getString(1));
//                    	logger.debug("dirty table names "+resultSet.getString(1));
                    	
//                        repositoryName.add(resultSet.getString(3));
                    }
                    resultSet.close();
                } catch (NullPointerException ex){      	
                }
                
                statement.close();
                statement = null;
                DBMR.getInstance().freeConnection(connection);
                connection = null;
				*/
               lastModifyTime = tempTime;//TODO put a minus for safety
               
               //logger.debug("repositoryName " + repositoryName + " registeredRepositoryLength " + registeredRepositoryLength);
               
               for(int j = 0; j < registeredRepositoryLength; j++) {
            	   Repository r = registeredRepository.get(j);    
            	   logger.debug("r.getRepoName() " + r.getTableName());
            	   logger.debug("repositoryTableName " + repositoryTableName);
            	   System.out.println("I am calling update function on dictionary: " + r.getTableName());
            	   r.reload(true);
               }
              // clearRepository();// need to be deactivated before server deployment
               shutDown();
               repositoryTableName.clear();
               setSwitchCount();
               
                
            } catch(Exception ex) { logger.fatal("Exception in Repository Manager",ex);
            } finally {
                if(statement != null)
                    try{statement.close();}catch(Exception s){}
                if(connection != null)
                    try{DBMR.getInstance().freeConnection(connection);}catch(Exception c){}
            }

            try {
            	if (switchCount <= 8)
            		sleep(REPOSITORY_CHECK_SHORT_DELAY_TIME);
            	else
            		sleep(REPOSITORY_CHECK_LONG_DELAY_TIME);
            } catch(InterruptedException ie){}
          
        }
        repositoryTableName.clear();
        repositoryTableName = null;
    }
    public void clearRepository() {
    	registeredRepository.clear();
    	registeredRepositoryLength = 0;
    	return;
    }
    public void shutDown() {
        running=false;
    }
}
