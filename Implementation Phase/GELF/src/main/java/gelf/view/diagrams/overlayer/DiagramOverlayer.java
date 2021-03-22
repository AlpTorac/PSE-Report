package gelf.view.diagrams.overlayer;

import gelf.view.diagrams.IDiagram;
import gelf.view.diagrams.IDiagramOverlayer;

/**
 * The context class for the strategy pattern (see {@link DiagramOverlayStrategy}) for overlaying
 * {@link gelf.view.diagrams.type.Diagram Diagrams}.
 * @author Alp Torac Genc
 */
public class DiagramOverlayer implements IDiagramOverlayer {
	private DiagramOverlayStrategy overlayStrategy;

	public void setOverlayStrategy(DiagramOverlayStrategy overlayStrategy) {
		this.overlayStrategy = overlayStrategy;
	}

	@Override
	public IDiagram overlay() {
		return this.overlayStrategy.overlay();
	}
}
