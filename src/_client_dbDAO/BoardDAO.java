package _client_dbDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import _client_dbFile.DBConnectionMgr;
import all_DTO.boardDTO;

public class BoardDAO {

	public boardDTO selBoard = null;

	public BoardDAO() {
	}

	// 게시글 가져오기
	public List<boardDTO> getList() {
		List<boardDTO> list = new ArrayList<>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DBConnectionMgr pool = null;

		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
		} catch (Exception eeq1) {
			eeq1.printStackTrace();
		}
		String sql = "select title_number, member_id, title, Board_content, wdate, MEMBER_PASSWORD  \n";
		sql += "from board \n";
		sql += "order by title_number desc \n";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// 모든 data 가져올때
			while (rs.next()) {
				boardDTO dto = new boardDTO();
				dto.setSeq(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setWdate(rs.getString(5));
				dto.setPword(rs.getString(6));
				list.add(dto);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			pool.freeConnection(conn, stmt, rs);
		}
		return list;
	}

	public List<boardDTO> getListsel(String str, String select) {
		List<boardDTO> selList = new ArrayList<>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DBConnectionMgr pool = null;

		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
		} catch (Exception eeq1) {
			eeq1.printStackTrace();
		}
		String sql = " select title_number, member_id, title, Board_content, wdate, MEMBER_PASSWORD  \n";
		sql += "from board \n";
		sql += "where " + select + " like '%" + str + "%' ";
		sql += "order by title_number desc \n";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// 모든 data 가져올때
			while (rs.next()) {
				boardDTO dto = new boardDTO();
				dto.setSeq(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setWdate(rs.getString(5));
				dto.setPword(rs.getString(6));
				selList.add(dto);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			pool.freeConnection(conn, stmt, rs);
		}
		return selList;
	}

	// 게시글 추가
	public boolean addBoard(boardDTO dto) {
		int seq = 1;
		List<boardDTO> list = getList();

		for (int i = 0; i < list.size(); i++) {
			if (seq < list.get(i).getSeq()) {
				seq = list.get(i).getSeq();
			}
		}
		seq++;

		String sql = " insert into board " + " (title_number, member_id, title, Board_content, wdate, member_password) "
				+ " values(?,?,?,?,SYSDATE,?) ";
		int count = 0;
		Connection conn = null;
		PreparedStatement psmt = null; // 순서대로 딱딱딱딱딱
		DBConnectionMgr pool = null;

		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();

			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, seq);
			psmt.setString(2, dto.getId());
			psmt.setString(3, dto.getTitle());
			psmt.setString(4, dto.getContent());
			// psmt.setString(5, dto.getWdate());
			psmt.setString(5, dto.getPword());

			count = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, psmt);
			System.out.println("6/6 addBoard"); // add작업은5/6 작업이 필요하지않아서 여기선
			// 사용되지않음
		}

		return count > 0 ? true : false;
	}

	/// 게시글 조회수 수정
	public List<boardDTO> read() throws SQLException {
		List<boardDTO> list = getList();

		int cnt = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DBConnectionMgr pool = null;

		String sql = "select  title_number, member_id, title, Board_content, wdate, member_password  \n";
		sql += "from board ";
		sql += "where title_number = '" + selBoard.getSeq() + "'";

		// sql = "update board set title = '" + title + "', Board_content = '" +
		// content + "' \n";
		// sql += "where title_number = '" + seq + "'";

		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			cnt = stmt.executeUpdate(sql);

			while (rs.next()) {
				boardDTO dto = new boardDTO();
				dto.setSeq(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setWdate(rs.getString(5));
				dto.setPword(rs.getString(6));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, stmt, rs);
		}

		return list;
	}

	// 게시글 삭제
	public int delete() throws SQLException {

		List<boardDTO> list = getList();
		int seq = selBoard.getSeq();
		int cnt = 0;

		Connection conn = null;
		Statement stmt = null;
		DBConnectionMgr pool = null;

		String sql = "delete board \n";
		sql += "where title_number = '" + seq + "'"; // 숫자에 ''잇어도되고 없어도됨

		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
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

	public boardDTO searchpass(int seq) {
		boardDTO dto = null;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DBConnectionMgr pool = null;

		String sql = "select TITLE_NUMBER, NAMEMEMBER_PASSWORD \n";
		sql += "from BOARD \n";
		sql += "Where TITLE_NUMBER = '" + seq + "'";

		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();

			stmt = conn.createStatement();
			// select를 제외한 나머지는 data를 넣는것, select는 data를 가져오는것
			rs = stmt.executeQuery(sql);
			// 하나만 찾을때
			if (rs.next()) {
				dto = new boardDTO();
				dto.setSeq(rs.getInt("TITLE_NUMBER"));
				dto.setPword(rs.getString("NAMEMEMBER_PASSWORD"));
			}

		} catch (Exception e) {

		} finally {
			pool.freeConnection(conn, stmt, rs);
		}
		return dto;
	}

	public int boardUpdate(boardDTO dto) throws SQLException {

		List<boardDTO> list = getList();
		int seq = selBoard.getSeq();

		int cnt = 0;
		Connection conn = null;
		Statement stmt = null;
		DBConnectionMgr pool = null;

		String sql = "update board set Board_content = '" + dto.getContent() + "', title = '" + dto.getTitle() + "' \n";
		sql += "where title_number = " + seq;

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

}