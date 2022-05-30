package com.Apharma.sep4;

import com.Apharma.sep4.Persistence.DataWarehouse.DataWarehouseDAO;
import com.Apharma.sep4.Persistence.DataWarehouse.iDataWarehouseDAO;
import com.Apharma.sep4.Persistence.DatabaseDAO.ReadingDAO;
import com.Apharma.sep4.Persistence.DatabaseDAO.iReadingDAO;
import com.Apharma.sep4.RawData.WebSocketClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class BeanConfig
{
	private final String dataWarehouse = "jdbc:postgresql://aajqdx6jxlql49.cuqtxh0uawoy.eu-central-1.rds.amazonaws.com:5432/postgres?currentSchema=\"DW_aPHarma\"";
	private final String loriotServer = "wss://iotnet.teracom.dk/app?token=vnoUcQAAABFpb3RuZXQudGVyYWNvbS5ka" +
																					"-iuwG5H1SHPkGogk2YUH3Y=";
	@Bean
	public WebSocketClient getWebSocket()
	{
		return new WebSocketClient(loriotServer);
	}
	
	/**
	 Method for getting a DataWarehouseDAO.
	 
	 @return Instance of DataWarehouseDAO singleton
	 */
	@Bean
	@Scope("singleton")
	public iDataWarehouseDAO warehouseDAO()
	{
		return new DataWarehouseDAO();
	}
	
	@Bean
	@Scope("singleton")
	public iReadingDAO readingDAO()
	{
		return new ReadingDAO();
	}
	
	@Bean(name = "jdbcUrl")
	public String jdbcUrl()
	{
		return dataWarehouse;
	}
	
	@Bean(name = "username")
	public String username()
	{
		return "postgres";
	}
	
	@Bean(name = "password")
	public String password()
	{
		return "password";
	}
}
