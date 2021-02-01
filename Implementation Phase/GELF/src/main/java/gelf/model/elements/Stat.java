package gelf.model.elements;

public class Stat {
	private float min;
	private float max;
	private float avg;
	private float med;
	
	public Stat(float min, float max, float avg, float med) {
		this.setMin(min);
		this.setMax(max);
		this.setAvg(avg);
		this.setMed(med);
	}

	public float getMin() {
		return min;
	}
	
	public void setMin(float min) {
		this.min = min;
	}
	
	public float getMax() {
		return max;
	}
	
	public void setMax(float max) {
		this.max = max;
	}
	
	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}

	public float getMed() {
		return med;
	}

	public void setMed(float med) {
		this.med = med;
	}
	
}
