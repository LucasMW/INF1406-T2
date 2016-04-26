import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

// Controls all workers
public class Manager 
{
	int numberOfThreads;
	int N; // matrixDimension
	int matrixAmmount;
	List<Worker> workers = new ArrayList<Worker>();
	private double[][] currentMatrix;
    private double[][] currentMatrixCopy; //old values
	private double[][] multiplierMatrix;
	List<double[][]> matrixList = new LinkedList<double[][]>();
	public Object matrixlock = new Object();
	
	private ExecutorService pool;
	
	public double getCurrentMatrix(int i,int j)
	{
		//System.out.println("get current");
		return this.currentMatrix[i][j];
	}
	public void setCurrentMatrix(int i,int j, double val)
	{
		//System.out.printf("set current [%d][%d] val %f\n",i,j,val);
		this.currentMatrix[i][j] = val;
	}
	public double getMultiplierMatrix(int i, int j)
	{
		//System.out.println("get multiplier");
		return this.multiplierMatrix[i][j];
	}
	public double getCurrentMatrixCopy(int i, int j)
	{
		//System.out.println("get copy");
		return this.currentMatrixCopy[i][j];	
	}
	public Manager(int numberOfThreads, int matrixDimension)
	{
		this.numberOfThreads = numberOfThreads;
		this.N = matrixDimension;
		this.pool =  Executors.newFixedThreadPool(this.numberOfThreads);
	}
	//set list of matrix to proccess
	public void setMatrixList(List<double[][]> list)
	{
		this.matrixList = list;
	}
	// do batch calculations for all matrix list
	public void startBatch()
	{
		this.currentMatrix = matrixList.get(0);
		this.matrixList.remove(0);
		
		while(!matrixList.isEmpty())
		{
			nextMatrix();
		}
		
	}
	public void nextMatrix()
	{
		System.out.println("Next Matrix called");
		// copy matrix data
		this.createMatrixCopy();
		
		// set new multiplier matrix
		this.multiplierMatrix = matrixList.get(0);
		// remove from queue
		this.matrixList.remove(0);
		// do the calculations
		this.proccessMatrix();
	}
	private void proccessMatrix()
	{
		List<Callable<Object>> todo = new LinkedList<Callable<Object>>();
		for (int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				Worker w = new Worker(N,this,i,j);
				todo.add(Executors.callable(w)); 
			}
		}
		// wait threads finish this product
		try {
			this.pool.invokeAll(todo);
		} catch (InterruptedException e) {
			System.out.println("Please do not interrupt");
			e.printStackTrace();
		}
		System.out.println("Matrix Processed");
	}
	// create deep copy of current Matrix
	private void createMatrixCopy()
	{
		this.currentMatrixCopy = new double[N][N];
		for (int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				this.currentMatrixCopy[i][j] = this.currentMatrix[i][j];
			}
			
		}
	}
	//use this to forbid pool to operate again
	public void terminatePool()
	{
		this.pool.shutdown();
		System.out.println("waiting threads to finish");
		try {
			this.pool.awaitTermination(120, TimeUnit.SECONDS);
			System.out.println("pool finished");
		} catch (InterruptedException e) {
			System.out.println("pool was forced to shutdown");
			this.pool.shutdownNow();
		}
	}
	//debug printMatrix
	public void printMatrix()
	{
		System.out.println(N);
		for (int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				System.out.printf("%f ",this.currentMatrix[i][j]);
			}
			System.out.printf("\n");
		}
	}
}
