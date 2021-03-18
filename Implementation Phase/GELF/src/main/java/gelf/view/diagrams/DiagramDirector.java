package gelf.view.diagrams;

import gelf.view.diagrams.builder.DiagramBuilder;

/**
 * The singleton director class to the {@link DiagramBuilder} instances.
 * <p>
 * This class is responsible for initiating the building of 
 * {@link IDiagram} by using an appropriate builder.
 * 
 * @author Alp Torac Genc
 *
 */
public class DiagramDirector {
	/**
	 * The builder to use.
	 */
	private DiagramBuilder builder;
	
	/**
	 * The only instance of the class.
	 */
	private static DiagramDirector instance;
	
	private DiagramDirector() {
		
	}

	public static DiagramDirector getDiagramDirector() {
		if (instance == null) {
			instance = new DiagramDirector();
		}
		return instance;
	}
	
	public void setBuilder(DiagramBuilder builder) {
		this.builder = builder;
	}
	
	/**
	 * @return The diagram defined within {@link #builder}.
	 */
	public IDiagram build() {
		return this.builder.buildDiagram();
	}
}
