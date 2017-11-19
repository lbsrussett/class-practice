package esof322.pa4.team1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.*;
import org.junit.Test;
import org.junit.runner.JUnitCore;

//import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
//import com.sun.net.httpserver.Authenticator.Failure;

public class MonopolyTest {
	protected String actual, expected;
	protected int actualInt, expectedInt;
	protected boolean actualBool, expectedBool;
	protected Dice testDice;
	protected Player testPlayer;
	protected Tile testTile;
	
	@Test
	public void testPlayers() {
		// Not exactly sure which way we should test player initialization

	}

	@Test
	public void testPlayerName() throws FileNotFoundException, IOException {
		testDice = new Dice();
		testTile = new Tile();
		Tile[] tileArray = {testTile};
		testPlayer = new Player("testPlayer", testDice, tileArray);
		actual = testPlayer.getName();
		expected = "testPlayer";

		assertEquals(actual, expected);
	}

	@Test
	public void testDiceRoll() {
		Dice testDice = new Dice();
		actualInt = testDice.getTotalRoll();
		int val1 = testDice.getDie1();
		int val2 = testDice.getDie2();
		expectedInt = val1 + val2;
		
		assertEquals(actualInt, expectedInt);
	}

	@Test
	public void testStartingMoney() throws FileNotFoundException, IOException {
		testDice = new Dice();
		testTile = new Tile();
		Tile[] tileArray = {testTile};
		testPlayer = new Player("testPlayer", testDice, tileArray);
		actualInt = 1500;
		expectedInt = testPlayer.getMoney();
		
		assertEquals(actualInt, expectedInt);
	}

	@Test
	public void testRollDoubles() {
		testDice = new Dice();
		do {
			testDice.rollPair();
		}while(!testDice.isDouble());
		
		boolean expectedBool = true;
		boolean actualBool = testDice.isDouble();
		
		assertEquals(expectedBool, actualBool);
	}

	@Test
	public void testPassGo() {

	}

	@Test
	public void testSpacesMoved() {

	}

	@Test
	public void testPropertyOwned() {

	}

	@Test
	public void testBuyProperty() {

	}

	@Test
	public void testPayRent() {

	}

	@Test
	public void testPropertyMortgaged() {

	}

	@Test
	public void testMortgageValue() {

	}

	@Test
	public void testBuyHouse() {

	}

	@Test
	public void testHasMonopoly() throws FileNotFoundException, IOException {
		testDice = new Dice();
		testTile = new Tile();
		Tile[] tileArray = {testTile};
		testPlayer = new Player("testPlayer", testDice, tileArray);
		expectedBool = true;
		actualBool = testPlayer.checkMonopoly();
		
		assertEquals(expectedBool, actualBool);
	}

	@Test
	public void testGoToJail() {

	}

	@Test
	public void testGetOutOfJail() {

	}

	@Test 
	public void testCollectRent() {

	}

	@Test
	public void testPayTax() {

	}

	@Test
	public void testPayRentWithHouse() {

	}

	@Test
	public void testAuctionProperty() {

	}

	@Test
	public void testWinner() {

	}

	@Test 
	public void testEndTurn() {

	}

	@Test
	public void testPlayerWorth() {

	}

	@Test
	public void testSellAssets() {

	}

	@Test
	public void testPrintProperties() {

	}

	@Test
	public void testGameTimer() {

	}

	@Test
	public void testPropertyOwner() {

	}
	
	/*public static void main(String[] args) {
        org.junit.runner.Result result = JUnitCore.runClasses(MonopolyTest.class);
        
        for (org.junit.runner.notification.Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if(result.wasSuccessful())
            System.out.println("MonopolyTest has passed JUnit testing.");
	   }*/
}