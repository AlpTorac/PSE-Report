package gelf.view.diagrams.type;

import java.util.stream.Stream;

import gelf.view.diagrams.components.DiagramAxis;
import gelf.view.diagrams.components.DiagramComponent;
import gelf.view.diagrams.components.DiagramValueDisplayComponent;
import gelf.view.diagrams.data.DiagramData;

/**
 * The class that represents a heat map.
 * <p>
 * A heat map's {@link Diagram#data data} has two arrays of indices and
 * {@code index1.length} arrays of values on its own.
 * @author Alp Torac Genc
 */
public class HeatMap extends Diagram {
	public HeatMap(DiagramData data, DiagramAxis[] axes,
			DiagramValueDisplayComponent[] valueDisplayComponents,
			DiagramComponent[] nonValueDisplayComponents) {
		super(data, axes, valueDisplayComponents, nonValueDisplayComponents);
	}
	
	@Override
	public int[] getIndexPositionsOfComponent(DiagramValueDisplayComponent dvdc) {
		DiagramData data = this.getDiagramData();
		
		// Has the length of 2, since a heat map always has 2 indices.
		Integer[] indexLengths = ((Stream<float[]>) data.extractIndices().stream()).map(n -> n.length).toArray(Integer[]::new);
		DiagramValueDisplayComponent[] dvdcs = this.getValueDisplayComponents();
		
		for (int i = 0, index = 0; i < indexLengths[0]; i++) {
			for (int j = 0; j < indexLengths[1]; j++, index++) {
				if (dvdc == dvdcs[index]) {
					return new int[] {i, j};
				}
			}
		}
		return new int[] {-1, -1};
	}
}
