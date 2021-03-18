package gelf.view.diagrams;

import java.awt.Color;
/**
 * The singleton class, which is responsible for containing and providing access to the
 * default values and settings relevant to diagrams.
 * @author Alp Torac Genc
 *
 */
public class SettingsProvider {
	/**
	 * The amount of post comma digits to be shown.
	 */
	private final static int LOWEST_DIGIT = 1;
	/**
	 * The tolerance value to be used, when comparing floating point numbers.
	 */
	private final static double TOLERANCE = 1E-7;
	/**
	 * The layer value of {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponent}.
	 */
	private int diagramValueDisplayLayer = 1;
	/**
	 * The layer value of {@link gelf.view.diagrams.components.DiagramAxis DiagramAxis}.
	 */
	private int diagramAxisLayer = 2;
	/**
	 * The layer value of {@link gelf.view.diagrams.components.DiagramComponent DiagramComponent} subclasses that
	 * do not implement {@link gelf.view.diagrams.components.DiagramValueDisplayComponent DiagramValueDisplayComponent} and
	 * that are not {@link gelf.view.diagrams.components.DiagramAxis DiagramAxis}.
	 * <p>
	 * Such components are diagram specific components.
	 */
	private int diagramNonValueDisplayLayer = 1;
	/**
	 * The layer value of {@link gelf.view.diagrams.indicator.DiagramViewHelper DiagramViewHelper}.
	 */
	private int diagramViewHelperDisplayLayer = 2;
	/**
	 * The layer value of {@link gelf.view.diagrams.components.HoverLabel HoverLabel}.
	 */
	private int diagramHoverLabelLayer = 3;
	
	/**
	 * The proportion of the left margin of the diagram to the width of the diagram.
	 */
	private float diagramLeftMariginFactor = 1f / 20f;
	/**
	 * The proportion of the right margin of the diagram to the width of the diagram.
	 */
	private float diagramRightMariginFactor = 1f / 20f;
	/**
	 * The proportion of the top margin of the diagram to the height of the diagram.
	 */
	private float diagramTopMariginFactor = 1f / 20f;
	/**
	 * The proportion of the bottom margin of the diagram to the height of the diagram.
	 */
	private float diagramBottomMariginFactor = 3f / 20f;
	
	/**
	 * The colors to be used, when diagrams are being overlaid or
	 * brought together.
	 */
	private Color[] valueDisplayComponentColors = new Color[] {
			new Color(0xdb3939), new Color(0x36ba43)
	};
	
	/**
	 * The default thickness of the {@link gelf.view.diagrams.components.DiagramAxis DiagramAxis}.
	 */
	private int axisThickness = 1;
	/**
	 * The default color of the {@link gelf.view.diagrams.components.DiagramAxis DiagramAxis}.
	 */
	private Color axisColor = Color.BLACK;
	/**
	 * The default font size of the {@link gelf.view.diagrams.components.DiagramAxis DiagramAxis}.
	 */
	private int axisValueFontSize = 10;
	/**
	 * The default font type of the {@link gelf.view.diagrams.components.DiagramAxis DiagramAxis}.
	 */
	private String axisFontType = "TimesRoman";
	/**
	 * Whether the values of a {@link gelf.view.diagrams.components.DiagramAxis DiagramAxis} will be
	 * shown as default.
	 */
	private boolean showAxisValues = true;
	/**
	 * Additional space left for the values displayed in {@link gelf.view.diagrams.components.DiagramAxis DiagramAxis}.
	 */
	private int additionalSpaceForAxisValues = 5;
	/**
	 * The amount of steps in a {@link gelf.view.diagrams.components.DiagramAxis DiagramAxis} dedicated to be an x-axis.
	 */
	private int stepsInXAxis = 10;
	/**
	 * The amount of steps in a {@link gelf.view.diagrams.components.DiagramAxis DiagramAxis} dedicated to be an y-axis.
	 */
	private int stepsInYAxis = 10;
	
	/**
	 * The absolute minimum left margin of a diagram.
	 */
	private int diagramMinimumLeftMarigin = axisValueFontSize * 3;
	/**
	 * The absolute minimum right margin of a diagram.
	 */
	private int diagramMinimumRightMarigin = axisValueFontSize * 3;
	/**
	 * The absolute minimum bottom margin of a diagram.
	 */
	private int diagramMinimumBottomMarigin = axisValueFontSize * 3;
	/**
	 * The absolute minimum top margin of a diagram.
	 */
	private int diagramMinimumTopMarigin = axisValueFontSize * 3;
	
	/**
	 * The default thickness of the borders of {@link gelf.view.diagrams.components.DiagramBar}.
	 */
	private int barBorderThickness = 1;
	
	/**
	 * The default thickness of the borders of {@link gelf.view.diagrams.components.HeatMapLabel}.
	 */
	private int heatMapLabelBorderThickness = 1;
	/**
	 * The default thickness of the borders of {@link gelf.view.diagrams.components.DiagramColorScale}.
	 */
	private int heatMapColorScaleBorderThickness = 1;
	/**
	 * The left margin of {@link gelf.view.diagrams.components.DiagramColorScale}.
	 */
	private float heatMapColorScaleLeftMariginFactor = 1f / 20f;
	/**
	 * The right margin of {@link gelf.view.diagrams.components.DiagramColorScale}.
	 */
	private float heatMapColorScaleRightMariginFactor = 1f / 20f;
	/**
	 * The default border color of {@link gelf.view.diagrams.components.HeatMapLabel}.
	 */
	private Color heatMapColorScaleBorderColor = Color.BLACK;
	/**
	 * The default colors of {@link gelf.view.diagrams.components.DiagramColorScale}.
	 */
	private Color[] heatMapColorScaleColors = new Color[] {new Color(0x455bff), new Color(0xff5c5c)};
	
	/**
	 * The amount of value intervals to be displayed of a {@link gelf.view.diagrams.components.DiagramColorScale}.
	 */
	private int colorScaleValueDisplaySteps = 10;
	
	/**
	 * The proportion of half of the width of a {@link gelf.view.diagrams.components.BarChartBar} to the step length of the
	 * x-axis of its diagram.
	 */
	private double barChartBarWidthInSteps = 0.5d;
	
	/**
	 * The default color of {@link gelf.view.diagrams.indicator.DiagramViewHelper DiagramViewHelper}.
	 */
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
	
	/**
	 * Rounds and returns the String version of the given value in a scientific format.
	 * <p>
	 * Always {@link #LOWEST_DIGIT} amount of post comma digits will be shown (even if it ends up
	 * including trailing zeroes). 
	 * @param value a given value.
	 * @return The String value of the rounded version of the given value.
	 */
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
		result = isNegative ? "-" + result : result;
		if (exp != 0) {
			result += "e" + (-exp);
		}
		return result;
	}
	
	/**
	 * Stores the formating algorithm to be used for displaying values.
	 * @param value a given value.
	 * @return The said algorithm used on the given value.
	 */
	public String getRoundedValueAsString(float value) {
		return this.roundValue(value);
	}
	/**
	 * Stores the formating algorithm to be used for displaying values.
	 * @param value a given value.
	 * @return The said algorithm used on the given value.
	 */
	public String getRoundedValueAsString(double value) {
		return this.roundValue(value);
	}

	public Color getValueDisplayComponentColorAt(int index) {
		return this.valueDisplayComponentColors[index];
	}

	public void setValueDisplayComponentColorAt(Color valueDisplayComponentColor, int index) {
		this.valueDisplayComponentColors[index] = valueDisplayComponentColor;
	}
	
	/**
	 * @param color1 a given color.
	 * @param color2 another given color.
	 * @return The mixture of the given colors.
	 */
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

	public int getColorScaleValueDisplaySteps() {
		return colorScaleValueDisplaySteps;
	}

	public void setColorScaleValueDisplaySteps(int colorScaleValueDisplaySteps) {
		this.colorScaleValueDisplaySteps = colorScaleValueDisplaySteps;
	}

	public float getHeatMapColorScaleLeftMariginFactor() {
		return heatMapColorScaleLeftMariginFactor;
	}

	public void setHeatMapColorScaleLeftMariginFactor(float heatMapColorScaleLeftMariginFactor) {
		this.heatMapColorScaleLeftMariginFactor = heatMapColorScaleLeftMariginFactor;
	}

	public float getHeatMapColorScaleRightMariginFactor() {
		return heatMapColorScaleRightMariginFactor;
	}

	public void setHeatMapColorScaleRightMariginFactor(float heatMapColorScaleRightMariginFactor) {
		this.heatMapColorScaleRightMariginFactor = heatMapColorScaleRightMariginFactor;
	}
	
}
