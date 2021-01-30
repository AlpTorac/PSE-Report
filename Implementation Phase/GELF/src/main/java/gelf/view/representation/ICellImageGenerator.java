package gelf.view.representation;

import java.awt.image.BufferedImage;

/*
 * Interface for the CellImageGenerator class.
 */
public interface ICellImageGenerator {
	
	/*
	 *  Builds the representative image of a cell with the given number of input and output pins.
	 */
	 public BufferedImage buildCell(int inputPins, int outputPins);

}
