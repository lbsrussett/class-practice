package gamePackage;

public class Dice {
	private int value1 = 0;
	private int value2 = 0;
	
	// Method to return the first rolled dice
	private int roll1() {
		value1 = (int)(Math.random()*6 + 1);
		return value1;
	}
	
	// Method to return the second rolled dice
	private int roll2() {
		value2 = (int)(Math.random()*6 + 1);
		return value2;
	}
	
	// Method to return the value of two rolled dice
	public int rollPair() {
		return roll1() + roll2();
	}
	
	public int getDie1() {
		return value1;
	}
	
	public int getDie2() {
		return value2;
	}	
	
	public int getTotalRoll() {
		return value1 + value2;
	}
	
	// Method to determine if a die roll resulted in doubles
	public boolean isDouble() {
		if (value1 == value2) return true;
		return false;
	}
	
}
