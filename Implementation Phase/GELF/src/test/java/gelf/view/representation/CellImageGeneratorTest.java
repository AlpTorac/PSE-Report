package gelf.view.representation;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

class CellImageGeneratorTest {

	private JFrame frame = new JFrame();
	private CellImageGenerator gen = new CellImageGenerator();
	private JLabel label = new JLabel();
	
	@Test
	void test1() {
		frame.add(label);
		label.setIcon(new ImageIcon(gen.buildCell(2, 3)));
		frame.setVisible(true);
	}
	
	@Test
	void test2() {
		frame.add(label);
		label.setIcon(new ImageIcon(gen.buildCell(5, 3)));
		frame.setVisible(true);
	}
	
	@Test
	void test3() {
		frame.add(label);
		label.setIcon(new ImageIcon(gen.buildCell(6, 3)));
		frame.setVisible(true);
	}
	
	@Test
	void test4() {
		frame.add(label);
		label.setIcon(new ImageIcon(gen.buildCell(2, 4)));
		frame.setVisible(true);
	}

}
