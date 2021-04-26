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
	
	
}
