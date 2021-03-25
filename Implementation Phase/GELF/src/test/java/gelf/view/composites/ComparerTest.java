package gelf.view.composites;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gelf.controller.EventManager;
import gelf.model.elements.Element;
import gelf.model.elements.InputPin;
import gelf.model.elements.Library;
import gelf.model.elements.attributes.TimingGroup;
import gelf.model.elements.attributes.TimingSense;
import gelf.model.elements.attributes.TimingType;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.parsers.LibertyParser;
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.view.composites.Comparer.Attribute;

class ComparerTest {

	private static MainWindow window;
	private static Comparer comparer;
	private static SubWindow subWindow;
	private static Library library;
	private static Project proj;
	
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
		
		proj = new Project();
		subWindow = new SubWindow(lib, proj, window.outliner, window.subWindowArea, 1000, 1000);
		subWindow.setVisible(false);
		
		window.subWindowArea.addSubWindow(subWindow);
	}
	
	private static void setUp(Element e1, Element e2) {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(e1);
		elements.add(e2);
		comparer = new Comparer(elements, proj, subWindow, 1000, 1000);
	}
	
	@Test
	void libraryAttributeUpdateDiagramTest() {
		setUp(library, library);
		Attribute[] attrArr = new Attribute[] {Attribute.INPUT_POWER, Attribute.OUTPUT_POWER, Attribute.DEFAULT_LEAKAGE, Attribute.LEAKAGE};
		
		for (Attribute attr : attrArr) {
			comparer.attribute = attr;
			comparer.setSelectedPins(null);
		}
		
		TimingSense[] tSs = TimingSense.values();
		TimingGroup[] tGs = TimingGroup.values();
		TimingType[] tTs = TimingType.values();
		
		comparer.attribute = Attribute.TIMING;
		
		for (TimingSense tS : tSs) {
			comparer.timingSense = tS;
			for (TimingGroup tG : tGs) {
				comparer.timingGroup = tG;
				for (TimingType tT : tTs) {
					comparer.timingType = tT;
					comparer.setSelectedPins(null);
				}
			}
		}
	}
	
	@Test
	void cellAttributeUpdateDiagramTest() {
		setUp(library.getCells().get(0), library.getCells().get(0));
		Attribute[] attrArr = new Attribute[] {Attribute.INPUT_POWER, Attribute.OUTPUT_POWER, Attribute.LEAKAGE};
		
		for (Attribute attr : attrArr) {
			comparer.attribute = attr;
			comparer.setSelectedPins(null);
		}
		
		TimingSense[] tSs = TimingSense.values();
		TimingGroup[] tGs = TimingGroup.values();
		TimingType[] tTs = TimingType.values();
		
		comparer.attribute = Attribute.TIMING;
		
		for (TimingSense tS : tSs) {
			comparer.timingSense = tS;
			for (TimingGroup tG : tGs) {
				comparer.timingGroup = tG;
				for (TimingType tT : tTs) {
					comparer.timingType = tT;
					comparer.setSelectedPins(null);
				}
			}
		}
	}

	@Test
	void outputPinAttributeUpdateDiagramTest() {
		setUp(library.getCells().get(0).getOutPins().get(0), library.getCells().get(0).getOutPins().get(0));
		InputPin[] inPins = library.getCells().get(0).getInPins().toArray(InputPin[]::new);
		Attribute[] attrArr = new Attribute[] {Attribute.INPUT_POWER, Attribute.OUTPUT_POWER, Attribute.DEFAULT_LEAKAGE, Attribute.LEAKAGE};
		
		for (Attribute attr : attrArr) {
			comparer.attribute = attr;
			for (InputPin ip : inPins) {
				for (InputPin ip2 : inPins) {
					try {
						ArrayList<InputPin> list = new ArrayList<InputPin>();
						list.add(ip);
						list.add(ip2);
						comparer.setSelectedPins(list);
					} catch (Exception e) {}
				}
			}
		}
		
		TimingSense[] tSs = TimingSense.values();
		TimingGroup[] tGs = TimingGroup.values();
		TimingType[] tTs = TimingType.values();
		
		comparer.attribute = Attribute.TIMING;
		
		for (TimingSense tS : tSs) {
			comparer.timingSense = tS;
			for (TimingGroup tG : tGs) {
				comparer.timingGroup = tG;
				for (TimingType tT : tTs) {
					comparer.timingType = tT;
					for (InputPin ip : inPins) {
						for (InputPin ip2 : inPins) {
							try {
								ArrayList<InputPin> list = new ArrayList<InputPin>();
								list.add(ip);
								list.add(ip2);
								comparer.setSelectedPins(list);
							} catch (Exception e) {}
						}
					}
				}
			}
		}
	}
	
	@Test
	void inputPinAttributeUpdateDiagramTest() {
		setUp(library.getCells().get(0).getInPins().get(0), library.getCells().get(0).getInPins().get(0));
		Attribute[] attrArr = new Attribute[] {Attribute.INPUT_POWER, Attribute.OUTPUT_POWER, Attribute.DEFAULT_LEAKAGE, Attribute.LEAKAGE};
		
		for (Attribute attr : attrArr) {
			comparer.attribute = attr;
			comparer.setSelectedPins(null);
		}
		
		TimingSense[] tSs = TimingSense.values();
		TimingGroup[] tGs = TimingGroup.values();
		TimingType[] tTs = TimingType.values();
		
		comparer.attribute = Attribute.TIMING;
		
		for (TimingSense tS : tSs) {
			comparer.timingSense = tS;
			for (TimingGroup tG : tGs) {
				comparer.timingGroup = tG;
				for (TimingType tT : tTs) {
					comparer.timingType = tT;
					comparer.setSelectedPins(null);
				}
			}
		}
	}
}
