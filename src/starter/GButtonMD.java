package starter;


/*
 * File: GButton.java
 * ------------------
 * Provides a simple way to create a button with text.
 * The button will automatically set the size of the text
 * Based on the width and height of the button.
 * Users can also set the Color of the button's background
 * as well as the color of the text.
 */

import java.awt.Color;
import java.awt.Font;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRoundRect;

public class GButtonMD extends GCompound {
	private GImage rect;
	private GLabel message;
	private String filePath = "../media/buttonMD/box";
	private String color;
	private String fileEnd = ".png";

	public static final int BUFFER = 20;
	
	public GButtonMD(String string, int x, int y, int width, int height) {
		this(string, x, y, width, height, "blue");
	}
	
	public GButtonMD(String label, int x, int y, int width, int height, String color) {
		super();
		setLocation(x, y);
		if (width == height) {
			color = color + "SQR";
		}
		rect = new GImage(filePath + color + fileEnd, 0, 0);
		rect.setSize(width, height);
		add(rect);
		message = new GLabel(label);
		message.setColor(Color.WHITE);
		sizeLabelFont(message, width - BUFFER, height - BUFFER);
		double centerX = width / 2 - message.getWidth() / 2;
		double centerY = height / 2 + message.getAscent() / 4;
		add(message, centerX, centerY);
	}


	private void sizeLabelFont(GLabel label, double width, double height) {
		int size, style;
		String name;
		Font f = label.getFont();
		name = f.getFontName();
		style = f.getStyle();
		size = f.getSize();
		while (label.getWidth() < width && label.getHeight() < height) {
			f = label.getFont();
			size = f.getSize();
			label.setFont(new Font(name, style, size + 1));
		}
		label.setFont(new Font(name, style, size - 1));
	}

	public void setColor(Color col) {
		message.setColor(col);
	}
}
