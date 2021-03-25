package gelf.view.composites;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.controller.EventManager;
import gelf.model.elements.Cell;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.OutputPin;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.parsers.LibertyParser;
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.view.composites.Visualizer.Attribute;

class VisualizerTest {

	private static MainWindow window;
	private static Visualizer visualizer;
	private static SubWindow subWindow;
	private static Library library;
	
	@BeforeAll
	private static void init() {
        MainWindow mainWindow = new MainWindow("GELF - Graphical Editor for Liberty Files", 1200, 800,
                Model.getInstance().getCurrentProject());
        EventManager em = new EventManager(mainWindow);
        mainWindow.outliner.setTreeMouseListener(em);
        
        mainWindow.setVisible(false);
        window = mainWindow;
        
		File file = new File("src/test/resources/baseline.lib");
		String libFileContent = null;
		try {
			libFileContent = Files.readString(file.toPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LibertyParser.setUp();
		Library lib = null;
		try {
			lib = LibertyParser.parseLibrary(libFileContent);
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		library = lib;
		
		Project proj = new Project();
		SubWindow sWindow = new SubWindow(lib, proj, window.outliner, window.subWindowArea, 1000, 1000);
		sWindow.setVisible(false);
		subWindow = sWindow;
		window.subWindowArea.addSubWindow(sWindow);
		
		Visualizer v = null;
		
		for (Component c : sWindow.getComponents()) {
			if (c instanceof Visualizer) {
				v = (Visualizer) c;
				break;
			}
		}
		
		visualizer = v;
	}
	
	@Test
	void libraryAttributeUpdateDiagramTest() {
		subWindow.setElement(library);
		Attribute[] attrArr = new Attribute[] {Attribute.INPUT_POWER, Attribute.OUTPUT_POWER, Attribute.DEFAULT_LEAKAGE, Attribute.LEAKAGE};
		
		for (Attribute attr : attrArr) {
			visualizer.attribute = attr;
			visualizer.updateDiagram();
		}
		
		TimingSense[] tSs = TimingSense.values();
		TimingGroup[] tGs = TimingGroup.values();
		TimingType[] tTs = TimingType.values();
		
		visualizer.attribute = Attribute.TIMING;
		
		for (TimingSense tS : tSs) {
			visualizer.timingSense = tS;
			for (TimingGroup tG : tGs) {
				visualizer.timingGroup = tG;
				for (TimingType tT : tTs) {
					visualizer.timingType = tT;
					visualizer.updateDiagram();
				}
			}
		}
	}
	
	@Test
	void cellAttributeUpdateDiagramTest() {
		subWindow.setElement(library.getCells().get(0));
		Attribute[] attrArr = new Attribute[] {Attribute.INPUT_POWER, Attribute.OUTPUT_POWER, Attribute.LEAKAGE};
		
		for (Attribute attr : attrArr) {
			visualizer.attribute = attr;
			visualizer.updateDiagram();
		}
		
		TimingSense[] tSs = TimingSense.values();
		TimingGroup[] tGs = TimingGroup.values();
		TimingType[] tTs = TimingType.values();
		
		visualizer.attribute = Attribute.TIMING;
		
		for (TimingSense tS : tSs) {
			visualizer.timingSense = tS;
			for (TimingGroup tG : tGs) {
				visualizer.timingGroup = tG;
				for (TimingType tT : tTs) {
					visualizer.timingType = tT;
					visualizer.updateDiagram();
				}
			}
		}
	}

	@Test
	void outputPinAttributeUpdateDiagramTest() {
		subWindow.setElement(library.getCells().get(0).getOutPins().get(0));
		InputPin[] inPins = library.getCells().get(0).getInPins().toArray(InputPin[]::new);
		Attribute[] attrArr = new Attribute[] {Attribute.INPUT_POWER, Attribute.OUTPUT_POWER, Attribute.DEFAULT_LEAKAGE, Attribute.LEAKAGE};
		
		for (Attribute attr : attrArr) {
			visualizer.attribute = attr;
			for (InputPin ip : inPins) {
				visualizer.updateDiagram(ip);
			}
		}
		
		TimingSense[] tSs = TimingSense.values();
		TimingGroup[] tGs = TimingGroup.values();
		TimingType[] tTs = TimingType.values();
		
		visualizer.attribute = Attribute.TIMING;
		
		for (TimingSense tS : tSs) {
			visualizer.timingSense = tS;
			for (TimingGroup tG : tGs) {
				visualizer.timingGroup = tG;
				for (TimingType tT : tTs) {
					visualizer.timingType = tT;
					for (InputPin ip : inPins) {
						visualizer.updateDiagram(ip);
					}
				}
			}
		}
	}
	
	@Test
	void inputPinAttributeUpdateDiagramTest() {
		subWindow.setElement(library.getCells().get(0).getInPins().get(0));
		Attribute[] attrArr = new Attribute[] {Attribute.INPUT_POWER, Attribute.OUTPUT_POWER, Attribute.DEFAULT_LEAKAGE, Attribute.LEAKAGE};
		
		for (Attribute attr : attrArr) {
			visualizer.attribute = attr;
			visualizer.updateDiagram();
		}
		
		TimingSense[] tSs = TimingSense.values();
		TimingGroup[] tGs = TimingGroup.values();
		TimingType[] tTs = TimingType.values();
		
		visualizer.attribute = Attribute.TIMING;
		
		for (TimingSense tS : tSs) {
			visualizer.timingSense = tS;
			for (TimingGroup tG : tGs) {
				visualizer.timingGroup = tG;
				for (TimingType tT : tTs) {
					visualizer.timingType = tT;
					visualizer.updateDiagram();
				}
			}
		}
	}
	
	@Test
	void updateTest() {
		if (library != null) {
			subWindow.setElement(library);
			if (library.getCells() != null) {
				for (Cell c : library.getCells()) {
					subWindow.setElement(c);
					if (c.getInPins() != null) {
						for (InputPin ip : c.getInPins()) {
							subWindow.setElement(ip);
						}
					}
					if (c.getOutPins() != null) {
						for (OutputPin op : c.getOutPins()) {
							try {
								subWindow.setElement(op);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}
