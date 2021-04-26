package address.modele;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class Item {
	//ATTRIBUTS
	private String name;
	private double pos_x, pos_y;
	private double height, width;
	Image image;
	
	//CONSTRUCTEUR
	public Item (String name, double pos_x, double pos_y, double height, double width, String url) throws MalformedURLException {
		this.name = name;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.height = height;
		this.width = width;
		
		File file = new File(url);
		String localURL = file.toURI().toURL().toString();
		this.image = new Image(localURL);
		
	}
	
	public Item(int index) throws MalformedURLException {
		this.name = "item " + index;
		this.pos_x = 0;
		this.pos_y  = 0;
		this.height = 20;
		this.width= 20;
		
		File file = new File("images/default");
		String localURL = file.toURI().toURL().toString();
		this.image = new Image(localURL);
	}
	
	//METHODES
	
	public String getName() { return this.name; }
	public double getX() { return this.pos_x; }
	public double getY() { return this.pos_y; }
	public double getWidth() { return this.width; }
	public double getHeight() { return this.height; }
	public Image getImage() { return this.image; } 
	
	public void draw (GraphicsContext gc) {
		gc.drawImage(this.image, this.pos_x, this.pos_y);
	}
	
}
