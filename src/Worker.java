
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
		this.i=i;
		this.j=j;
	}
	
	@Override
	public void run() 
	{
		evaluate(this.i,this.j);
	}
	private void evaluate(int i, int j)
	{
		double acc=0;
		for(int k=0;k<N;k++)
		{
			double a = manager.getCurrentMatrixCopy(i,k);
			double b = manager.getMultiplierMatrix(k,j);
			double x = a * b;
			System.out.printf("i %d,j %d,k %d  %f * %f = %f\n",i,j,k,a,b,x);
			acc += x;
		}
		manager.setCurrentMatrix(i, j, acc);
		System.out.printf("i=%d j=%d = %f\n",i,j,acc);
		acc=0;
		this.i=-1;
		this.j=-1;
	}
	

}
