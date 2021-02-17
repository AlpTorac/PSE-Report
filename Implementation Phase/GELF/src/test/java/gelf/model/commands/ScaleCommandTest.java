package gelf.model.commands;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.attributes.InputPower;
import gelf.model.elements.attributes.PowerGroup;

class ScaleCommandTest {
	
	float[] values = {0.000302f, 0.0003592f, 0.0003661f, 0.0003644f, 
			  0.000366f, 0.0003663f, 0.0003662f};
	InputPower inPow = new InputPower(PowerGroup.FALL_POWER, values);
	
	@Test
	void executeTest() {
		ScaleCommand scale = new ScaleCommand(inPow, 5f);
		scale.execute();
		float[] expected = {0.000302f * 5f, 0.0003592f * 5f, 0.0003661f * 5f, 0.0003644f * 5f, 
				  0.000366f * 5f, 0.0003663f * 5f, 0.0003662f * 5f};
		
		Assertions.assertArrayEquals(expected, inPow.getValues());
	}
	
	@Test
	void undoTest() {
		ScaleCommand scale = new ScaleCommand(inPow, 5f);
		scale.execute();
		scale.undo();
		Assertions.assertArrayEquals(values, inPow.getValues());
	}

}
