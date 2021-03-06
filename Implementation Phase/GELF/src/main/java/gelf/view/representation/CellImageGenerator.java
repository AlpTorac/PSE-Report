package gelf.view.representation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Creates a representative image of the chosen cell.
 * @author Ege Uzhan
 */
public class CellImageGenerator implements ICellImageGenerator {
	
	BufferedImage cellImage;
	BufferedImage pinImage;
	
	/**
	 * Default constructor.
	 */
	public CellImageGenerator() {
		
		try {
			cellImage = ImageIO.read(new File("src/main/resources/CellImage.png"));
			pinImage = ImageIO.read(new File("src/main/resources/PinImage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * Creates a representative image of the chosen cell.
	 * @param inputPins Number of input pins.
	 * @param outputPins Number of output pins.
	 */
	public BufferedImage buildCell(int inputPins, int outputPins) {
		if (inputPins < 6 && outputPins < 4) {
			BufferedImage newImage = new BufferedImage(cellImage.getWidth() + 2 * pinImage.getWidth(),
				cellImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
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
		else {
			int maxPins = (inputPins >= outputPins) ? inputPins  : outputPins ;
			BufferedImage newImage = new BufferedImage(pinImage.getWidth() * 3,
					 maxPins * cellImage.getHeight() / 6 ,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = newImage.createGraphics();
			for (int i = 1; i <= inputPins; i++) {
				if (inputPins <= 5) {
					g2.drawImage(pinImage, 0, (i) * cellImage.getHeight() / (inputPins + 1), null);
				}
				else if (inputPins < maxPins) {
					if (i % 2 == 0) {
						g2.drawImage(pinImage, 0, 5 + ((maxPins/2 - inputPins/2) + i - 1)
							* cellImage.getHeight() / 6 , null);
					}
					else {
						g2.drawImage(pinImage, 0, 5 + ((maxPins/2 - inputPins/2) + i - 1)
								* cellImage.getHeight() / 6 , null);
					}
				}
				else {
					g2.drawImage(pinImage, 0, 5 + (i - 1) * cellImage.getHeight() / (6) , null);
				}
			}
			for (int i = 1; i <= outputPins; i++) {
				if (outputPins <= 3) {
					g2.drawImage(pinImage, pinImage.getWidth() * 2,
							(i) * cellImage.getHeight() / (outputPins + 1), null);
				}
				else if (outputPins < maxPins) {
					if (i % 2 == 0) {
						g2.drawImage(pinImage, pinImage.getWidth() * 2, 5 + (i-1)
							* cellImage.getHeight() / (outputPins) , null);
					}
					else {
						g2.drawImage(pinImage, pinImage.getWidth() * 2, 5 + (i-1)
								* cellImage.getHeight() / (outputPins) , null);
					}
				}
				else {
					g2.drawImage(pinImage, pinImage.getWidth() * 2,  5 + (i - 1)
						* cellImage.getHeight() / (6), null);
				}
			}
				return newImage;
		}
	}

}
