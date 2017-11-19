package gamePackage;

public class Property extends Tile {
	
	private String name;
	boolean morgaged = false;
	private int[] rent;	// rent array, to return current rent based on number of houses
	private int price;
	private boolean isOwned = false;
	private Player owner;
	private int numHouses = 0;
	private int housePrice;
	private static int totalHouses = 32;
	private static int totalHotels = 12;	
	
	public Property (String name, int price, int[] rent, int housePrice) {
		this.name = name;
		this.rent = rent;
		this.price = price;
		this.housePrice = housePrice;
		super.isProperty = true;
	}
	
	public void printProperty() {
		System.out.println(name);
	}

	public int getMorgageValue() {
		return price/2;
	}
	
	public int getHousesValue() {
		return housePrice;
	}
	
	public boolean isMorgaged() {
		return morgaged;
	}

	public String getName() {
		return name;
	}

	public void morgage() {
		morgaged = true;
	}

	public int getPrice() {
		return price;
	}
	
	public int getRent() {
		return rent[numHouses];
	}
	
	public void setOwner(Player name) {
		owner = name;
		isOwned = true;
	}
	
	public boolean isOwned() {
		return isOwned;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public int getHouses() {
		if (numHouses < 5) return numHouses;
		else return 0;
	}
	
	public void addHouse() {
		numHouses++;
		totalHouses--;
	}
	
	public int getHotel() {
		if (numHouses == 5) return 1;
		else return 0;
	}
	
	public void addHotel() {
		numHouses++;
		totalHouses += 4;
		totalHotels--;
	}
}
