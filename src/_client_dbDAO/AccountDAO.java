package _client_dbDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import _client_dbFile.DBConnectionMgr;
import all_DTO.MembershipDTO;


public class AccountDAO {
	
	

	public String log_id;
	public String log_name;
	
	public AccountDAO(){
		
	}
	
	
	public List<MembershipDTO> getList() {
		List<MembershipDTO> list = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DBConnectionMgr pool = null;
		
		try{
			pool = DBConnectionMgr.getInstance();
			conn= pool.getConnection();
		}catch(Exception eeq1){
			eeq1.printStackTrace();
		}
		String sql = "select MEMBER_NUMBER, MEMBER_ID, PASSWORD, NAME, AGE, PHONE_NUMBER, AUTH, SEAT, MINUTES  \n";
		sql += "from CUSTOMER \n";
			
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// 모든 data 가져올때
			while(rs.next()){
				MembershipDTO dto = new MembershipDTO();
				dto.setMember_number(rs.getInt(1));
				dto.setMember_id(rs.getString(2));
				dto.setPassword(rs.getString(3));
				dto.setName(rs.getString(4));
				dto.setAge(rs.getInt(5));
				dto.setPhone_number(rs.getString(6));
				dto.setAuth(rs.getInt(7));
				dto.setSeat(rs.getInt(8));
				dto.setMinutes(rs.getInt(9));
			
				list.add(dto);
			}
		}catch(SQLException e1){
			e1.printStackTrace();
		}finally{
			pool.freeConnection(conn, stmt, rs);
		}
		return list;
	}
	//
	
	
	
	
	//회원추가 
	public boolean addMember(MembershipDTO dto){
		
		int seq=10;
		List<MembershipDTO> list = getList();
		
		for (int i=0;i<list.size();i++) {
		 	if(seq<list.get(i).getMember_number()){
			seq = list.get(i).getMember_number();}
		}
		seq++;
		
		
		String sql = " insert into CUSTOMER " // 커리문을 두번쓸수 있기때문에 맨앞과 끝은 한칸씩 떨어뜨리는게좋다.
		             + " (MEMBER_NUMBER, MEMBER_ID, PASSWORD, NAME, AGE, PHONE_NUMBER, AUTH, SEAT, MINUTES) "
		             + " values(seq_member_number.nextval,?, ?, ?, ?, ?, ?, ?, ?)";
		
		int count = 0;
		Connection conn = null;
		PreparedStatement psmt = null; // 순서대로 딱딱딱딱딱
		ResultSet rs = null;
		DBConnectionMgr pool = null;
		
		try{
		pool = DBConnectionMgr.getInstance();
		conn = pool.getConnection();
		
		
		psmt = conn.prepareStatement(sql);
		psmt.setString(1, dto.getMember_id());
		
		
		psmt.setString(2, dto.getPassword());
		psmt.setString(3, dto.getName());
		psmt.setInt(4, dto.getAge());
		psmt.setString(5, dto.getPhone_number());
		psmt.setInt(6, dto.getAuth());
		psmt.setInt(7, dto.getSeat());
		psmt.setInt(8, dto.getMinutes());
		
		count = psmt.executeUpdate();
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			pool.freeConnection(conn, psmt);
			System.out.println("6/6 addMember"); // add작업은5/6 작업이 필요하지않아서 여기선 사용되지않음 
		}
		
		return count > 0? true: false;
	}
	
	public MembershipDTO search(String member_id) throws SQLException {
		MembershipDTO dto = null;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DBConnectionMgr pool = null;
		
		// 1 MEMBER_NUMBER, 
		//2 MEMBER_ID, 
		//3 PASSWORD, 
		//4 NAME, 
		//5 AGE, 
		//6 PHONE_NUMBER, 
		//7 AUTH, 
		//8 SEAT, 
		//9 MINUTES
		String sql = "select MEMBER_ID, NAME, PHONE_NUMBER, PASSWORD, AGE \n";
		sql += "from CUSTOMER \n";
		sql += "Where MEMBER_ID = '" + member_id + "'";

		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
			stmt = conn.createStatement();
			// select를 제외한 나머지는 data를 넣는것, select는 data를 가져오는것
			rs = stmt.executeQuery(sql);
			// 하나만 찾을때
			if (rs.next()) {
				dto = new MembershipDTO();
				dto.setMember_id(rs.getString("member_id"));// id는 컬럼이름, rs에서 컬럼 id정보를 세터를 통해
												// dto에 넣음
				dto.setName(rs.getString("NAME"));
				dto.setPhone_number(rs.getString("PHONE_NUMBER")); // int 는 getInt사용
				dto.setPassword(rs.getString("PASSWORD"));
				dto.setAge(rs.getInt("AGE"));
			}

		} catch (Exception e) {

		} finally {
			pool.freeConnection(conn, stmt, rs);
		}
		return dto;
	}
	
	
	public MembershipDTO search1(String member_id,String phone_number) throws SQLException {
		MembershipDTO dto = null;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DBConnectionMgr pool = null;
		
		
		String sql = "select MEMBER_ID, NAME, PHONE_NUMBER, PASSWORD \n";
		sql += "from CUSTOMER \n";
		sql += "Where MEMBER_ID = '" + member_id +"' or PHONE_NUMBER = '"+ phone_number+"'" ;


		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
			stmt = conn.createStatement();
			// select를 제외한 나머지는 data를 넣는것, select는 data를 가져오는것
			rs = stmt.executeQuery(sql);
			// 하나만 찾을때
			if (rs.next()) {
				dto = new MembershipDTO();
				dto.setMember_id(rs.getString("id"));// id는 컬럼이름, rs에서 컬럼 id정보를 세터를 통해
											// dto에 넣음
				dto.setPhone_number(rs.getString("email")); // int 는 getInt사용
			}

		} catch (Exception e) {

		} finally {
			pool.freeConnection(conn, stmt, rs);

		}
		return dto;
	}
	
	// 수정 
	public int update(String phone_number, String password) throws SQLException {
		int cnt = 0;
		Connection conn = null;
		Statement stmt = null;
		DBConnectionMgr pool = null;

		
		// 1 MEMBER_NUMBER, 
				//2 MEMBER_ID, 
				//3 PASSWORD, 
				//4 NAME, 
				//5 AGE, 
				//6 PHONE_NUMBER, 
				//7 AUTH, 
				//8 SEAT, 
				//9 MINUTES
		String sql = "update CUSTOMER set PASSWORD = '" + password + "', PHONE_NUMBER = '"+ phone_number + "' \n";
		sql += "where MEMBER_ID = '" + log_id +"'" ;

		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
			stmt = conn.createStatement();
			cnt = stmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, stmt);

		}
		
		return cnt;
	}
	
	public int delete() throws SQLException{
		int cnt = 0;
		
		Connection conn = null;
		Statement stmt = null;
		DBConnectionMgr pool = null;
		
		
		String sql = "delete CUSTOMER \n";
		sql += "where name = '" + log_name + "'"; // 숫자에 ''잇어도되고 없어도됨
		
		try {
			pool = DBConnectionMgr.getInstance();
			conn =  pool.getConnection();
			
			stmt = conn.createStatement(); // 현재상태를 생성한다
			cnt = stmt.executeUpdate(sql); // DB를 업데이트 해주는 함수, data가 추가되면서 추가되는
											// 만큼 integer값으로 리턴되서 넘어오기때문에 int로
											// 받아서 return해줌
		} catch (Exception e) {

		} finally {
			pool.freeConnection(conn, stmt);

		}
		
		return cnt;
	}
	
	// 1 MEMBER_NUMBER, 
	//2 MEMBER_ID, 
	//3 PASSWORD, 
	//4 NAME, 
	//5 AGE, 
	//6 PHONE_NUMBER, 
	//7 AUTH, 
	//8 SEAT, 
	//9 MINUTES
	public boolean phoneSearch(String phone_number) throws SQLException {
		MembershipDTO dto = null;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DBConnectionMgr pool = null;
		

		String sql = "select MEMBER_ID, NAME, PHONE_NUMBER, PASSWORD \n";
		sql += "from CUSTOMER \n";
		sql += "Where PHONE_NUMBER = '"+ phone_number+"'"  ;
		


		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
			stmt = conn.createStatement();
			// select를 제외한 나머지는 data를 넣는것, select는 data를 가져오는것
			rs = stmt.executeQuery(sql);
			// 하나만 찾을때
			if (rs.next()) {
				dto = new MembershipDTO();
				dto.setPhone_number(rs.getString("phone_number")); // int 는 getInt사용
			}

		} catch (Exception e) {

		} finally {
			pool.freeConnection(conn, stmt, rs);
			
			if(dto.getPhone_number().equals(phone_number)) return true;
			if(dto != null) return false;
			

		}
		return false;
	}
	
public boolean setSeat(int num, String id) {
		

		Connection conn = null;
		PreparedStatement psmt = null;
		
		DBConnectionMgr pool = null;
		int count = 0;

		String sql = "update CUSTOMER set SEAT = ? \n";
		sql += "Where MEMBER_ID = ?" ;
		


		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
			psmt = conn.prepareStatement(sql);
			
			
			psmt.setInt(1, num);
			psmt.setString(2, id);
			
			count = psmt.executeUpdate();
		} catch (Exception e) {

		} finally {
			pool.freeConnection(conn, psmt);
			
			if(count > 0) return true;
			if(count <= 0) return false;
			
		}
		return false;
	}
	
	
}
