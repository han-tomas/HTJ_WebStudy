package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;
public class ReserveDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db= new CreateDataBase();
	private static ReserveDAO dao;
	
	// 싱글턴
	public static ReserveDAO newInstance()
	{
		if(dao==null)
			dao = new ReserveDAO();
		return dao;
	}
	
	// 맛집 읽기
	public List<FoodVO> foodReserveData(String type)
	{
		List<FoodVO> list = new ArrayList<FoodVO>();
		try
		{
			conn=db.getConnection();
			String sql="SELECT poster,name,phone,reserve_day,fno "
					+ "FROM food_house "
					+ "WHERE type LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, type);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo = new FoodVO();
				String poster=rs.getString(1);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(2));
				vo.setPhone(rs.getString(3));
				vo.setReserve_day(rs.getString(4));
				vo.setFno(rs.getInt(5));
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
	// 예약 가능한 날짜 => 맛집마다 가능한 날을 읽어온다
	public String foodReserveDay(int fno)
	{
		String result="";
		try
		{
			conn=db.getConnection();
			String sql="SELECT reserve_day From food_house "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return result;
	}
	public String reserve_day_time(int rno)
	{
		String result="";
		try
		{
			conn=db.getConnection();
			String sql="SELECT time FROM reserve_day "
					+ "WHERE rno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, rno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return result;
	}
	public String reserve_get_time(int tno)
	{
		String result="";
		try
		{
			conn=db.getConnection();
			String sql="SELECT time FROM reserve_time "
					+ "WHERE tno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, tno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return result;
	}
	// 예약 등록
	public void reserve_ok(ReserveVO vo)
	{
		try
		{
			conn=db.getConnection();
			String sql="INSERT INTO reserve_info VALUES("
					+ "ri_no_seq.nextval,?,?,?,?,?,'n',SYSDATE)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setInt(2, vo.getFno());
			ps.setString(3, vo.getRday());
			ps.setString(4, vo.getRtime());
			ps.setString(5, vo.getInwon());
			ps.executeUpdate();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
	}
	// 예약 현황 출력
	public List<ReserveVO> reserveInfoData(String id)
	{
		List<ReserveVO> list = new ArrayList<ReserveVO>();
		try
		{
			conn=db.getConnection();
			String sql="SELECT no,fno,rday,rtime,inwon,TO_CHAR(regdate,'yyyy-MM-dd hh24:mi:ss'),"
					+ "foodGetPoster(fno),foodGetName(fno),foodGetPhone(fno),rok,id "
					+ "FROM reserve_info "
					+ "WHERE id=? "
					+ "ORDER BY no DESC";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				ReserveVO vo = new ReserveVO();
				vo.setNo(rs.getInt(1));
				vo.setFno(rs.getInt(2));
				vo.setRday(rs.getString(3));
				vo.setRtime(rs.getString(4));
				vo.setInwon(rs.getString(5));
				vo.setDbday(rs.getString(6));
				String poster=rs.getString(7);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(8));
				vo.setTel(rs.getString(9));
				vo.setRok(rs.getString(10));
				vo.setId(rs.getString(11));
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
	public List<ReserveVO> reserveAdminData()
	{
		List<ReserveVO> list = new ArrayList<ReserveVO>();
		try
		{
			conn=db.getConnection();
			String sql="SELECT no,fno,rday,rtime,inwon,TO_CHAR(regdate,'yyyy-MM-dd hh24:mi:ss'),"
					+ "foodGetPoster(fno),foodGetName(fno),foodGetPhone(fno),rok,id "
					+ "FROM reserve_info "
					+ "ORDER BY no DESC";
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				ReserveVO vo = new ReserveVO();
				vo.setNo(rs.getInt(1));
				vo.setFno(rs.getInt(2));
				vo.setRday(rs.getString(3));
				vo.setRtime(rs.getString(4));
				vo.setInwon(rs.getString(5));
				vo.setDbday(rs.getString(6));
				String poster=rs.getString(7);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(8));
				vo.setTel(rs.getString(9));
				vo.setRok(rs.getString(10));
				vo.setId(rs.getString(11));
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
}
