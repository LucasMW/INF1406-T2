import java.util.LinkedList;
import java.util.List;



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
		System.out.println(String.format("Received path=%s\n N=%d, M=%d q = %d"
				,filePath,N,M,numberOfMatrices));
		

		double [ ][ ] A = {   { 20, 18},
                              { 18, 20},
                          };
		double [ ][ ] B = 
			{   
				{ 1, 0},
                { 0, 1},
            };
		double [ ][ ] C = 
			{   
				{ 1, 2},
                { 1, 2},
            };
		List<double[][]> list = new LinkedList<double[][]>();
		list.add(A);
		for(int x=0;x<100;x++)
		{
			list.add(B);
		}
		
		Manager manager = new Manager(5, 2);
		manager.setMatrixList(list);
		manager.startBatch();
		
		System.out.println("Finished");
		System.out.println(manager.currentMatrix.toString());
		manager.printMatrix();
	}
	

	
}

