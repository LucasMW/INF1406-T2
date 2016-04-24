

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
				{ 20, 18},
                { 18, 20},
            };
		double [ ][ ] C = 
			{   
				{ 20, 18},
                { 18, 20},
            };
	}
	
}

