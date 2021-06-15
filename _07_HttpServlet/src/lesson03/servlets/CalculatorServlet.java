package lesson03.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/calc")
public class CalculatorServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int a = Integer.parseInt(req.getParameter("a"));
		int b = Integer.parseInt(req.getParameter("b"));
		
		resp.setContentType("text/plain"); // 태그XX 평문을 전송함
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		out.println("a="+a+", "+"b="+b+"의 계산결과");
		out.println("a+b="+(a+b));
		out.println("a-b="+(a-b));
		out.println("a*b="+(a*b));
		out.println("a/b="+(a/b));
		out.println("a%b="+(a%b));
	}
	
	

}
