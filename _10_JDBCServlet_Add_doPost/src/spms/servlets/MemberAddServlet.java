package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>회원등록</title></head>");
		out.println("<body><h1>회원등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br/>");
		out.println("이메일: <input type='text' name='email'><br/>");
		out.println("암호: <input type='password' name='password'><br/>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'><br/>");
		out.println("</form>");
		out.println("</body></html>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// submit 추가 시 post방식으로 들어오기 때문 -> doPost
		
		// DB에 새로운 사용자를 등록
		/* tomcat은 문자열을 기본적으로 유니코드로 인식 <->브라우저 utf-8
		 * 영문/숫자는 정상적으로 보이지만, 한글은 codeset이 달라서 깨질 수 있다.
		 * setCharacterEncoding을 초기에 실행하여 브라우저의 codeset과
		 * 일치시켜 해석하므로 한글이 잘 보이게 된다.*/
		
		// 1. request로 부터 수신되는 데이터를 utf-8로 해석한다(한글의 경우)
		req.setCharacterEncoding("UTF-8");
		
		// DB에 새로운 사용자를 등록
		// 2. DB관련 정보 준비
		Connection conn = null;
		/*<공통점> : sql문을 실행할 때 사용한다.
		 *  1) Statement
		 *		-. 질의 할때 마다 sql문을 컴파일한다.
		 *		-. 바이너리 데이터 전송이 불가능하다.
		 *		-. sql문 내에 입력값을 문자열로 포함되므로 코드관리가 어렵다.
		 * 	2) PreparedStatement
		 * 		-. sql문을 미리 준비하여 컴파일 해 둔다.
		 * 			-> 입력 매개변수값만 추가하여 서버에 전송한다.
		 * 			-> 특히 여러번 반복하여 질의하는 경우, 실행속도가 빠르다.
		 * 		-. 바이너리 데이터 전송이 가능
		 * 		-. sql문과 입력 매개변수가 분리되어 있어서 코드 작성이 편리하다.
		 * */
		PreparedStatement stmt = null;
		String url ="jdbc:mysql://localhost:3306/studydb?serverTimezone=UTC";
		String sqlInsert = "INSERT INTO members(email,pwd," + "\r\n" +
		"mname,cre_date,mod_date)" + "\r\n" + "VALUES(?,?,?,NOW(),NOW())";
		String id = "study";
		String pwd = "study";
		
		try {
			// 1) mySql jdbc driver(=mySql을 jdbc로 제어하기 위한 드라이버) 객체를 로딩한다.
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			// 2) mySql 서버에 접속
			conn = DriverManager.getConnection(url, id, pwd);
			// 3) sql을 실행할 preparedStatement 객체 생성
			stmt = conn.prepareStatement(sqlInsert);
			// 4) ?와 치환할 값을 넣어준다.
			stmt.setString(1, req.getParameter("email"));
			stmt.setString(2, req.getParameter("password"));
			stmt.setString(3, req.getParameter("name"));
			
			// row : 적용된 (실행된) 행의 갯수
			int row = stmt.executeUpdate();
									
			// 5) 브라우저에 결과를 전송한다.
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>회원등록결과</title></head>");
			out.println("<body>");
			if(row >= 1) {
				out.println("<p>등록성공입니다.</p>");
			}else {
				out.println("<p>등록하지 못했습니다.</p>");
			}
			out.println("</body></html>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			//resultset은 없다. select문이 아니기 때문.
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
