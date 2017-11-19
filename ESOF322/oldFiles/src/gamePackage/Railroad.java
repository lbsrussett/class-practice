package gamePackage;

public class Railroad extends Tile{

	private String name;
	private int[] rent;
	private boolean isProperty;
	private int price;
	private boolean morgaged = false;
	private boolean isOwned = false;
	private Player owner;
	private Property p;

	public Railroad(String name, int price, int[] rent) {
		this.name = name;
		this.price = price;
		this.rent = rent;
	}
	
	public int getRent(int numRailroads) {
		return rent[numRailroads];
	}

	public int getMorgageValue() {
		return price/2;
	}

	public boolean isMorgaged() {
		return morgaged;
	}

	public String getName() {
		return name;
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
	public void morgage() {
		morgaged = true;
	}

	public int getPrice() {
		return price;
	}

	public void printRailroad() {
		System.out.println(name);
	}
}
