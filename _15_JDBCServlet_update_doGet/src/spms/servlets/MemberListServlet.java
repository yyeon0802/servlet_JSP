package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		Connection conn = null;		// MySQL 서버 연결
		Statement stmt = null;		// sql문 실행
		ResultSet rs = null;		// SELECT문 실행결과
		
		//MySQL 접속 정보
		//String mySqlUrl = "jdbc:mysql://localhost:3306/studydb?serverTimezone=UTC";
		String mySqlUrl = "jdbc:mysql://localhost/studydb?serverTimezone=UTC";
		String id = "study";
		String pwd = "study";
		
		// \r\n -> 행을 바꿈 +로 sql문 연결할때 꼭 사용하라 or 띄어쓰기가 필요
		String sqlSelect = "SELECT mno,mname,email,cre_date" + "\r\n" +
							"FROM members" + "\r\n" + 
							"ORDER BY mno";
	
		try {
			// 1) MySQL 제어 드라이버 객체를 로딩
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			// 2) MySQL 서버와 연결
			conn = DriverManager.getConnection(mySqlUrl, id, pwd);
			// 3) sql문을 실행하기 위한 객체 생성
			stmt = conn.createStatement();
			// 4) sql문 실행(서버 전송 후 결과값 받아오기)
			rs = stmt.executeQuery(sqlSelect);
			// 5) 결과값을 브라우저에 전송
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			
			//신규회원을 추가할 링크 a 태그 : get 요청 -> addServlet은 doGet으로 받는다
			/* href = '/add' => 기본주소/add
			 * href = 'add' => 현재 계층주소의 마지막만 add로 변경
			 * 		즉 , 기본주소/member/list 가 아니라
			 * 				=> 기본주소/member/add
			 * */
			
			out.println("<p><a href='add'>신규 회원</a></p>");
			
			
			/*rs.next()의 역할
			 * 1) 결과값이 true일때 다음 데이터가 존재한다.
			 * 2) 내부적으로 최초에 cursor는 첫번째 데이터 이전을 가리키고 있다.
			 *		rs.next()를 호출할 때 마다 다음 행으로 커서를 옮긴다.
			 * */
			
			
			while(rs.next()) { //한 행의 1번째 , 2번째, 3번째 ... 데이터
				/*
				out.println(rs.getInt(1) + ", " + 		//mno의 타입이 숫자기에 int로
							rs.getString(2) + ", " +	
							rs.getString(3) + ", " +
							rs.getDate(4) + "<br/>");
				*/
				
				/* 위와 같은 방법 */
				 out.println(rs.getInt("mno") + ", " + 
				"<a href='update?no=" + rs.getInt("mno")+"'>" +
							rs.getString("mname") + "</a>, " +
							rs.getString("email") + ", " +
							rs.getDate("cre_date") + "<br/>");	
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			try {
				if(stmt!=null) stmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			try {
				if(conn!=null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
