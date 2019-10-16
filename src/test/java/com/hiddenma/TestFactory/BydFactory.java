package com.hiddenma.TestFactory;

public class BydFactory implements CarFactory {

	public Car createCar(String name) {
		return new BydCar();
	}

}
