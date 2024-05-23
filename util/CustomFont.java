package util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class CustomFont {
	private static Font customFont;
	
	private static void createCustomFont() {
		try {
		    //create the font to use. Specify the size!
		    customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/font/fsex300-webfont.ttf"));
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(customFont);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
	}
	
	public static Font getCustomFont() {
		createCustomFont();
		return customFont;
	}

}
