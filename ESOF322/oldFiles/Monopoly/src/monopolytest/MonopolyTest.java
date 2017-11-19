package monopolytest

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.net.httpserver.Authenticator.Failure;

public class MonopolyTest {
	protected String actual, expected;
	protected int actualInt, expectedInt;
	protected Dice testDice;
	protected Player testPlayer;
	
	/*@Before
	public void setUp() {
		Dice testDice = new Dice();
		Player[] testPlayers = new Player[4];
		for(int i = 0; i < testPlayers.length; i++) {
			String playerName = "testPlayer" + i;
			testPlayers[i] = new Player(playerName, testDice);
		}
	}*/

	@Test
	public void testPlayers() {
		// Not exactly sure which way we should test player initialization

	}

	@Test
	public void testPlayerName() {
		testDice = new Dice();
		testPlayer = new Player("testPlayer", testDice);
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
	public void testStartingMoney() {

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
	public void testHasMonopoly() {

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

	public static void main(String[] args) {
        org.junit.runner.Result result = JUnitCore.runClasses(MonopolyTest.class);
        
        for (org.junit.runner.notification.Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if(result.wasSuccessful())
            System.out.println("MonopolyTest has passed JUnit testing.");
	   }
}

