package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.dbconn.*;
public class GoodsDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static GoodsDAO dao;
	private CreateDataBase db = new CreateDataBase();
	public static GoodsDAO newInstance() 
	{
		if(dao==null)
			dao=new GoodsDAO();
		return dao;
	}
	// 기능
	// 1. 목록 => 12개씩
	public List<GoodsVO> goodsListData(int page)
	{
		List<GoodsVO> list = new ArrayList<GoodsVO>();
		try 
		{
			conn=db.getConnection();
			String sql="SELECT num "
					+ "FROM(SELCET rownum as num ";
			
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(page*rowSize)-(rowSize-1);
			int end= page*rowSize;
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs = ps.executeQuery();
			
		}catch(Exception ex)
		{
			
		}
		finally
		{
			
		}
		return list;
	}
	// 2. 상세보기 => 디자인된 파일로 출력
	public GoodsVO goodsDetailData(int no)
	{
		GoodsVO vo = new GoodsVO();
		try
		{
			conn=db.getConnection();
			String sql="SELECT no,goods_name,goods_sub,goods_price,goods_discount,goods_first_price,"
					+ "goods_delivery,goods_poster "
					+ "FROM goods_all "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSub(rs.getString(3));
			vo.setPrice(rs.getString(4));
			vo.setDicount(rs.getInt(5));
			vo.setFirst_price(rs.getString(6));
			vo.setDelivery(rs.getString(7));
			vo.setPoster(rs.getString(8));
			rs.close();
		}catch(Exception ex)
		{
			
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return vo;
	}
	
}
