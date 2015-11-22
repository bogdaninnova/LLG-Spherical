package main; 

public class Calculator {

	public double teta = 0;
	public double fi = 0;
	
	private double t = 0;
	private double dt = 0.001;
	
	private double at = 1 * Math.PI / 2;
	private double af = 0;
	
	private final double alpha = 0.01;
	
	
	private double h = 0.25;
	private double w = 1;
	
	
	public void iteration() {
		double[] k1 = LLG(teta, fi, t);
		double[] k2 = LLG(teta + k1[0] * dt/2, fi + k1[1] * dt/2, t + dt/2);
		double[] k3 = LLG(teta + k2[0] * dt/2, fi + k2[1] * dt/2, t + dt/2);
		double[] k4 = LLG(teta + k3[0] * dt, fi + k3[1] * dt, t + dt);	
		
		teta += dt/6 * (k1[0] + 2 * k2[0] + 2 * k3[0] + k4[0]);
		fi += dt/6 * (k1[1] + 2 * k2[1] + 2 * k3[1] + k4[1]);
		t += dt;
	}
	
	private double hx(double mt, double mf, double t) {
		double ha_x = (sin(mt) * sin(at) * cos(mf - af) + cos(mt) * cos(at)) * sin(at) * cos(af);
		double ho_x = h * Math.cos(w * t);
		return ha_x + ho_x;
	}
	
	private double hy(double mt, double mf, double t) {
		double ha_y = (sin(mt) * sin(at) * cos(mf - af) + cos(mt) * cos(at)) * sin(at) * sin(af);
		double ho_y = h * Math.sin(w * t);
		return ha_y + ho_y;
	}
	
	private double hz(double mt, double mf, double t) {
		double ha_z = (sin(mt) * sin(at) * cos(mf - af) + cos(mt) * cos(at)) * cos(at);
		return ha_z;
	}
	
	private double[] LLG(double mt, double mf, double t) {
		
		double hx = hx(mt, mf, t);
		double hy = hy(mt, mf, t);
		double hz = hz(mt, mf, t);
		
		double mp = sin(mt) * sin(at) * cos(mf - af) + cos(mt) * cos(at);

		double thetta =
			(hy * cos(mf) - hx * sin(mf)) + mp * sin(at) * sin(af - mf) +
			alpha * cos(mt) * ((hx * cos(mf) + hy * sin(mf)) + mp * sin(at) * cos(af - mf)) -
			alpha * sin(mt) * (hz + mp * cos(at));
		
		double phi = 0;
		if (mt > 0.001 && mt < Math.PI - 0.001)
			phi = 
				- cos(mt) / sin(mt) *
					(hx * cos(mf) + hy * sin(mf) - 
					mp * sin(at) * cos(af - mf)) + hz + mp * cos(at) +
				alpha / sin(mt) * (hy * cos(mf) - hx * sin(mf)) +
				alpha / sin(mt) * mp * sin(at) * sin(af - mf);
		double[] array = {thetta, phi};
		return array;
	}
	
	private double sin(double a) {
		return Math.sin(a);
	}
	
	private double cos(double a) {
		return Math.cos(a);
	}
	
}
