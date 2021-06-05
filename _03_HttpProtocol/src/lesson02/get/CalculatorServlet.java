package lesson02.get;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/CalculatorServlet")
public class CalculatorServlet extends HttpServlet {

	//부모 인터페이스 Operator로 한번에 관리하기 위한 Hashtable opTable
	// String : key / Operator : value
	private Hashtable<String, Operator> opTable =
				new Hashtable<String, Operator>();
	
	public CalculatorServlet() {
		opTable.put("+", new PlusOperator());
		opTable.put("-", new MinusOperator());
		opTable.put("*", new MultipleOperator());
		opTable.put("/", new DivOperator());
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("get 요청");
		//공통된 처리를 맡기는 process method
		process(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post 요청");
		process(req,resp);
	}
	
	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 브라우저가 보낸 변수 값들을 추출
		String sV1 = req.getParameter("v1");
		String sV2 = req.getParameter("v2");
		String op = req.getParameter("op");
		
		// -> 받은 String 변수값 형변환
		double v1 = Double.parseDouble(sV1);
		double v2 = Double.parseDouble(sV2);
				
		// 2. 우리가 전송하는 데이터를 브라우저가 utf-8로 해석하라는 요청 설정
		resp.setContentType("text/html; charset=UTF-8");
		// -> print하기 전에 먼저 설정해야 한다
		
		// 3. 우리가 만든 CalculatorServlet servlet 객체(tomcat의 container에 의해 로딩되는 객체)
		// 	즉,  우리의 서블릿 객체(CalculatorServlet) -> tomcat -> apache -> 브라우저
		//			-> calculator.html   : 소켓 연결 스트림을 포함하는 통신 객체 추출
		
		PrintWriter out = resp.getWriter();
		
		// 4. 브라우저에 제목 전송
		out.println("<html><body>");
		out.println("<h1>계산결과</h1>");
		out.println("결과 :  ");
		
		// 5. op값에 따른 담당객체를 objTable로 부터 얻는다
		// ->모든 자식 operator를 받기 위해 부모인 operator 변수로 받음
		Operator operator = opTable.get(op);
		
		// 6. 계산
		try {
			if(operator == null) {
				out.println("계산할 수 없는 연산자 입니다.");
			}else {
				// execute 값에 따라 해당되는 객체(+,-,*,/)에 호출됨 (다형성)
				double result = operator.execute(v1, v2);
				out.println(String.format("%.3f", result));
			}
		} catch (Exception e) {
			out.println("연산 오류 발생!");
		}
		
		// 7. 브라우저에  마무리 태그 전송
		out.println("</body></html>");
		
	}
}
