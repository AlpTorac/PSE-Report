package gelf.view.diagrams;

import java.awt.Color;
import java.awt.Font;

public class SettingsProvider {
	
	private int axisThickness = 1;
	private Color axisColor = Color.BLACK;
	private int axisValueFontSize = 10;
	private String axisFontType = "TimesRoman";
	private int fontStyle = Font.PLAIN;
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
	
	private Color barColor = Color.RED;
	private Color barComparisonColor = Color.GREEN;
	private Color barBorderColor = Color.BLACK;
	private int barBorderThickness = 1;
	
	private Color diagramLabelBorderColor = Color.BLACK;
	private Color diagramLabelForegroundColor = Color.BLACK;
	private int diagramLabelFontSize = 10;
	private String diagramLabelFontType = "TimesRoman";
	
	private Color hoverLabelBorderColor = Color.BLACK;
	private Color hoverLabelForegroundColor = Color.BLACK;
	private int hoverLabelFontSize = 10;
	private String hoverLabelFontType = "TimesRoman";
	
	private int functionGraphPointSize;
	private Color functionGraphPointColor;
	
	private int heatMapLabelBorderThickness = 1;
	private int heatMapColorScaleBorderThickness = 1;
	private Color heatMapColorScaleBorderColor = Color.BLACK;
	private Color[] heatMapColorScaleColors = new Color[] {Color.RED, Color.BLUE};
	/**
	 * The vertical space left for the color scale of the heat map.
	 */
	private int heatMapColorScaleVerticalSpace = 200;
	
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

	public String getAxisFontType() {
		return axisFontType;
	}

	public int getFontStyle() {
		return fontStyle;
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

	public Color getBarBorderColor() {
		return barBorderColor;
	}

	public int getBarBorderThickness() {
		return barBorderThickness;
	}

	public Color getDiagramLabelBorderColor() {
		return diagramLabelBorderColor;
	}

	public Color getDiagramLabelForegroundColor() {
		return diagramLabelForegroundColor;
	}

	public int getDiagramLabelFontSize() {
		return diagramLabelFontSize;
	}

	public String getDiagramLabelFontType() {
		return diagramLabelFontType;
	}

	public Color getHoverLabelBorderColor() {
		return hoverLabelBorderColor;
	}

	public Color getHoverLabelForegroundColor() {
		return hoverLabelForegroundColor;
	}

	public int getHoverLabelFontSize() {
		return hoverLabelFontSize;
	}

	public String getHoverLabelFontType() {
		return hoverLabelFontType;
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

	public void setAxisFontType(String axisFontType) {
		this.axisFontType = axisFontType;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
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

	public void setBarBorderColor(Color barBorderColor) {
		this.barBorderColor = barBorderColor;
	}

	public void setBarBorderThickness(int barBorderThickness) {
		this.barBorderThickness = barBorderThickness;
	}

	public void setDiagramLabelBorderColor(Color diagramLabelBorderColor) {
		this.diagramLabelBorderColor = diagramLabelBorderColor;
	}

	public void setDiagramLabelForegroundColor(Color diagramLabelForegroundColor) {
		this.diagramLabelForegroundColor = diagramLabelForegroundColor;
	}

	public void setDiagramLabelFontSize(int diagramLabelFontSize) {
		this.diagramLabelFontSize = diagramLabelFontSize;
	}

	public void setDiagramLabelFontType(String diagramLabelFontType) {
		this.diagramLabelFontType = diagramLabelFontType;
	}

	public void setHoverLabelBorderColor(Color hoverLabelBorderColor) {
		this.hoverLabelBorderColor = hoverLabelBorderColor;
	}

	public void setHoverLabelForegroundColor(Color hoverLabelForegroundColor) {
		this.hoverLabelForegroundColor = hoverLabelForegroundColor;
	}

	public void setHoverLabelFontSize(int hoverLabelFontSize) {
		this.hoverLabelFontSize = hoverLabelFontSize;
	}

	public void setHoverLabelFontType(String hoverLabelFontType) {
		this.hoverLabelFontType = hoverLabelFontType;
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
}
