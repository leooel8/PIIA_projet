package address.modele;


import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Type {
	//ATTRIBUTS
	private final StringProperty typeName;
	private final ArrayList<Item> items;

	//CONSTRUCTEURS
	public Type() {
		this(null, null);
	}
	
	public Type(String typeName, ArrayList<Item> lesItems) {
		this.typeName = new SimpleStringProperty(typeName);
		this.items=lesItems;
	}
	
	//Getters et Setters
	public String getTypeName() {
		return typeName.get();
	}

	public void setTypeName(String typeName) {
		this.typeName.set(typeName);
	}
	
	public StringProperty typeNameProperty() {
		return typeName;
	}

	public ArrayList<Item> getItemsList() {return this.items; }
}