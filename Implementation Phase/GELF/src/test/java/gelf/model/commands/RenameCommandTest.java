package gelf.model.commands;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gelf.model.elements.InputPin;

class RenameCommandTest {

	@Test
	void executeTest() {
		InputPin inPin = new InputPin("old name", null, null);
		RenameCommand rename = new RenameCommand(inPin, "new name");
		rename.execute();
		Assertions.assertEquals("new name", inPin.getName());
	}
	
	@Test
	void undoTest() {
		InputPin inPin = new InputPin("old name", null, null);
		RenameCommand rename = new RenameCommand(inPin, "new name");
		rename.execute();
		rename.undo();
		Assertions.assertEquals("old name", inPin.getName());
	}

}
