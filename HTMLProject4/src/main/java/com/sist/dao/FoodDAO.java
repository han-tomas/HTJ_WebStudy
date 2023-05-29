package com.sist.dao;
// => 카테고리 => 카테고리별 맛집 => 맛집에 대한 상세보기 => 지도출력,검색
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
	// 5. 기능
	// 5-1. 카테고리 출력
	public List<CategoryVO> food_category_list()
	{
		// 카테고리 1개의 정보 = 번호,이미지,제목,부제목 => CategoryVO
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		try
		{
			getConnection();
			String sql = "SELECT cno,title,subject,poster "
					+ "FROM food_category "
					+ "ORDER BY cno ASC";
//			String sql = "SELECT /*+ INDEX_ASC(food_category fc_cno_pk)*/ cno,title,subject,poster "
//					+ "FROM food_category";
					
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				CategoryVO vo = new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setPoster(rs.getString(4));
				list.add(vo);
			}
			rs.close();
			/*
			 *  list => 받아서 브라우저로 전송 실행
			 *  		----------------- Servlet, JSP
			 *  		Spring => Servlet 제작 => DispatcherServlet
			 */
			
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
	// 5-1-1. 카테고리 정보
		public CategoryVO food_category_info(int cno) {
			CategoryVO vo = new CategoryVO();
			try {
				getConnection();
				String sql = "SELECT title, subject FROM food_category "
							+"WHERE cno = " + cno;
				ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				rs.next();
				vo.setTitle(rs.getString(1));
				vo.setSubject(rs.getString(2));
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disConnection();
			}
			return vo;
		}
	// 5-2. 카테고리별 맛집 ==> List<FoodVO> list = new ArrayList<FoodVO>;
	public List<FoodVO> food_category_data(int cno)
	{
		List<FoodVO> list = new ArrayList<FoodVO>();
		try
		{
			getConnection();
			String sql ="SELECT fno,name,poster,address,phone,type "
					+ "FROM food_house "
					+ "WHERE cno="+cno;
			ps= conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster =rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				/*
				 * 	서울특별시 종로구 돈화문로11가길 25
				 *	지번 서울시 종로구 익선동 156
				 */
				String address=rs.getString(4);
				address=address.substring(0,address.lastIndexOf("지"));
				vo.setAddress(address.trim());
				// 서울특별시 종로구 돈화문로11가길 25
				vo.setPhone(rs.getString(5));
				vo.setType(rs.getString(6));
				
				list.add(vo);
			}
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
	// 5-3. 맛집 상세보기 => FoodVO
	// 5-4. 맛집 검색 => List<FoodVO> list = new ArrayList<FoodVO>;
}