package address.modele;

import java.util.ArrayList;

public class Projet {
	
	private String name;
	private int width, height;
	private ArrayList<Item> itemList;
	
	public Projet(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.itemList = new ArrayList<Item>(0);
	}
	
	public Projet(int index) {
		this.name  =  "Projet " + index;
		this.width = 6;
		this.height = 3;
		this.itemList = new ArrayList<Item>(0);
	}
	
	public Projet(String name, int width, int height, ArrayList<Item> itemList) throws Exception {
		this.name = name;
		this.width = width;
		this.height = height;
		this.itemList = new ArrayList<Item>(0);
		for(int i = 0; i < itemList.size(); i++) {
			Item currIt = new Item();
			currIt = itemList.get(i).copy();
			this.itemList.add(currIt);
		}
	}
	
	public String getName() { return this.name; }
	public int getWidth() { return this.width; }
	public int getHeight() {  return this.height; }
	public ArrayList<Item> getItemList() { return this.itemList; }
	public void setName(String name) { this.name = name; }
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	public void addItem(Item item) { this.itemList.add(item); }
	public void removeItem(Item item) { this.itemList.remove(item); }
	public void removeItemIndex(int index) { this.itemList.remove(index); }
	
	
	public String toString() {
		String res = "";
		res += "Nom : " + this.name + "\n";
		res += "	X = " + this.width + "\n";
		res += "	Y = " + this.height + "\n";
		res += this.showItems();
		return res;
	}
	
	public String showItems() {
		String res = "";
		res += "	Item position : \n";
		for (int i = 0; i < this.itemList.size(); i++) {
			res += "		[" + i + "] = x = " + this.getItemList().get(i).getX() + "; y = " + this.getItemList().get(i).getY() + "\n";
		}
		return res;
	}
	
	
}
