package com.sist.dao;
import java.util.*;
import java.sql.*;
public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	private static FoodDAO dao;
	// 1. 드라이버 등록 => 한번 수행 => 시작과 동시에 한번 수행 , 멤버변수 초기화 : 생성자
	public FoodDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ClassNotFoundException => 체크 예외처리 => 반드시 예외처리
			//  java.io , java.net , java.sql
			
		}catch(Exception ex) {}
	}
	// 2. 오라클 연결
	public void getConnection()
	{
		try 
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
			
		} catch (Exception e) {}
	}
	// 3. 오라클 해제
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close(); // 통신 닫기
			if(conn!=null) conn.close(); // 오라클 닫기
		}catch(Exception ex) {}
	}
	// 4. 싱글턴 설정 => static은 메모리 공간이 1개만 가지고 있다.
	// 메모리누수 현상을 방지
	// DAO => new를 이용해서 생성 => 사용하지 않는 DAO가 오라클을 연결하고 있다.
	// 싱글턴은 데이터베이스에서는 필수조건
	public static FoodDAO newInstance()
	{
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	
	public List<FoodVO> foodAllData()
	{
		List<FoodVO> list = new ArrayList<FoodVO>();
		try
		{
			getConnection();
			String sql ="SELECT name,address,poster,phone,type "
					+ "FROM food_house "
					+ "ORDER BY fno ASC";
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo = new FoodVO();
				String name=rs.getString(1);
				vo.setName(name);
				String addr=rs.getString(2);
				addr=addr.substring(0,addr.lastIndexOf("지번"));
				vo.setAddress(addr);
				String poster=rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setPhone(rs.getString(4));
				vo.setType(rs.getString(5));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
}
