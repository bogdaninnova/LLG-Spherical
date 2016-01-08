package main; 

public class Calculator {

	private double t = 0;
	private double dt = 0.01;

	// Anisotropy axe's angles
	public static final double at = 0 * Math.PI;
	public static final double af = 0 * Math.PI;

	// m_theta, m_fi
	public static double teta = 0.1 * Math.PI;
	public static double fi = af;

	private final double alpha = 0.01;

	//Circular field's characteristics
	public static double h = 0.2;
	public static double w = 1;
	
	
	public double dm_theta = 0;
	public double dm_fi = 0;

	public double hx = 0;
	public double hy = 0;
	public double hz = 0;
	
	

	//Runge-Kutta method
	public void iteration() {
		double[] k1 = LLG(teta, fi, t);
		double[] k2 = LLG(teta + k1[0] * dt/2, fi + k1[1] * dt/2, t + dt/2);
		double[] k3 = LLG(teta + k2[0] * dt/2, fi + k2[1] * dt/2, t + dt/2);
		double[] k4 = LLG(teta + k3[0] * dt, fi + k3[1] * dt, t + dt);	
		
		dm_theta = dt/6 * (k1[0] + 2 * k2[0] + 2 * k3[0] + k4[0]);
		dm_fi = dt/6 * (k1[1] + 2 * k2[1] + 2 * k3[1] + k4[1]);
		
		teta +=dm_theta;
		fi += dm_fi;
		t += dt;
	}
	
	public double getdQ() {
		return hx * sin(dm_theta) * cos(dm_fi) +
				hy * sin(dm_theta) * sin(dm_fi) +
				hz * cos(dm_theta);
	}
	
	private double hx(double mt, double mf, double t) {
		double ha_x = m_p(mt, mf) * sin(at) * cos(af);
		double ho_x = h * Math.cos(w * t);
		return hx = ha_x + ho_x;
	}
	
	private double hy(double mt, double mf, double t) {
		double ha_y = m_p(mt, mf) * sin(at) * sin(af);
		double ho_y = h * Math.sin(w * t);
		return hy = ha_y + ho_y;
	}
	
	private double hz(double mt, double mf, double t) {
		double ha_z = m_p(mt, mf) * cos(at);
		return hz = ha_z;
	}

	private double m_p(double mt, double mf) {
		return sin(mt) * sin(at) * cos(mf - af) + cos(mt) * cos(at);
	}
	
	private double[] LLG(double mt, double mf, double t) {
		
		double hx = hx(mt, mf, t);
		double hy = hy(mt, mf, t);
		double hz = hz(mt, mf, t);

		//m_parallel
		double mp = m_p(mt, mf);

		//m_theta
		double thetta =
			(hy * cos(mf) - hx * sin(mf)) + mp * sin(at) * sin(af - mf) +
			alpha * cos(mt) * ((hx * cos(mf) + hy * sin(mf)) + mp * sin(at) * cos(af - mf)) -
			alpha * sin(mt) * (hz + mp * cos(at));
		
		//m_fi
		double phi =
				- cos(mt) / sin(mt) *
					(hx * cos(mf) + hy * sin(mf) +
					mp * sin(at) * cos(af - mf)) + hz + mp * cos(at) +
				alpha / sin(mt) * (hy * cos(mf) - hx * sin(mf)) +
				alpha / sin(mt) * mp * sin(at) * sin(af - mf);
		phi /= alpha * alpha + 1;
		thetta /= alpha * alpha + 1;
		double[] array = {thetta, phi};
		return array;
	}
	
	
	private double dW_dtheta(double mt, double mf, double t) {
		return -( h * cos(mt) * cos(mf) * cos(w * t) + 
				h * cos(mt) * sin(mf) * sin(w * t) +
				m_p(mt, mf) * (sin(at) * cos(mt) * cos(af - mf) - cos(at) * sin(mt)));
	}
	
	private double dW_dphi(double mt, double mf, double t) {
		return -( h * sin(mt) * sin(mf) * cos(w * t) + 
				h * sin(mt) * cos(mf) * sin(w * t) +
				m_p(mt, mf) * (sin(at) * sin(mt) * sin(af - mf)));
	}
	
	
	private double[] LLG_w(double mt, double mf, double t) {
		double thetta = - 1 / sin(mt) *
				(alpha * sin(mt) * dW_dtheta(mt, mf, t) + dW_dphi(mt, mf, t));
		double phi = - 1 / sin(mt) / sin(mt) *
				(sin(mt) * dW_dtheta(mt, mf, t) - alpha * dW_dphi(mt, mf, t));
		phi /= alpha * alpha + 1;
		thetta /= alpha * alpha + 1;
		double[] array = {thetta, phi};
		return array;
	}
	
	
	
	
	
	private static double sin(double a) {
		return Math.sin(a);
	}
	
	private static double cos(double a) {
		return Math.cos(a);
	}
	
}
