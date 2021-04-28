package address.modele;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Item {
	//ATTRIBUTS
	private String name;
	private double pos_x, pos_y;
	private double height, width;
	private String url;
	Image image;
	private Double rotation;
	private boolean rotated = false;
	
	//CONSTRUCTEUR
	public Item (String name, double pos_x, double pos_y, double height, double width, String url) throws MalformedURLException {
		this.name = name;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.pos_x = pos_x;
		
		this.url = url;
		
		File file = new File(url);
		String localURL = file.toURI().toURL().toString();
		this.image = new Image(localURL);
		
		this.height = this.image.getHeight();
		this.width = this.image.getWidth();
		
		this.rotation = 0.0;
		
	}
	
	public Item () throws MalformedURLException {
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
	public String getUrl() { return this.url; }
	public double getRotation() { return this.rotation; }
	public boolean getRotated() { return this.rotated; }
	
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
	
	public void drawTest (GraphicsContext gc) {
		gc.save();
        rotate(gc, rotation, pos_x + width / 2, pos_y + height / 2);
        gc.drawImage(image, pos_x, pos_y, width, height);
        gc.restore();
	}
	
	private void rotate(GraphicsContext gc, double angle, double px,
            double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(),
                r.getTx(), r.getTy());
    }
	
	public void drawRect (GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(this.pos_x, this.pos_y, this.width, this.height);
	}
	
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
	
	public Item copy() throws Exception {
		Item res = new Item();
		res.setX(this.getX());
		res.setY(this.getY());
		res.setWidth(this.getWidth());
		res.setHeight(this.getHeight());
		res.setUrl(this.getUrl());
		
		return res;
	}
	
	public void rotateRight(Canvas canvas) {
		double diff = (width - height)/2 ;
		if (rotated) {
			if (pos_x >= 0 && pos_x + width <= canvas.getWidth()) {
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
	
	public void rotateLeft(Canvas canvas) {
		double diff = (width - height)/2 ;
		if (rotated) {
			if (pos_x >= 0 && pos_x + width <= canvas.getWidth()) {
				this.rotation -= 90;
				rotated = false;
			}
		} else {
			if (pos_y - diff >= 0 && pos_y + height + diff <= canvas.getHeight()) {
				this.rotation -= 90;
				rotated = true;
			}
		}
	}
}
