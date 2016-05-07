package main; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Launcher {

	public static void main(String...strings) {
		StochasticCalculator c = new StochasticCalculator();

		System.out.println(StochasticCalculator.get_dW_dtheta(Math.PI /3, 0, 0));



		System.exit(0);

		int k = 100000;
		ArrayList<Double> listX = new ArrayList<>(k);
		ArrayList<Double> listY = new ArrayList<>(k);
		ArrayList<Double> listZ = new ArrayList<>(k);


		LinkedList<Vector> list = c.getResult(k * 10);
		
		writeDoubleList(listX, "listX");
		writeDoubleList(listY, "listY");
		writeDoubleList(listZ, "listZ");
		
		new Draw(list, 0.4 * Math.PI, 0.4 * Math.PI, 0, "w=" + c.w + ";h=" + c.h + ";teta=" + c.THETA_AN).drawTraectory(true);
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
