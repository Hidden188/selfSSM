package com.hiddenma.TestFactory;

public class TestMain {

	public static void main(String[] args) {
		Car b = new BydFactory().createCar("比亚迪223");
		b.run();
	}

}
