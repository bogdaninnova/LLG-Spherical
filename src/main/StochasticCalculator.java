package main;

import java.util.Random;

public class StochasticCalculator {

    private static final double dt = Math.pow(10, -8);
    private static final double da = Math.pow(10, -8);//step by angle

    private static final double w = 1;
    private static final double h = 1;

    private double theta = 0;
    private double phi = 0;
    private double t = 0;

    private static Random rand = new Random();

    private static final double ALPHA = 0.01;
    private static final double L = 1 / (1 + ALPHA * ALPHA);
    private static final double Ha = 1;
    private static final double M = 1;
    private static final double V = 1;
    private static final double KbT = 1;
    private static final double T = 1;
    private static final double E = Ha * M * V / KbT / T;

    private static final double SQRT_2_ALPHA_E = Math.sqrt(2 * ALPHA / E);

    private static final double THETA_AN = Math.PI / 4;
    private static final double PHI_AN = 0;
    private static final double SIN_PHI_AN = sin(PHI_AN);
    private static final double COS_PHI_AN = cos(PHI_AN);
    private static final double SIN_THETA_AN = sin(THETA_AN);
    private static final double COS_THETA_AN = cos(THETA_AN);


    public void iteration() {

        double dW = rand.nextDouble();//0..1

        double dEnergy_dtheta = get_dW_dtheta(theta, phi, t);
        double dEnergy_dphi = get_dW_dphi(theta, phi, t);
        double sinTheta_1 = 1 / sin(theta);

        double dTheta = L * dt * (-ALPHA * dEnergy_dtheta - sinTheta_1 * dEnergy_dphi + ALPHA / E * Math.atan(theta) + SQRT_2_ALPHA_E * dW);
        double dPhi = 0;

        theta += dTheta;
        phi += dPhi;
        t += dt;
    }


    private static double get_dW_dtheta(double theta, double phi, double t) {
        return (getW(theta + da, phi, t) - getW(theta, phi, t)) / da;
    }

    private static double get_dW_dphi(double theta, double phi, double t) {
        return (getW(theta, phi + da, t) - getW(theta, phi, t)) / da;
    }

    private static double getW(double theta, double phi, double t) {
        return -0.5 * Math.pow(getM_H0(t, theta, phi), 2) - getM_Ea(theta, phi);
    }

    private static double getM_H0(double t, double theta, double phi) {
        double[] h0 = getH0(t);
        return sin(theta) * cos(phi) * h0[0] + sin(theta) * sin(phi) * h0[1] + cos(theta) * h0[2];
    }

    private static double[] getH0(double t) {
        double[] result = new double[3];
        result[0] = h * sin(w * t);
        result[1] = h * cos(w * t);
        result[2] = 0;
        return result;
    }

    private static double getM_Ea(double theta, double phi) {
        return sin(theta) * cos(phi) * SIN_THETA_AN * COS_PHI_AN +
                sin(theta) * sin(phi) * SIN_THETA_AN * SIN_PHI_AN +
                cos(theta) * COS_THETA_AN;
    }

    private static double sin(double x) {
        return Math.sin(x);
    }

    private static double cos(double x) {
        return Math.cos(x);
    }

}
