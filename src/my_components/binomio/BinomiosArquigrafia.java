package my_components.binomio;

public class BinomiosArquigrafia {

	String left, right;
	int leftValue,rightValue;
	
	
	public BinomiosArquigrafia(String l, String r){
		left = l;
		right = r;
		leftValue=50;
		rightValue=50;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public int getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(int leftValue) {
		this.leftValue = leftValue;
	}

	public int getRightValue() {
		return rightValue;
	}

	public void setRightValue(int rightValue) {
		this.rightValue = rightValue;
	}
	
	
}
