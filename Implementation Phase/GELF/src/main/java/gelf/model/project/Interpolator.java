package gelf.model.project;

import org.apache.commons.math3.analysis.interpolation.NevilleInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;

/**
 * Provides interpolating functionality in order to unify pin, cell and library
 * indexes as well as provide comprehensive visualisation data
 * 
 * @author Xhulio Pernoca
 * 
 *         Uses code of apache interpolation to fit the purposes of this project
 *         Copyright 2021 [name of copyright owner] SPDX-License-Identifier:
 *         Apache-2.0
 */
public class Interpolator {

    /**
     * Instantiates the interpolator
     */
    public Interpolator() {
    }

    /**
     * Interpolates a set of new values (usually for an input attribute)
     * 
     * @param indexes     the initial index_1 array
     * @param values      the initial value array
     * @param newIndexes1 the desired index_1 array
     * @return the desired value array
     */
    public float[] interpolate(float[] indexes, float[] values, float[] newIndexes) {
        if (indexes == null || values == null || newIndexes == null) {
            return null;
        }
        if (indexes.length == 0 || values.length == 0 || newIndexes.length == 0) {
            return null;
        }
        float[] newValues = new float[newIndexes.length];
        if (values.length == 1) {
            float value = values[0];
            for (int i = 0; i < newValues.length; i++) {
                newValues[i] = value;
            }
            return newValues;
        }
        double[] doubleValues = convertFloatsToDoubles(values);
        double[] doubleIndexes = convertFloatsToDoubles(indexes);
        PolynomialFunctionLagrangeForm function;
        NevilleInterpolator interpolator = new NevilleInterpolator();
        function = interpolator.interpolate(doubleIndexes, doubleValues);
        for (int i = 0; i < newIndexes.length; i++) {
            Double newIndex = (double) newIndexes[i];
            newValues[i] = (float) function.value(newIndex);
        }
        return newValues;
    }

    /**
     * Interpolates a set of new values (usually for an output attribute)
     * 
     * @param indexes1    the initial index_1 array
     * @param indexes2    the initial index_2 array
     * @param values      the initial value array
     * @param newIndexes1 the desired index_1 array
     * @param newIndexes2 the desired index_2 array
     * @return the desired value array
     */
    public float[][] bicubicInterpolate(float[] indexes1, float[] indexes2, float[][] values, float[] newIndexes1,
            float[] newIndexes2) {
        if (values == null || values[0] == null || indexes1 == null || indexes2 == null || newIndexes1 == null
                || newIndexes2 == null) {
            return null;
        }
        float[][] newValues = new float[newIndexes1.length][newIndexes2.length];
        double[][] newDoubleValues = new double[newIndexes1.length][newIndexes2.length];
        double[] doubleIndexes1 = convertFloatsToDoubles(indexes1);
        double[] doubleIndexes2 = convertFloatsToDoubles(indexes2);
        double[][] newIntermediateValues = new double[newIndexes2.length][indexes1.length];
        double[][] doubleValues = new double[values.length][values[0].length];
        for (int i = 0; i < values.length; i++) {
            doubleValues[i] = convertFloatsToDoubles(values[i]);
        }
        for (int i = 0; i < indexes1.length; i++) {
            PolynomialFunctionLagrangeForm function;
            NevilleInterpolator interpolator = new NevilleInterpolator();
            function = interpolator.interpolate(doubleIndexes2, doubleValues[i]);
            for (int j = 0; j < newIndexes2.length; j++) {
                newIntermediateValues[j][i] = function.value(newIndexes2[j]);
            }
        }
        for (int i = 0; i < newIndexes2.length; i++) {
            PolynomialFunctionLagrangeForm function;
            NevilleInterpolator interpolator = new NevilleInterpolator();
            function = interpolator.interpolate(doubleIndexes1, newIntermediateValues[i]);
            for (int j = 0; j < newIndexes1.length; j++) {
                newDoubleValues[j][i] = function.value(newIndexes1[j]);
            }
        }
        for (int i = 0; i < newDoubleValues.length; i++) {
            int length = newDoubleValues[i].length;
            for (int j = 0; j < length; j++) {
                newValues[i][j] = (float) newDoubleValues[i][j];
            }
        }
        return newValues;
    }

    /**
     * Converts an array of floats into one of doubles Should probably not belong
     * here, but can't find any utility package.
     * 
     * @param input an array of floats
     * @return an array of doubles
     */
    private static double[] convertFloatsToDoubles(float[] floatArray) {
        if (floatArray == null) {
            return null;
        }
        double[] doubleArray = new double[floatArray.length];
        for (int i = 0; i < floatArray.length; i++) {
            doubleArray[i] = floatArray[i];
        }
        return doubleArray;
    }
}
