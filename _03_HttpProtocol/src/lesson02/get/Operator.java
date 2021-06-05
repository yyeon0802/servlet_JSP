package lesson02.get;

public interface Operator {
	// 상속을 받을 메소드를 정의해놓는 interface
	// 껍데기 뿐이라 객체를 만들수  X
	public String getName();
	public double execute(double e, double b) throws Exception;
}
