package _client_dbDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import _client_dbFile.DBConnectionMgr;

public class LoginMemberDAO {

	
	// 로그인 메소드
	public static int loginMember(String id, String pass) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String hashPass = "hash_pass 초기값";
		int flag = -1; // 0 : 성공!, 1 : 이미 로그인 되어있음, 2 : 로그인 실패!(아이디나 비밀번호가 틀림)
		DBConnectionMgr pool = null;

		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			con = pool.getConnection();
			sql = "select password from customer where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			hashPass = rs.getString("password");
			rs.close();
			pstmt.close();
			
			sql = "select seat from customer where member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			
			rs = pstmt.executeQuery();
			rs.next();
			int seat = rs.getInt("seat");
			
			if(seat > 0){
				flag = 1;
			}else{
				if (pass.equals(hashPass) )
					flag = 0;
				else{
					flag = 2;
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}
	
	public static boolean isEmpty(String seatNum){
		boolean isEmpty = true;
		int seat = 0;
		
		Connection con = null;
	      Statement statement = null;
	      ResultSet resultSet = null;      
	      DBConnectionMgr pool = null;
	      String sql = " select seat"
	            + " from customer"
	            + " where seat = "
	            + seatNum;
		
	      try{
		         pool = DBConnectionMgr.getInstance();
		         con = pool.getConnection();
		         statement = con.createStatement();
		         resultSet = statement.executeQuery(sql);
		         
		         if(resultSet.next()){
		        	 seat = resultSet.getInt("seat");
		         }
		      }catch (Exception e) {
		         e.printStackTrace();
		      }finally {
		         pool.freeConnection(con, statement, resultSet);
		         
		         if(seat == 0) isEmpty = true;
		         if(!(seat == 0)) isEmpty = false;
		      }
		return isEmpty;
	}
	
	public static List<Integer> seatSet(){
	      List<Integer> list = new ArrayList<>();
	      
	      Connection con = null;
	      Statement statement = null;
	      ResultSet resultSet = null;      
	      DBConnectionMgr pool = null;
	      String sql = " select seat"
	            + " from customer"
	            + " where seat > 0";
	      
	      try{
	         pool = DBConnectionMgr.getInstance();
	         con = pool.getConnection();
	         statement = con.createStatement();
	         resultSet = statement.executeQuery(sql);
	         
	         while(resultSet.next()){
	            int num = resultSet.getInt("seat");
	            
	            list.add(num);
	         }
	      }catch (Exception e) {
	         e.printStackTrace();
	      }finally {
	         pool.freeConnection(con, statement, resultSet);
	      }
	      
	      return list;
	   }
}
