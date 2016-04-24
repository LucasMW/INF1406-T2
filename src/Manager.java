import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


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
	
	
	
	
	public Manager(int numberOfThreads, int matrixDimension)
	{
		this.numberOfThreads = numberOfThreads;
		this.N = matrixDimension;
		
	}
	public void setWorkers()
	{
		for(int i= 0; i < numberOfThreads; i++)
		{
			Worker w = new Worker(this.N,this);
			this.workers.add(w);
		}
	}
	public void startWorkers()
	{
		for(Worker w : this.workers)
		{
			Thread t = new Thread(w);
			t.start();
		}
	}
	public void nextIndex()
	{
		
	}
	public void nonThreadedAlgo()
	{
		
	}
}
