package gelf.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gelf.model.project.Model;
import gelf.view.composites.MainWindow;

class EventManagerTest {

	@Test
	void test() {
		Model m = Model.getInstance();
		MainWindow w = new MainWindow("hi", 0, 0, m.getCurrentProject());
		new EventManager(w);
	}

}
