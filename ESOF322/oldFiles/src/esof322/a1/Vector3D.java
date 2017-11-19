package esof322.pa1;

public final class Vector3D {
    private double x, y, z;
    private String sX, sY, sZ;
    
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    	// method that converts this vector to a string representation of its lengths
    public String toString() {
        sX = Double.toString(x);
        sY = Double.toString(y);
        sZ = Double.toString(z);
        String sVector = "(" + sX + ", " + sY + ", " + sZ + ")";
        return sVector;
    }
    
    	// method for finding if two vectors are equal down to a constant, in this case 1e-5 or .00001
    public boolean equals(Vector3D compare) {
        double epsilon = 1E-5;
        boolean xEps = Math.abs(x-compare.x) <= epsilon;
        boolean yEps = Math.abs(y-compare.y) <= epsilon;
        boolean zEps = Math.abs(z-compare.z) <= epsilon;
        if(xEps && yEps && zEps) {
            return true;
        }
        else {
            return false;
        }
        
    }
    
    	// method for scaling a vector by a factor, or multiplying its lengths by a given value that returns a new vector
    public Vector3D scale(double f) {
        return new Vector3D(this.x*f, this.y*f, this.z*f);
    }
    
    	// method for finding the vector created by adding two vectors, one given and one the class one
    public Vector3D add(Vector3D v) {
        return new Vector3D( round(x + v.x) , round(y + v.y), round(z + v.z));
    }
    
    	// method for finding the difference between two vectors, which returns a new vector of the given dimensions
    public Vector3D subtract(Vector3D v) {
        return new Vector3D( round(x - v.x) , round(y - v.y), round(z - v.z));
    }
    
    	//Method for negating a vector, that returns the new vector
    public Vector3D negate() {
        return new Vector3D(-1*x,-1*y,-1*z);
    }
    
    	// method for finding the dot product of two vectors, one given and the class one
    public double dot(Vector3D v) {
        return (x*v.x + y*v.y + z*v.z);
    }
    
    	// method for finding the length or magnitude of the vector
    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    	// Method for rounding doubles to within a certain range, in this case within a factor of 1e10 or 0.00000000001
    private double round(double val) {
    	return Math.round(val * 1e10)/1e10;
    }
}

