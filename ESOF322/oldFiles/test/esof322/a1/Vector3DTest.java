package esof322.pa1;

import static org.junit.Assert.*;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.lang.Math;

public class Vector3DTest {
    protected double x, y, z;
    protected String sX, sY, sZ;
    protected Vector3D vector, testVector;
    protected double DELTA = 1e-10;
    @Before
    public void setUp() {
        x = 1.0;
        y = 2.0;
        z = 3.0;
        sX = Double.toString(x);
        sY = Double.toString(y);
        sZ = Double.toString(z);
        vector = new Vector3D(x, y, z);
    }
    
    /**
     * testToString JUnit test to ensure String representation of Vector3D
     * instance is correct
     */
    @Test
    public void testToString() {
        
        String sVector = vector.toString();
        // test that the String returned is not null
        assertNotNull(sVector);
        // test that toString method returns String representation of x, y and z coordinates
        String testV1 = "(" + sX + ", " + sY + ", " + sZ + ")";
        assertEquals(testV1, sVector);
        // test that toString method will not match different x, y and z coordinates
        testVector = new Vector3D(2.0, 3.0, 4.0);
        String testV2 = testVector.toString();
        assertThat(sVector, not(testV2));
    }
    /**
     * testEquals JUnit test to test different equality comparisons between Vector3D class
     * and test vectors
     */
    @Test
    public void testEquals() {
        double epsilon = 1E-6;
        double testDouble = 0.0;
        assert(vector.equals(vector));
        	// test inequality with different x, y, and z values
        testVector = new Vector3D(2.0, 3.0, 4.0);
        assertThat(vector, not(testVector));
        	// test equality with epsilon margin of error
        testVector = new Vector3D(1.0+(epsilon), 2.0+(epsilon), 3.0+(epsilon));
        assert(vector.equals(testVector));
        testVector = new Vector3D(1.0-(epsilon), 2.0-(epsilon), 3.0-(epsilon));
        assert(vector.equals(testVector));
        	// test inequality with greater than epsilon margin of error
        testVector = new Vector3D(1.0+(epsilon*10), 2.0+(epsilon*10), 3.0+(epsilon*10));
        assertThat(vector, not(testVector));
        	// test inequality with one different x, y, or z value
        testVector = new Vector3D(testDouble, 2.0, 3.0);
        assertThat(vector, not(testVector));
        testVector = new Vector3D(1.0, testDouble, 3.0);
        assertThat(vector, not(testVector));
        testVector = new Vector3D(1.0, 2.0, testDouble);
        assertThat(vector, not(testVector));
    }
    @Test
    public void testScale() {
        String actual = new Vector3D(1.0, 2.0, 4.0).scale(3.0).toString();
        String expected = (new Vector3D(3.0, 6.0, 12.0)).toString();
        assertEquals(actual, expected);
        	// Checks that the scale function gives correct scaling with a whole number constant factor
        actual = new Vector3D(2.0, 3.0, 4.0).scale(-2.0).toString();
        expected = new Vector3D(-4.0, -6.0, -8.0).toString();
        assertEquals(actual, expected);
    		// Checks that the scale function gives correct scaling with a negative whole number constant factor
        actual = new Vector3D(1.0, 1.5, 2.0).scale(2.0).toString();
        expected = new Vector3D(2.0, 3.0, 4.0).toString();
        assertEquals(actual, expected);
        	// Checks that integer scaling works with a decimal parameter in the vector
        actual = new Vector3D(1.0, 2.0, 3.0).scale(2.5).toString();
        expected = new Vector3D(2.5, 5.0, 7.5).toString();
        assertEquals(actual, expected);
        	// Check for decimal scalar functionality
        actual = new Vector3D(1.0, 1.5, 2.0).scale(0).toString();
        expected = new Vector3D(0.0, 0.0, 0.0).toString();
        assertEquals(actual, expected);
        	// Check for 0 scalar functionality
    }
    
    @Test
    public void testAdd() {
        
        String actual = ((new Vector3D(1.0, 2.0, 4.0)).add(new Vector3D(4.0, 5.0, 6.0))).toString();
        String expected = (new Vector3D(5.0,7.0,10.0)).toString();
        assertEquals(expected, actual);
        	// Checks that the add function works with whole number component vectors
        actual = ((new Vector3D(-1.0, 2.0, 4.0)).add(new Vector3D(4.0, 5.0, 6.0))).toString();
        expected = (new Vector3D(3.0,7.0,10.0)).toString();
        assertEquals(expected, actual);
        	// checks that the add function works with negative number components
        actual = ((new Vector3D(-1.2, 2.6, 4.8)).add(new Vector3D(4.0, 5.2, 6.4))).toString();
        expected = (new Vector3D(2.8,7.8,11.2)).toString();
        assertEquals(expected, actual);
        	// check that it works with decimal component vectors
        actual = ((new Vector3D(-1.5, 0.0, 90.0)).add(new Vector3D(0.0, 0.0, 0.0))).toString();
        expected = (new Vector3D(-1.5, 0.0, 90.0)).toString();
        assertEquals(expected, actual);
        	// check with adding a zero vector
    }
    
    @Test
    public void testSubtract() {
        	// test for whole number subtraction
        String actual = ((new Vector3D(1.0, 2.0, 4.0)).subtract(new Vector3D(4.0, 5.0, 6.0))).toString();
        String expected = (new Vector3D(-3.0,-3.0,-2.0)).toString();
        assertEquals(expected, actual);
        	// decimal subtraction
        actual = ((new Vector3D(2.3, 2.1, 4.9)).subtract(new Vector3D(1.1, 2.0, 3.8))).toString();
        expected = (new Vector3D(1.2,0.1,1.1)).toString();
        assertEquals(expected, actual);
        	// zero vector subtraction
        actual = ((new Vector3D(0.0, 0.0, 0.0)).subtract(new Vector3D(1.1, 2.0, 3.8))).toString();
        expected = (new Vector3D(-1.1,-2.0,-3.8)).toString();
        assertEquals(expected, actual);
        
        actual = ((new Vector3D(9.435,8.1111,9.101)).subtract(new Vector3D(0.0, 0.0, 0.0))).toString();
        expected = (new Vector3D(9.435,8.1111,9.101)).toString();
        assertEquals(expected, actual);
        	// High value subtraction
        actual = ((new Vector3D(12098127.12387, 123096.12938, 123456789.0)).subtract(new Vector3D(1.1, 2.0, 3.8))).toString();
        expected = (new Vector3D(12098126.02387, 123094.12938, 123456785.2)).toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNegate() {
        Vector3D v = new Vector3D(1.0, 2.0, 3.0);
        
        assertNotNull(v.negate());
        	// Test that the function does not return Null
        assertEquals((v.negate()).toString(), (new Vector3D(-1,-2,-3) ).toString() );
        	// Check that the string values of the negated vector and a new vector with the expected values are equal
        
        Vector3D actualVector = new Vector3D(-.9, .000001, 1232.930);
        assertEquals((actualVector.negate()).toString(), (new Vector3D(.9, -.000001, -1232.930) ).toString() );
        	// weird decimal cases

    }
    
    @Test
    public void testDot() {
        Vector3D testV = new Vector3D(3,2,1);
        assertNotNull((new Vector3D(1.0, 3.0, 4.0)).dot(testV));
        	// Test that the function does not return Null
        assertEquals((new Vector3D(1.0, 3.0, 4.0)).dot(testV), 13.0, DELTA);
        	// Test for the value, with a given vector of 3, 2, 1
        
        assertEquals((new Vector3D(0.1, .3, -.1)).dot(testV), .8, DELTA);
        	// test with decimal component vectors
        
       assertNotEquals((new Vector3D(12.45, 5.12345, 9.5)).dot(testV), 0.0, DELTA);
       		// test with incorrect guess
    }
    @Test
    public void testMagnitude() {
        
    	assertNotNull(vector.magnitude());
        // Test that the function does not return a null value
        
    	assertEquals(vector.magnitude(), Math.sqrt(x*x + y*y + z*z), DELTA);
        // Test that the magnitude function is returning a value equal to the expected magnitude
        
        Vector3D testV = new Vector3D(.1231241, 971123, 0.0);
        double testX = .1231241;
        double testY = 971123;
        double testZ = 0.0;
        assertEquals(testV.magnitude(), Math.sqrt(testX*testX + testY*testY + testZ*testZ), DELTA);
        	// test vector with different values
    }
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(Vector3DTest.class);
        
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if(result.wasSuccessful())
            System.out.println("Vector3D has passed JUnit testing.");
	   }
}
