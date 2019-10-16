package com.hiddenma.TestFactory;

public class JiliFactory implements CarFactory {

	public Car createCar(String name) {
		return new JiliCar();
	}

}
