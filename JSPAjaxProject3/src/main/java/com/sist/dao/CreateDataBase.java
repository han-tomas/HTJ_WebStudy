package com.sist.dao;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
public class CreateDataBase {
    public Connection getConnection()
    {
    	Connection conn=null;
    	try
    	{
    		Context init=new InitialContext();
    		Context c=(Context)init.lookup("java://comp/env");
    		DataSource ds=(DataSource)c.lookup("jdbc/oracle");
    		conn=ds.getConnection();
    	}catch(Exception ex) 
    	{
    		ex.printStackTrace();
    	}
    	
    	return conn;
    	
    }
    public void disConnection(Connection conn,PreparedStatement ps)
    {
    	try
    	{
    		if(ps!=null) ps.close();
    		if(conn!=null) conn.close();
    	}catch(Exception ex) {}
    	
    }
    public void disConnection(Connection conn,CallableStatement ps) //오버로딩 // 프로시저 호출할때 쓰는 메소드 
    {
    	try
    	{
    		if(ps!=null) ps.close();
    		if(conn!=null) conn.close();
    	}catch(Exception ex) {}
    	
    }
}
