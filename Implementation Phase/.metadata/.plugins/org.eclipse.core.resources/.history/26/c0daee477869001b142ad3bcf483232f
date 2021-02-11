package gelf.view.diagrams.data;

import java.util.Collection;

public class DiagramData {
	private Collection<?> data;
	private DiagramDataFormatter ddf;
	
	public DiagramData(Collection<?> data) {
		this.data = data;
	}
	
	public Collection<?> getData() {
		return this.data;
	}
	
	public void setData(Collection<?> data) {
		this.data = data;
	}
	
	public Object getFormattedData() {
		return ddf.format(data);
	}
	
	public void setFormat(DiagramDataFormatter ddf) {
		this.ddf = ddf;
	}
}
