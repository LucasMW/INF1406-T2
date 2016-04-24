
public class Worker implements Runnable 
{
	int i;
	int j;
	double r;
	double N;
	Manager manager;
	
	public Worker(int N,Manager manager)
	{
		this.N = N;
		this.manager = manager;
	}
	
	@Override
	public void run() 
	{
		double acc=0;
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<0;j++)
			{
				for(int k=0;k<N;k++)
				{
					acc += manager.currentMatrixCopy[i][k] * manager.multiplierMatrix[k][j];
				}
				manager.currentMatrix[i][j] = acc;
			}
		}
		// TODO Auto-generated method stub
		
	}

}
