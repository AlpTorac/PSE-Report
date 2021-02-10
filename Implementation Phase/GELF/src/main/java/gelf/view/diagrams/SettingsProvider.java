package gelf.view.diagrams;

import java.awt.Color;

public class SettingsProvider {
	private int axisThickness = 1;
	private Color axisColor = Color.BLACK;
	private int axisValueFontSize = 10;
	private boolean showAxisValues = true;
	private int additionalSpaceForAxisValues = 5;
	/**
	 * The horizontal space left between a vertical axis and the edges for displaying values.
	 */
	private int xSpaceForAxisValues = axisValueFontSize + additionalSpaceForAxisValues;
	/**
	 * The vertical space left between a horizontal axis and the edges for displaying values.
	 */
	private int ySpaceForAxisValues = axisValueFontSize + additionalSpaceForAxisValues;
	private int stepsInXAxis = 10;
	private int stepsInYAxis = 10;
	
	/**
	 * Controls how transparent the bars will be.
	 * Needs a value between 0 - 255.
	 * The higher the value, the more transparent.
	 */
	private int barAlpha = 100;
	private Color barColor = new Color(255, 0, 0, barAlpha);
	private Color barComparisonColor = new Color(0, 255, 0, barAlpha);
	private int barBorderThickness = 1;
	
	private int functionGraphPointSize = 5;
	private Color functionGraphPointColor = Color.RED;
	
	/**
	 * Controls how transparent the bars will be.
	 * Needs a value between 0 - 255.
	 * The higher the value, the more transparent.
	 */
	private int heatMapLabelAlpha = 200;
	private int heatMapLabelBorderThickness = 1;
	private int heatMapColorScaleBorderThickness = 1;
	private Color heatMapColorScaleBorderColor = Color.BLACK;
	private Color[] heatMapColorScaleColors = new Color[] {new Color(255, 0, 0, heatMapLabelAlpha),
			new Color(0, 0, 255, heatMapLabelAlpha)};
	/**
	 * The vertical space left for the color scale of the heat map.
	 */
	private int heatMapColorScaleVerticalSpace = 200;
	private int heatMapSpaceBetweenDiagramAndColorScale = 100;
	/**
	 * Increase the maximum index by this amount to leave some more space for the final bar.
	 */
	private int histogramIndexEndIndexSpace = 10;
	
	private static SettingsProvider instance;
	
	public static SettingsProvider getInstance() {
		if (instance == null) {
			instance = new SettingsProvider();
		}
		return instance;
	}

	public int getAxisThickness() {
		return axisThickness;
	}
	

	public Color getAxisColor() {
		return axisColor;
	}

	public int getAxisValueFontSize() {
		return axisValueFontSize;
	}

	public boolean isShowAxisValues() {
		return showAxisValues;
	}

	public int getAdditionalSpaceForAxisValues() {
		return additionalSpaceForAxisValues;
	}

	public int getxSpaceForAxisValues() {
		return xSpaceForAxisValues;
	}

	public int getySpaceForAxisValues() {
		return ySpaceForAxisValues;
	}

	public Color getBarColor() {
		return barColor;
	}

	public Color getBarComparisonColor() {
		return barComparisonColor;
	}

	public int getBarBorderThickness() {
		return barBorderThickness;
	}

	public Color[] getHeatMapColorScaleColors() {
		return heatMapColorScaleColors;
	}

	public int getFunctionGraphPointSize() {
		return functionGraphPointSize;
	}

	public void setAxisThickness(int axisThickness) {
		this.axisThickness = axisThickness;
	}

	public void setAxisColor(Color axisColor) {
		this.axisColor = axisColor;
	}

	public void setAxisValueFontSize(int axisValueFontSize) {
		this.axisValueFontSize = axisValueFontSize;
	}

	public void setShowAxisValues(boolean showAxisValues) {
		this.showAxisValues = showAxisValues;
	}

	public void setAdditionalSpaceForAxisValues(int additionalSpaceForAxisValues) {
		this.additionalSpaceForAxisValues = additionalSpaceForAxisValues;
	}

	public void setxSpaceForAxisValues(int xSpaceForAxisValues) {
		this.xSpaceForAxisValues = xSpaceForAxisValues;
	}

	public void setySpaceForAxisValues(int ySpaceForAxisValues) {
		this.ySpaceForAxisValues = ySpaceForAxisValues;
	}

	public void setBarColor(Color barColor) {
		this.barColor = barColor;
	}

	public void setBarComparisonColor(Color barComparisonColor) {
		this.barComparisonColor = barComparisonColor;
	}

	public void setBarBorderThickness(int barBorderThickness) {
		this.barBorderThickness = barBorderThickness;
	}

	public void setHeatMapColorScaleColors(Color[] heatMapColorScaleColors) {
		this.heatMapColorScaleColors = heatMapColorScaleColors;
	}

	public void setFunctionGraphPointSize(int functionGraphPointSize) {
		this.functionGraphPointSize = functionGraphPointSize;
	}

	public Color getFunctionGraphPointColor() {
		return functionGraphPointColor;
	}

	public void setFunctionGraphPointColor(Color functionGraphPointColor) {
		this.functionGraphPointColor = functionGraphPointColor;
	}

	public int getStepsInYAxis() {
		return stepsInYAxis;
	}

	public void setStepsInYAxis(int stepsInYAxis) {
		this.stepsInYAxis = stepsInYAxis;
	}

	public int getStepsInXAxis() {
		return stepsInXAxis;
	}

	public void setStepsInXAxis(int stepsInXAxis) {
		this.stepsInXAxis = stepsInXAxis;
	}

	public int getHeatMapLabelBorderThickness() {
		return heatMapLabelBorderThickness;
	}

	public void setHeatMapLabelBorderThickness(int heatMapLabelBorderThickness) {
		this.heatMapLabelBorderThickness = heatMapLabelBorderThickness;
	}

	public int getHeatMapColorScaleVerticalSpace() {
		return heatMapColorScaleVerticalSpace;
	}

	public void setHeatMapColorScaleVerticalSpace(int heatMapColorScaleVerticalSpace) {
		this.heatMapColorScaleVerticalSpace = heatMapColorScaleVerticalSpace;
	}

	public Color getHeatMapColorScaleBorderColor() {
		return heatMapColorScaleBorderColor;
	}

	public void setHeatMapColorScaleBorderColor(Color heatMapColorScaleBorderColor) {
		this.heatMapColorScaleBorderColor = heatMapColorScaleBorderColor;
	}

	public int getHeatMapColorScaleBorderThickness() {
		return heatMapColorScaleBorderThickness;
	}

	public void setHeatMapColorScaleBorderThickness(int heatMapColorScaleBorderThickness) {
		this.heatMapColorScaleBorderThickness = heatMapColorScaleBorderThickness;
	}

	public int getHistogramIndexEndIndexSpace() {
		return histogramIndexEndIndexSpace;
	}

	public void setHistogramIndexEndIndexSpace(int histogramIndexEndIndexSpace) {
		this.histogramIndexEndIndexSpace = histogramIndexEndIndexSpace;
	}

	public int getHeatMapSpaceBetweenDiagramAndColorScale() {
		return heatMapSpaceBetweenDiagramAndColorScale;
	}

	public void setHeatMapSpaceBetweenDiagramAndColorScale(int heatMapSpaceBetweenDiagramAndColorScale) {
		this.heatMapSpaceBetweenDiagramAndColorScale = heatMapSpaceBetweenDiagramAndColorScale;
	}
}
