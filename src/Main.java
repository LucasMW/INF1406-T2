import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class Main 
{
	// expects ./prog path N M n
	public static void main(String args[]) 
	{
		System.out.println("INF1406 T2 - Lucas Menezes");
		if(args.length != 4)
		{
			System.out.println(String.format("Error: count %d", args.length));
			System.out.println("expects ./prog path N M n");
			return;
		}
		String filePath = args[0]; // path to matrices file 
		int N = Integer.parseInt(args[1]); //matrix dimension (NxN)
		int M = Integer.parseInt(args[2]); // number of threads to be used;
		int numberOfMatrices = Integer.parseInt(args[3]);  // number of matrices in file
		System.out.println(String.format("Received path=%s\n N=%d, M=%d, q = %d"
				,filePath,N,M,numberOfMatrices));
		

//		double [ ][ ] A = {   { 20, 18},
//                              { 18, 20},
//                          };
//		double [ ][ ] B = 
//			{   
//				{ 1, 0},
//                { 0, 1},
//            };
//		double [ ][ ] C = 
//			{   
//				{ 1, 2},
//                { 1, 2},
//            };
//		List<double[][]> list = new LinkedList<double[][]>();
//		list.add(A);
//		for(int x=0;x<30000;x++)
//		{
//			list.add(B);
//		}
		
		try {
			debugExec("pwd");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<double[][]> matrixList = null;
		try 
		{
			matrixList = readMatrixFromPath(filePath, numberOfMatrices, N);
		} catch (FileNotFoundException e) 
		{
			System.out.println("ERROR: FILE NOT FOUND");
			System.out.println(String.format("Path: %s is incorrect",filePath));
			e.printStackTrace();
			System.out.println("Terminating program");
			return;
		}
		
		Manager manager = new Manager(M, N);
		manager.setMatrixList(matrixList);
		long startTime = System.currentTimeMillis();
		manager.startBatch();
		long elapsedTime = System.currentTimeMillis() - startTime;
		
		
		System.out.println("Finished");
		System.out.println(String.format("It took %d miliseconds", elapsedTime));
		manager.printMatrix();
	}
	private static List<double[][]> readMatrixFromPath(String path, int numberOfMatrices, int N) throws FileNotFoundException
	{
		
		List<Double> doubleList = readFileAsDoubleList(path);
		
		List<double[][]> list = new LinkedList<double[][]>(); 
		for(int k = 0; k < numberOfMatrices; k++)
		{
			double[][] matrix = new double [ N ] [ N ] ; 
			for(int i= 0 ; i<N ; i++)
			{
				for(int j=0;j<N;j++)
				{
					System.out.printf("k:%d[%d,%d]\n",k,i,j);
					matrix[i][j] =  doubleList.get(0);
					doubleList.remove(0);
				}
			}
			list.add(matrix);
		}
		return list;
	}
	static private void debugExec(String cmd) throws IOException, InterruptedException
	{
		Runtime r = Runtime.getRuntime();
		Process p = r.exec(cmd);
		p.waitFor();
		BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";

		while ((line = b.readLine()) != null) {
		  System.out.println(line);
		}

		b.close();
	}
	private static List<Double> readFileAsDoubleList(String path)
	{
		List<Double> list = new LinkedList<Double>();
		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new FileReader(path));
		    String text = null;

		    while ((text = reader.readLine()) != null) {
		    	for (String txt :text.split(" "))
		    	{
		    		list.add( Double.parseDouble(txt));
		    	}
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}
		return list;
	}
	

	
}

