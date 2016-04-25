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
	double[][] currentMatrix;
	double[][] currentMatrixCopy; //old values
	double[][] multiplierMatrix;
	List<double[][]> matrixList = new LinkedList<double[][]>();
	private int i;
	private int j;
	
	private Executor pool;
	
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
	public void setWorkers()
	{
		for(int i= 0; i < numberOfThreads; i++)
		{
			Worker w = new Worker(N,this,i,j);
			this.pool.execute(w);
		}
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
		this.currentMatrixCopy = this.currentMatrix;
		// set new multiplier matrix
		this.multiplierMatrix = matrixList.get(0);
		// remove from queue
		this.matrixList.remove(0);
		// do the calculations
		this.proccessMatrix();
	}
	private void proccessMatrix()
	{
		for (i=0;i<N;i++)
		{
			for(j=0;j<N;j++)
			{
				Worker w = new Worker(N,this,i,j);
				this.pool.execute(w); 
			}
		}
		System.out.println("Matrix Processed?");
	}
	public void printMatrix()
	{
		System.out.println(N);
		for (i=0;i<N;i++)
		{
			for(j=0;j<N;j++)
			{
				System.out.printf("%f ",this.currentMatrix[i][j]);
			}
			System.out.printf("\n");
		}
	}
}
