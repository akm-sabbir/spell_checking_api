package repository;
/**
 *
 * <p>Title: </p>
 *
 * <p>Description: Each repository will implement this interface in order to use Repository Manager</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface Repository
{
    /**
     * Reloads only updated row. It must not throw any exception.
     */
    public void reload(boolean realoadAll);

    /**
     * Returns the table name whose data it is reloading. Must be exact with vbSequencer table_name
     * @return String
     */
    public String getTableName();
}
