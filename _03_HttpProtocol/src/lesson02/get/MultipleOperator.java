package lesson02.get;

public class MultipleOperator implements Operator {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "*";
	}

	@Override
	public double execute(double e, double b) throws Exception {
		// TODO Auto-generated method stub
		return e*b;
	}

}
