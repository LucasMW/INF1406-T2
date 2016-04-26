import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
		// get parameters
		String filePath = args[0]; // path to matrices file 
		int N = Integer.parseInt(args[1]); //matrix dimension (NxN)
		int M = Integer.parseInt(args[2]); // number of threads to be used;
		int numberOfMatrices = Integer.parseInt(args[3]);  // number of matrices in file
		System.out.println(String.format("Received path=%s\n N=%d, M=%d, q = %d"
				,filePath,N,M,numberOfMatrices));
		
		
		//read matrix list from file
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
		
		// calculations
		Manager manager = new Manager(M, N);
		manager.setMatrixList(matrixList);
		
		long startTime = System.currentTimeMillis();
		manager.startBatch();
		long elapsedTime = System.currentTimeMillis() - startTime;
		
		System.out.println("Finished");
		System.out.println(String.format("It took %d miliseconds", elapsedTime));
		manager.printMatrix();
		
		// write to file the result
		try 
		{
			writeResultToFile("resultado.txt",manager,N);
			System.out.println("Wrote to file");
		} catch (FileNotFoundException e) 
		{
			System.out.println("ERROR: couldn't write to file");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) 
		{
			System.out.println("Expect never see this\n this is a bug");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//testRoutine(); use this for performance tests
	}
	private static void writeResultToFile(String filePath,Manager manager, int N) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter(filePath, "UTF-8");
		for(int i= 0 ; i<N ; i++)
		{
			for(int j=0;j<N;j++)
			{
				writer.print(String.format(Locale.ENGLISH,"%f ", manager.getCurrentMatrix(i, j))); //use .
			}
			writer.println();
		}
		writer.close();
	}
	
	private static List<double[][]> readMatrixFromPath(String path, int numberOfMatrices, int N) throws FileNotFoundException
	{
		List<Double> doubleList = readFileAsDoubleList(path); // an easy way to do this
		// I know it's not the most efficient
		
		//transform double list in matrix list according with parameters
		List<double[][]> list = new LinkedList<double[][]>(); 
		for(int k = 0; k < numberOfMatrices; k++)
		{
			double[][] matrix = new double [ N ] [ N ] ; 
			for(int i= 0 ; i<N ; i++)
			{
				for(int j=0;j<N;j++)
				{
					//System.out.printf("k:%d[%d,%d] %f\n",k,i,j,doubleList.get(0));
					matrix[i][j] =  doubleList.get(0);
					doubleList.remove(0);
				}
			}
			list.add(matrix);
		}
		return list;
	}
	
	//create a Double List from file
	private static List<Double> readFileAsDoubleList(String path)
	{
		List<Double> list = new LinkedList<Double>();
		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new FileReader(path));
		    String text = null;

		    while ((text = reader.readLine()) != null) {
		    	for (String txt :text.split("\\s+")) // deal with spaces and tabs
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
	//use this for measurements
	public static void testRoutine()
	{
		
		List<double[][]> list = new LinkedList<double[][]>();
		double[][] A = 
				{{1,2,3,4,5},
				{1,2,3,4,5},
				{1,2,3,4,5},
				{1,2,3,4,5},
				{1,2,3,4,5}};
		double[][]Id = 
				{{1,0,0,0,0},
				{0,1,0,0,0},
				{0,0,1,0,0},
				{0,0,0,1,0},
				{0,0,0,0,1}};
		list.add(A);
		for(int i=0;i<1000;i++)
		{
			list.add(Id);
		}
		Manager manager = new Manager(2, 5);
		manager.setMatrixList(list);
		
		long startTime = System.currentTimeMillis();
		manager.startBatch();
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Finished");
		System.out.println(String.format("It took %d miliseconds", elapsedTime));
		manager.printMatrix();
	}
}

