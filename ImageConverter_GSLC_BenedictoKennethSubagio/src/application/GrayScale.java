package application;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GrayScale {
	
	public static WritableImage convertGrayScale(Image image) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		WritableImage grayscaleImage = new WritableImage(width, height);
		PixelReader pixelReader = image.getPixelReader();
		
		for(int y = 0; y<height;y++) {
			for(int x = 0; x<width;x++) {
				Color color = pixelReader.getColor(x,y);
				double gray = ((color.getRed() + color.getGreen() + color.getBlue())/3.0);
				grayscaleImage.getPixelWriter().setColor(x,y, new Color(gray, gray, gray, color.getOpacity()));
			}
		}
		return grayscaleImage;
		
	}

}
