
public class Worker implements Runnable 
{
	int i;
	int j;
	double r;
	double N;
	Manager manager;
	
	public Worker(int N,Manager manager, int i, int j)
	{
		this.N = N;
		this.manager = manager;
	}
	
	@Override
	public void run() 
	{
		evaluate(i,j);
	}
	private void evaluate(int i, int j)
	{
		double acc=0;
		for(int k=0;k<N;k++)
		{
			acc += manager.currentMatrixCopy[i][k] * manager.multiplierMatrix[k][j];
		}
		manager.currentMatrix[i][j] = acc;
	}
	

}
