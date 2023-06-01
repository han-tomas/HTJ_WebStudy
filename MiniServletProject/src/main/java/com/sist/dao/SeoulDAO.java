package com.sist.dao;
import java.util.*;
import java.sql.*;
public class SeoulDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	private static SeoulDAO dao;
	// 1. 드라이버 등록 => 한번 수행 => 시작과 동시에 한번 수행 , 멤버변수 초기화 : 생성자
	public SeoulDAO()
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
	public static SeoulDAO newInstance()
	{
		if(dao==null)
			dao=new SeoulDAO();
		return dao;
	}
	// 5. 기능
	// 목록 => SQL(인라인뷰 => 페이징 기법)
	public List<SeoulVO> seoulListData(int page)
	{
		List<SeoulVO> list = new ArrayList<SeoulVO>();
		try 
		{
			getConnection();
			String sql="SELECT no,title,poster,num "
					+ "FROM(SELECT no,title,poster,rownum as num "
					+ "FROM(SELECT no,title,poster "
					+ "FROM seoul_location ORDER BY no ASC)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(page*rowSize)-(rowSize-1);
			int end=(page*rowSize);
			ps.setInt(1, start);
			ps.setInt(2, end);
			
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
			disConnection();
		}
		return list;
	}
	public List<SeoulVO> seoulHotelListData(int page)
	{
		List<SeoulVO> list = new ArrayList<SeoulVO>();
		try 
		{
			getConnection();
			String sql="SELECT no,title,poster,num "
					+ "FROM(SELECT no,title,poster,rownum as num "
					+ "FROM(SELECT no,title,poster "
					+ "FROM seoul_hotel ORDER BY no ASC)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=10;
			int start=(page*rowSize)-(rowSize-1);
			int end=(page*rowSize);
			ps.setInt(1, start);
			ps.setInt(2, end);
			
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
			disConnection();
		}
		return list;
	}
}
