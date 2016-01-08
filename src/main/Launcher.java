package main; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Launcher {

	public static void main(String...strings) {
		for (double w = 0.1; w <=2.001; w += 0.1)
			System.out.println(energy(w));
	}
	
	public static double energy(double w) {
		Calculator c = new Calculator();
		c.w = w;
		int k = 5000;
		ArrayList<Double> listX = new ArrayList<Double>(k);
		ArrayList<Double> listY = new ArrayList<Double>(k);
		ArrayList<Double> listZ = new ArrayList<Double>(k);
		
		for (int i = 0; i < 50000; i++)
			c.iteration();

		LinkedList<Vector> list = new LinkedList<Vector>();
		
		double energy = 0;
		double steps = 0;
		
		while (k --> 0) {
			c.iteration();
			energy += c.getdQ();
			steps++;
			listX.add(Math.sin(c.teta) * Math.cos(c.fi));
			listY.add(Math.sin(c.teta) * Math.sin(c.fi));
			listZ.add(Math.cos(c.teta));
			list.add(new Vector(c.teta, c.fi));
		}
		
		
		
//		writeDoubleList(listX, "listX");
//		writeDoubleList(listY, "listY");
//		writeDoubleList(listZ, "listZ");
		
		new Draw(list, 0.4 * Math.PI, 0.4 * Math.PI, 0, "w=" + Calculator.w + ";h=" + Calculator.h + ";teta=" + Calculator.at).drawTraectory(true);

		return(energy / steps);
	}
	
	public static void writeDoubleList(List<Double> averrageList, String name) {
		ListIterator<Double> iter = averrageList.listIterator();
		File file = new File(name + ".txt");
		try {
			@SuppressWarnings("resource")
			FileWriter writer = new FileWriter(file);
			while (iter.hasNext()) writer.append(iter.next() + "\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
