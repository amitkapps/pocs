package com.amitk.j2ee.jta.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AmitkJTADao {
	
	Context ctx;
	String dsJNDIName;
	DataSource ds;
	Connection conn;
	
	public AmitkJTADao(String dataSourceJNDIName){
		this.dsJNDIName = dataSourceJNDIName;
	}
	private Context getContext()
		throws NamingException{
		if(null == ctx)
			ctx = new InitialContext();
		
		return ctx;
		
	}
	
	private DataSource getDataSource()
		throws NamingException{
		if(null == ds)
			ds = (DataSource) getContext().lookup(dsJNDIName);
		
		return ds;
	}
	
	private Connection getConnection()
		throws NamingException, SQLException{
		if(null == conn)
			conn = getDataSource().getConnection();
		
		return conn;
	}
	
	public boolean updateRecord(int id, Date date)
		throws NamingException, SQLException{
		PreparedStatement ps = null;
		try {
			String sql = "update mt_amitk_jta set aj_update_dt = ? where aj_id = ? ";
			getConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setDate(1, date);
			ps.setInt(2, id);
			
			ps.execute();
			
		} finally{
			if(null != ps)
				ps.close();
			if(null != conn)
				conn.close();
		}
		
		return true;
	}
}
