package main; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Launcher {

	public static void main(String...strings) {
		Calculator c = new Calculator();
		int k = 500000;
		ArrayList<Double> listX = new ArrayList<Double>(k);
		ArrayList<Double> listY = new ArrayList<Double>(k);
		ArrayList<Double> listZ = new ArrayList<Double>(k);
		
		for (int i = 0; i < 0 * 300000; i++)
			c.iteration();

		LinkedList<Vector> list = new LinkedList<Vector>();
		while (k --> 0) {
			c.iteration();
			listX.add(Math.sin(c.teta) * Math.cos(c.fi));
			listY.add(Math.sin(c.teta) * Math.sin(c.fi));
			listZ.add(Math.cos(c.teta));
			list.add(new Vector(c.teta, c.fi));
		}
		
		writeDoubleList(listX, "listX");
		writeDoubleList(listY, "listY");
		writeDoubleList(listZ, "listZ");
		
		new Draw(list, 0.4 * Math.PI, 0.4 * Math.PI, 0, "w=" + Calculator.w + ";h=" + Calculator.h + ";teta=" + Calculator.at).drawTraectory(true);
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
