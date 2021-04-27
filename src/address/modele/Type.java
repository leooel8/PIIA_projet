package address.modele;


import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class Type {

	private final StringProperty typeName;
	private final ArrayList<Item> items;

	/**
	 * Default constructor.
	 */
	public Type() {
		this(null, null);
	}
	
	/**
	 * Constructor with some initial data.
	 * 
	 * @param typeName
	 * @param lesItems
	 */
	public Type(String typeName, ArrayList<Item> lesItems) {
		this.typeName = new SimpleStringProperty(typeName);
		this.items=lesItems;
	}
	
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