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
import javafx.scene.paint.Color;

public class Item {
	//ATTRIBUTS
	private String name;
	private double pos_x, pos_y;
	private double height, width;
	private String url;
	Image image;
	
	//CONSTRUCTEUR
	public Item (String name, double pos_x, double pos_y, double height, double width, String url) throws MalformedURLException {
		this.name = name;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		
		this.url = url;
		
		File file = new File(url);
		String localURL = file.toURI().toURL().toString();
		this.image = new Image(localURL);
		
		this.height = this.image.getHeight();
		this.width = this.image.getWidth();
		
	}
	
	public Item () {
		
	}
	
	public Item(int index) throws MalformedURLException {
		this.name = "item " + index;
		this.pos_x = 0;
		this.pos_y  = 0;
		this.height = 20;
		this.width= 20;
		
		File file = new File("Images/default");
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
	public String getUrl() {return this.url; }
	
	public void setX(double x) { this.pos_x = x; }
	public void setY(double y) { this.pos_y = y; }
	public void setWidth(double width) { this.width = width; }
	public void setHeight(double height) { this.height = height; }
	public void setUrl(String url) throws Exception {
		this.url = url;
		File file = new File(url);
		String localURL = file.toURI().toURL().toString();
		this.image = new Image(localURL);
	}
	
	
	public void draw (GraphicsContext gc) {
		gc.drawImage(this.image, this.pos_x, this.pos_y);
	}
	
	public void drawRect (GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(this.pos_x, this.pos_y, this.width, this.height);
	}
	
	public boolean isIn(double x, double y) {
		if (x >= this.getX() && x <= this.getX() + this.image.getWidth()) {
			if (y >= this.getY() && y <= this.getY() + this.image.getHeight()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Item copy() throws Exception {
		Item res = new Item();
		res.setX(this.getX());
		res.setY(this.getY());
		res.setWidth(this.getWidth());
		res.setHeight(this.getHeight());
		res.setUrl(this.getUrl());
		
		return res;
		
	}
	
}
