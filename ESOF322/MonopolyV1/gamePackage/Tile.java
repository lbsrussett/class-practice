package MonopolyV1.gamePackage;

public class Tile {
	
	private String name;
	private int[] rent;
	boolean morgaged = false;
	public boolean isProperty = false; //**currently public
	private boolean isRailroad = false;
	private boolean isUtility = false;
	private int price;
	private int housePrice;
	private Property p;
	private Railroad r;
	private Utility u;
	private int propGroup;
	
	public Tile() {
	}
	
	public Tile(String name, int price, int[] rent, int housePrice, int propType, int propGroup) {
		this.name = name;
		this.price = price;
		this.rent = rent;
		this.housePrice = housePrice;
		this.propGroup = propGroup;
		
		if (propType == 1) {
			p = new Property(this.name, price, rent, housePrice);
			isProperty = true;
		}
		
		else if (propType == 2) {
			r = new Railroad(this.name, price, rent);
			isRailroad = true;
		}
		
		else if (propType == 3) {
			u = new Utility(this.name, price, rent);
			isUtility = true;
		}	
				
	}
	
	public void morgage() {
		morgaged = true;
	}
	
	public boolean isMorgaged() {
		return morgaged;
	}
	
	public int getPropGroup() {
		return propGroup;
	}
	
	public void printName() {
		System.out.println(name);
	}
	
	public int getMorgageValue() {
		return price/2;
	}
	
	public int getHousesValue() {
		return housePrice/2;
	}
	
	public String getName() {
		return name;
	}
	
	public int getRent() {
		return rent[0];
	}
	
	public boolean isProperty() {
		return isProperty;
	}
	
	public boolean isUtility() {
		return isUtility;
	}
	
	public boolean isRailroad() {
		return isRailroad;
	}

	public Property getProperty() {
		return p;
	}
	public Utility getUtility() {
		return u;
	}
	public Railroad getRailroad() {
		return r;
	}
	
	
}