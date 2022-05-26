package com.Apharma.sep4.Persistence.DAO.DataWarehouse.DbUtils;


import javax.xml.datatype.DatatypeConfigurationException;
import java.sql.ResultSet;
import java.sql.SQLException;

/** class imported from Ole Hougaard's github
 * Available at: https://github.com/olehougaard/sdj3-a20/blob/master/cars-spring-soap/src/main/java/dk/via/db/DataMapper.java**/

public interface DataMapper<T> {
    T create(ResultSet rs) throws SQLException, DatatypeConfigurationException;
}
