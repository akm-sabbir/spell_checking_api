package repository;

import org.apache.log4j.Logger;

import java.util.HashMap;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


import dbm.DBMR;


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
    static Logger logger=  Logger.getLogger(RepositoryManager.class);
    boolean running=false;
    static RepositoryManager repositoryManager = null;
    
    public static long lastModifyTime ;
    private Repository [] registeredRepository;
    private int registeredRepositoryLength;
    public static final int REPOSITORY_LOADING_GRACE_TIME=30000;
    private RepositoryManager()
    {
    	
        registeredRepository = new Repository[150];
        registeredRepositoryLength = 0;
        running=true;
        setDaemon(true);
        lastModifyTime=System.currentTimeMillis()-REPOSITORY_LOADING_GRACE_TIME;
        start();
    }


    public static RepositoryManager getInstance()
    {
        if (repositoryManager == null)
        {
            CreateRepoManager();
        }
        return repositoryManager;
    }

    public synchronized void addRepository(Repository  p_repository)
    {
        if(p_repository==null)return;
          p_repository.reload(true);
          registeredRepository[registeredRepositoryLength++]=p_repository;
    }
    
    public void addRepository(Repository  p_repository,boolean reloadAll) 
    {
    	  if (p_repository == null) {
              return;
          }
          registeredRepository[registeredRepositoryLength++] = p_repository;
          if(reloadAll)
              p_repository.reload(reloadAll);
	}

    private synchronized static void CreateRepoManager()
    {
        if (repositoryManager == null)
        {
            repositoryManager = new RepositoryManager();
        }
    }

    public void  run()
    {
        Connection connection = null;
        Statement statement = null;
        HashMap<String,String> repositoryTableName = new HashMap<String,String>();        
        while (running)
        {
            try
            {
             long tempTime	 = System.currentTimeMillis()-REPOSITORY_LOADING_GRACE_TIME;
                connection = DBMR.getInstance().getConnection();
                statement = connection.createStatement(); //"select tableName, lastModifyTime from vbSequencer where lastModifyTime > ?");

                String sqlQuery ="select table_name, table_LastModificationTime from vbSequencer where table_LastModificationTime >=" +lastModifyTime;
            
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next())
                {
               
                    repositoryTableName.put(resultSet.getString(1),resultSet.getString(1));
                }
                resultSet.close();
                statement.close();
                statement = null;
                DBMR.getInstance().freeConnection(connection);
                connection = null;

              
               
               for(int j=0;j<registeredRepositoryLength;j++)
               {
            	   Repository r = registeredRepository[j];            	   
            	   
            	   if(repositoryTableName.get(r.getTableName())!=null)
            	   {
            		   logger.debug("Reloading repository with name :" +r.getTableName() +" for data change after :"+lastModifyTime);
            		   r.reload(false);
            	   }
               }
               lastModifyTime = tempTime;//TODO put a minus for safety

               repositoryTableName.clear();
                
            }
            catch(Exception ex)
            {
            
             logger.fatal("Exception in Repository Manager",ex);
            }
            finally
            {
                if(statement != null)
                    try{statement.close();}catch(Exception s){}
                if(connection != null)
                    try{DBMR.getInstance().freeConnection(connection);}catch(Exception c){}
            }

            try
            {
                sleep(1000);
            }catch(InterruptedException ie){}

        }
        repositoryTableName.clear();
        repositoryTableName = null;
    }

    public void shutDown()
    {
        running=false;
    }


	
}
