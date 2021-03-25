package gelf.model.commands;


import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenFileCommandTest {

	@Test
	void readFileTest() {
		OpenFileCommand ofc = new OpenFileCommand();
		try {
			String text = ofc.readFile("src/test/resources/baseline.lib");
			Assertions.assertEquals('i', text.charAt(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
