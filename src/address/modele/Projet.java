package address.modele;

import java.util.ArrayList;

public class Projet {
	
	private String name;
	private int width, height;
	private ArrayList<Item> itemList;
	private ArrayList<Projet> annuleList;
	
	public Projet(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.itemList = new ArrayList<Item>(0);
		this.annuleList = new ArrayList<Projet>(0);
	}
	
	public Projet(int index) {
		this.name  =  "Projet " + index;
		this.width = 6;
		this.height = 3;
		this.itemList = new ArrayList<Item>(0);
		this.annuleList = new ArrayList<Projet>(0);
	}
	
	public Projet(String name, int width, int height, ArrayList<Item> itemList, ArrayList<Projet> stateList) throws Exception {
		this.name = name;
		this.width = width;
		this.height = height;
		this.itemList = new ArrayList<Item>(0);
		for(int i = 0; i < itemList.size(); i++) {
			Item currIt = new Item();
			currIt = itemList.get(i).copy();
			this.itemList.add(currIt);
		}
		this.annuleList = new ArrayList<Projet>(0);
		for(int i = 0; i < stateList.size(); i++) {
			Projet currSt = new Projet(stateList.get(i).getName(), stateList.get(i).getWidth(), stateList.get(i).getHeight(),stateList.get(i).getItemList(), stateList.get(i).getAnnuleState());
			this.annuleList.add(currSt);
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
	
	public Projet getLastState() { return this.annuleList.get(0); }
	public ArrayList<Projet> getAnnuleState() { return this.annuleList; }
	
	public void addStateAnnule(Projet projet) {
		this.annuleList.add(0, projet);
		if (this.annuleList.size() == 6) {
			this.annuleList.remove(5);
		}
	}
	
	public void removeFirstState() {
		int size = this.annuleList.size();
		for (int i = size - 1; i > 0; i--) {
			int currSize = this.annuleList.size();
			this.annuleList.add(this.annuleList.get(currSize - 1));
			this.annuleList.remove(this.annuleList.get(currSize));
		}
		
		this.annuleList.remove(size - 1);
	}
	
	public String toString() {
		String res = "";
		res += "Nom : " + this.name + "\n";
		res += "	X = " + this.width + "\n";
		res += "	Y = " + this.height + "\n";
		res += this.showItems();
		res += "	Annule state nmbr : " + this.annuleList.size();
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
