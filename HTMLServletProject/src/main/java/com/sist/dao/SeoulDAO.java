package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.dbconn.*;
import com.sist.dao.*;
public class SeoulDAO {
	private String[] tables= {
			"",
		"seoul_location",
		"seoul_nature",
		"seoul_shop"
	};
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db= new CreateDataBase();
	private static SeoulDAO dao;
	
	private static SeoulDAO newInstance()
	{
		if(dao==null)
			dao=new SeoulDAO();
		return dao;
	}
	// 1. 기능
	public List<SeoulVO> seoulListData(int type)
	{
		List<SeoulVO> list = new ArrayList<SeoulVO>();
		try
		{
			conn=db.getConnection();
			String sql="SELECT no,title,poster, rownum "
					+ "FROM "+tables[type]
					+"WHERE rownum<=20"	;	
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				SeoulVO vo = new SeoulVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return list;
	}
	// 2. 총페이지 구하기
	public int seoulTotalPage(int type)
	{
		int total=0;
		try
		{
			conn=db.getConnection();
			String sql ="SELECT CEIL(COUNT(*)/12.0) "
					+ "FROM "+tables[type];
			/* 
			 * FROM ?
			 * ps.setString(1,tables[type])
			 * FROM 'tabe_name'  ==> setString시 ' '가 자동으로 들어가기 때문에 X(오류) 
			 */ 
			/*
			 * 	INSERT INTO table_name VALUES(?,?)
			 * 	홍길동 남자
			 * 	INSERT INTO table_name VALUES('홍길동','남자')
			 *  ps.setString(1,"홍길동")
			 *  ps.setString(2,"남자")
			 */
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return total;
	}
	// 3. 상세보기
	public SeoulVO seoulDetailData(int no, int type)
	{
		SeoulVO vo = new SeoulVO();
		try
		{
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			
		}
		return vo;
	}
}
