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
		System.out.println("get current");
		return this.currentMatrix[i][j];
	}
	public void setCurrentMatrix(int i,int j, double val)
	{
		System.out.printf("set current [%d][%d] val %f\n",i,j,val);
		this.currentMatrix[i][j] = val;
	}
	public double getMultiplierMatrix(int i, int j)
	{
		System.out.println("get multiplier");
		return this.multiplierMatrix[i][j];
	}
	public double getCurrentMatrixCopy(int i, int j)
	{
		System.out.println("get copy");
		return this.currentMatrixCopy[i][j];	
	}
	public Manager(int numberOfThreads, int matrixDimension)
	{
		this.numberOfThreads = numberOfThreads;
		this.N = matrixDimension;
		this.pool =  Executors.newFixedThreadPool(this.numberOfThreads);
	}
	public void setMatrixList(List<double[][]> list)
	{
		this.matrixList = list;
	}
	
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
		for (int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				Worker w = new Worker(N,this,i,j);
				this.pool.execute(w); 
			}
		}
		this.pool.shutdown();
		try {
			this.pool.awaitTermination(120, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println("Should not be interrupted");
			e.printStackTrace();
			
		}
		this.pool = Executors.newFixedThreadPool(this.numberOfThreads);
		
		System.out.println("Matrix Processed");
	}
	public void createMatrixCopy()
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
