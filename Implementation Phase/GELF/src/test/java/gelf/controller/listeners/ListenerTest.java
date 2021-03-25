package gelf.controller.listeners;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import gelf.controller.EventManager;
import gelf.model.elements.Element;
import gelf.model.elements.Library;
import gelf.model.exceptions.InvalidFileFormatException;
import gelf.model.parsers.LibertyParser;
import gelf.model.project.Model;
import gelf.model.project.Project;
import gelf.view.composites.Comparer;
import gelf.view.composites.MainWindow;
import gelf.view.composites.Outliner;
import gelf.view.composites.SubWindow;
import gelf.view.composites.SubWindowArea;
import gelf.view.composites.Visualizer;

public abstract class ListenerTest {
    public static Outliner outliner;
    public static SubWindowArea subWindowArea;
    public static Project proj;
    public static MainWindow window;
    public static Visualizer visualizer;
    public static SubWindow subWindow;
    public static Library library;
    public static Comparer comparer;
    
    public static EventManager em;
    
	public static void init() {
		proj = Model.getInstance().getCurrentProject();
		window = new MainWindow("GELF - Graphical Editor for Liberty Files", 1200, 800,
				proj);
		outliner = window.outliner;
        em = new EventManager(window);
        outliner.setTreeMouseListener(em);
        
        window.setVisible(false);
        
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
		subWindowArea = window.subWindowArea;
		
		ArrayList<Library> libraries = new ArrayList<Library>();
		libraries.add(library);
		proj.setLibraries(libraries);
		proj.inform();
	}
	
	public static void setUp(Element e1, Element e2) {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(e1);
		elements.add(e2);
		comparer = new Comparer(elements, proj, subWindow, 1000, 1000);
	}
}
