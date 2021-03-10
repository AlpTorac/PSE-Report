package gelf.view.diagrams;

import java.awt.Color;

public class SettingsProvider {
	private final static int LOWEST_DIGIT = 1;
	private final static double TOLERANCE = Double.valueOf("1E-" + LOWEST_DIGIT);
	
	private int diagramValueDisplayLayer = 1;
	private int diagramAxisLayer = 2;
	private int diagramNonValueDisplayLayer = 1;
	private int diagramViewHelperDisplayLayer = 2;
	private int diagramHoverLabelLayer = 3;
	
	private float diagramLeftMariginFactor = 1f / 20f;
	private float diagramRightMariginFactor = 1f / 20f;
	private float diagramTopMariginFactor = 2f / 20f;
	private float diagramBottomMariginFactor = 2f / 20f;
	
	/**
	 * The colors to be used, when diagrams are being overlaid or
	 * brought together.
	 */
	private Color[] valueDisplayComponentColors = new Color[] {
			new Color(0xdb3939), new Color(0x36ba43)
	};
	
	private int axisThickness = 1;
	private Color axisColor = Color.BLACK;
	private int axisValueFontSize = 10;
	private String axisFontType = "TimesRoman";
	private boolean showAxisValues = true;
	private int additionalSpaceForAxisValues = 5;
	private int stepsInXAxis = 10;
	private int stepsInYAxis = 10;
	
	private int diagramMinimumLeftMarigin = axisValueFontSize * 3;
	private int diagramMinimumRightMarigin = axisValueFontSize * 3;
	private int diagramMinimumBottomMarigin = axisValueFontSize * 3;
	private int diagramMinimumTopMarigin = axisValueFontSize * 3;
	
	private int barBorderThickness = 1;
	
	private int heatMapLabelBorderThickness = 1;
	private int heatMapColorScaleBorderThickness = 1;
	private Color heatMapColorScaleBorderColor = Color.BLACK;
	private Color[] heatMapColorScaleColors = new Color[] {new Color(0x455bff), new Color(0xff5c5c)};
	
	/**
	 * Increase the maximum index by this amount to leave some more space for the final bar.
	 */
	private float histogramIndexEndIndexFactor = 1.1f;
	
	private double barChartBarWidthInSteps = 0.5d;
	
	private Color diagramCoordinateLineColor = new Color(0x3668ff);
	
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

	public int getBarBorderThickness() {
		return barBorderThickness;
	}

	public Color[] getHeatMapColorScaleColors() {
		return heatMapColorScaleColors;
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

	public void setBarBorderThickness(int barBorderThickness) {
		this.barBorderThickness = barBorderThickness;
	}

	public void setHeatMapColorScaleColors(Color[] heatMapColorScaleColors) {
		this.heatMapColorScaleColors = heatMapColorScaleColors;
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

	public float getHistogramIndexEndIndexFactor() {
		return histogramIndexEndIndexFactor;
	}

	public void setHistogramIndexEndIndexFactor(float histogramIndexEndIndexFactor) {
		this.histogramIndexEndIndexFactor = histogramIndexEndIndexFactor;
	}

	public String getAxisFontType() {
		return axisFontType;
	}

	public void setAxisFontType(String axisFontType) {
		this.axisFontType = axisFontType;
	}
	
	public int getDiagramValueDisplayLayer() {
		return diagramValueDisplayLayer;
	}

	public void setDiagramValueDisplayLayer(int diagramValueDisplayLayer) {
		this.diagramValueDisplayLayer = diagramValueDisplayLayer;
	}

	public int getDiagramAxisLayer() {
		return diagramAxisLayer;
	}

	public void setDiagramAxisLayer(int diagramAxisLayer) {
		this.diagramAxisLayer = diagramAxisLayer;
	}

	public int getDiagramNonValueDisplayLayer() {
		return diagramNonValueDisplayLayer;
	}

	public void setDiagramNonValueDisplayLayer(int diagramNonValueDisplayLayer) {
		this.diagramNonValueDisplayLayer = diagramNonValueDisplayLayer;
	}

	public int getDiagramViewHelperDisplayLayer() {
		return diagramViewHelperDisplayLayer;
	}

	public void setDiagramViewHelperDisplayLayer(int diagramViewHelperDisplayLayer) {
		this.diagramViewHelperDisplayLayer = diagramViewHelperDisplayLayer;
	}

	public int getDiagramHoverLabelLayer() {
		return diagramHoverLabelLayer;
	}

	public void setDiagramHoverLabelLayer(int diagramHoverLabelLayer) {
		this.diagramHoverLabelLayer = diagramHoverLabelLayer;
	}

	public float getDiagramLeftMariginFactor() {
		return diagramLeftMariginFactor;
	}

	public void setDiagramLeftMariginFactor(float diagramLeftMariginFactor) {
		this.diagramLeftMariginFactor = diagramLeftMariginFactor;
	}
	
	public float getDiagramLeftMarigin(int containerWidth) {
		if (containerWidth * this.getDiagramLeftMariginFactor() < this.diagramMinimumLeftMarigin) {
			return this.diagramMinimumLeftMarigin;
		} else {
			return containerWidth * this.getDiagramLeftMariginFactor();
		}
	}
	
	public float getDiagramRightMarigin(int containerWidth) {
		if (containerWidth * this.getDiagramRightMariginFactor() < this.diagramMinimumRightMarigin) {
			return this.diagramMinimumRightMarigin;
		} else {
			return containerWidth * this.getDiagramRightMariginFactor();
		}
	}
	
	public float getDiagramBottomMarigin(int containerHeight) {
		if (containerHeight * this.getDiagramBottomMariginFactor() < this.diagramMinimumBottomMarigin) {
			return this.diagramMinimumBottomMarigin;
		} else {
			return containerHeight * this.getDiagramBottomMariginFactor();
		}
	}
	
	public float getDiagramTopMarigin(int containerHeight) {
		if (containerHeight * this.getDiagramTopMariginFactor() < this.diagramMinimumTopMarigin) {
			return this.diagramMinimumTopMarigin;
		} else {
			return containerHeight * this.getDiagramTopMariginFactor();
		}
	}

	public float getDiagramRightMariginFactor() {
		return diagramRightMariginFactor;
	}

	public void setDiagramRightMariginFactor(float diagramRightMariginFactor) {
		this.diagramRightMariginFactor = diagramRightMariginFactor;
	}

	public float getDiagramTopMariginFactor() {
		return diagramTopMariginFactor;
	}

	public void setDiagramTopMariginFactor(float diagramTopMariginFactor) {
		this.diagramTopMariginFactor = diagramTopMariginFactor;
	}

	public float getDiagramBottomMariginFactor() {
		return diagramBottomMariginFactor;
	}

	public void setDiagramBottomMariginFactor(float diagramBottomMariginFactor) {
		this.diagramBottomMariginFactor = diagramBottomMariginFactor;
	}
	
	private String roundValue(double value) {
		boolean isNegative = value < 0;
		double absVal = Math.abs(value);
		int exp = 0;
		// -1 < value < 1
		if (absVal == 0d) {
			return "0";
		} else if (absVal < 1) {
			while (absVal < 1) {
				absVal = absVal * 10d;
				exp++;
			}
		} else if (absVal >= 10) {
			while (absVal >= 10) {
				absVal = absVal / 10d;
				exp--;
			}
		}
		double scientificValue = ((double) Math.round(absVal * Math.pow(10, LOWEST_DIGIT))) / Math.pow(10, LOWEST_DIGIT);
		if (scientificValue >= 10) {
			scientificValue = ((double) Math.round(scientificValue * Math.pow(10, LOWEST_DIGIT - 1))) / Math.pow(10, LOWEST_DIGIT);
			exp--;
		}
		String result = String.valueOf(scientificValue);
		// Replace trailing zeroes and the decimal point, if unnecessary
		result = result.contains(".") ? result.replaceAll("0*$","").replaceAll("\\.$","") : result;
		result = isNegative ? "-" + result : result;
		if (exp != 0) {
			result += "e" + (-exp);
		}
		return result;
	}
	
	public String getRoundedValueAsString(float value) {
		return this.roundValue(value);
	}
	
	public String getRoundedValueAsString(double value) {
		return this.roundValue(value);
	}

	public Color getValueDisplayComponentColorAt(int index) {
		return this.valueDisplayComponentColors[index];
	}

	public void setValueDisplayComponentColorAt(Color valueDisplayComponentColor, int index) {
		this.valueDisplayComponentColors[index] = valueDisplayComponentColor;
	}
	
	public static Color getMixedColor(Color color1, Color color2) {
		float[] hsbColor1 = new float[3];
		Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), hsbColor1);
		
		float[] hsbColor2 = new float[3];
		Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), hsbColor2);
		
		double mixHue = (hsbColor1[0] + hsbColor2[0]) / 2d;
		double mixSaturation = (hsbColor1[1] + hsbColor2[1]) / 2d;
		double mixBrightness = (hsbColor1[2] + hsbColor2[2]) / 2d;
		
		int mixedColorBits = Color.HSBtoRGB((float) mixHue, (float) mixSaturation, (float) mixBrightness);
		
		Color mixedColor = new Color(mixedColorBits);
		
		return mixedColor;
	}

	public Color getDiagramCoordinateLineColor() {
		return diagramCoordinateLineColor;
	}

	public void setDiagramCoordinateLineColor(Color diagramCoordinateLineColor) {
		this.diagramCoordinateLineColor = diagramCoordinateLineColor;
	}

	public static double getTolerance() {
		return TOLERANCE;
	}

	public double getBarChartBarWidthInSteps() {
		return barChartBarWidthInSteps;
	}

	public void setBarChartBarWidthInSteps(double barChartBarWidthInSteps) {
		this.barChartBarWidthInSteps = barChartBarWidthInSteps;
	}
	
}
