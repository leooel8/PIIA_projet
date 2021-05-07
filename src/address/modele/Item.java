package address.modele;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Item {
	//ATTRIBUTS
	private String name;
	private double pos_x, pos_y;
	private double height, width;
	private String url;
	Image image;
	private Double rotation = 0.0;
	private boolean rotated = false;
	
	//CONSTRUCTEUR
	public Item (String name, double pos_x, double pos_y, double height, double width, String url, double rotation) throws MalformedURLException {
		this.name = name;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.pos_x = pos_x;
		this.rotation = rotation;
		if (rotation != 0.0) {
			this.rotated = true;
		}
		this.url = url;
		
		File file = new File(url);
		String localURL = file.toURI().toURL().toString();
		this.image = new Image(localURL);
		
		this.height = this.image.getHeight();
		this.width = this.image.getWidth();
		
		
	}
	
	public Item () throws MalformedURLException {
	}
	
	//METHODES
	//Getters et setters
	public String getName() { return this.name; }
	public double getX() { return this.pos_x; }
	public double getY() { return this.pos_y; }
	public double getWidth() { return this.width; }
	public double getHeight() { return this.height; }
	public Image getImage() { return this.image; } 
	public String getUrl() { return this.url; }
	public double getRotation() { return this.rotation; }
	public boolean getRotated() { return this.rotated; }
	
	public void setX(double x) { this.pos_x = x; }
	public void setY(double y) { this.pos_y = y; }
	public void setWidth(double width) { this.width = width; }
	public void setHeight(double height) { this.height = height; }
	public void setRotation(double rotation) { 
		this.rotation = rotation;
		if (rotation != 0.0) {
			this.rotated = true;
		}
	}
	public void setUrl(String url) throws Exception {
		this.url = url;
		File file = new File(url);
		String localURL = file.toURI().toURL().toString();
		this.image = new Image(localURL);
	}

	public Item copy() throws Exception {
		Item res = new Item();
		res.setX(this.getX());
		res.setY(this.getY());
		res.setWidth(this.getWidth());
		res.setHeight(this.getHeight());
		res.setUrl(this.getUrl());
		res.setRotation(this.getRotation());
		
		return res;
	}
	
	/*
	 * Fonction d'affichage de l'item
	 * */
	public void draw (GraphicsContext gc) {
			gc.drawImage(this.image, this.pos_x, this.pos_y);
		
	}
	
	/*
	 * Fonction d'affichage utilisée si l'item est tourné
	 * */
	public void drawTest (GraphicsContext gc) {
		gc.save();
        rotate(gc, rotation, pos_x + width / 2, pos_y + height / 2);
        gc.drawImage(image, pos_x, pos_y, width, height);
        gc.restore();
	}
	
	/*
	 * Fonction qui tourne le GraphicsContext
	 * */
	private void rotate(GraphicsContext gc, double angle, double px,
            double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(),
                r.getTx(), r.getTy());
    }
	
	/*
	 * Vérifie si la position donnée en paramètre est contenue par l'item
	 * */
	public boolean isIn(double x, double y) {
		if (rotated) {
			double diff = (width - height)/2 ;
			if (x >= this.getX() + diff && x <= this.getX() + diff + this.height) {
				if (y >= this.getY() - diff && y<= this.getY() - diff + this.width) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
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
		
	}
	
	/*
	 * Fonction de rotation
	 * */
	public void rotateRight(Canvas canvas) {
		double diff = (width - height)/2 ;
		if (rotated) {
			if (pos_x >= 0 && pos_x + width <= canvas.getWidth()) { //Vérifie que la rotation ne fera pas dépasser l'item de la zone de cuisine
				this.rotation += 90;
				rotated = false;
			}
		} else {
			if (pos_y - diff >= 0 && pos_y + height + diff <= canvas.getHeight()) {
				this.rotation += 90;
				rotated = true;
			}
		}
		
	}
}
