package MonopolyV1.gamePackage;

public class Utility extends Tile{

	private String name;
	private int[] rent;
	private boolean isProperty;
	private int price;
	private boolean morgaged = false;
	private boolean isOwned = false;
	private Player owner;
	private Property p;

	public Utility(String name, int price, int[] rent) {
		this.name = name;
		this.price = price;
		this.rent = rent;
	}
	
	public int getRent() {
		int numUtilities = owner.getNumUtilities();
		if (numUtilities == 1) return owner.getRoll()*4;
		else if (numUtilities == 2) return owner.getRoll()*10;
		else return -1;
	
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

	public void morgage() {
		morgaged = true;
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
	public int getPrice() {
		return price;
	}

	public void printUtility() {
		System.out.println(name);
	}
	
}