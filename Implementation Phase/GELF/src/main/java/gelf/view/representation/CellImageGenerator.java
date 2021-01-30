package gelf.view.representation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 *  Creates a representative image of the chosen cell.
 */
public class CellImageGenerator implements ICellImageGenerator {
	
	BufferedImage cellImage;
	BufferedImage pinImage;
	
	/*
	 * Default constructor.
	 */
	public CellImageGenerator() {
		
		try {
			cellImage = ImageIO.read(new File("src/main/java/gelf/view/representation/CellImage.png"));
			pinImage = ImageIO.read(new File("src/main/java/gelf/view/representation/PinImage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	/*
	 * Creates a representative image of the chosen cell.
	 * @param inputPins Number of input pins.
	 * @param outputPins Number of output pins.
	 */
	@Override
	public BufferedImage buildCell(int inputPins, int outputPins) {
		
		BufferedImage newImage = new BufferedImage(cellImage.getWidth() + 2 * pinImage.getWidth()
			, cellImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		g2.drawImage(cellImage, pinImage.getWidth(), 0, null);
		
		for (int i = 0; i < inputPins; i++) {
			g2.drawImage(pinImage, 0, (i + 1) * cellImage.getHeight() / (inputPins + 1), null);
		}
		
		for (int i = 0; i < outputPins; i++) {
			g2.drawImage(pinImage, pinImage.getWidth() + cellImage.getWidth(),
					(i + 1) * cellImage.getHeight() / (outputPins + 1), null);
		}
		
		return newImage;
	}

}
