package hate2;

public class Demo2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dog dog=new Dog();
		Thread t=new Thread(dog);
		t.start();
	

	}
}
	class Dog implements Runnable
	{
		int time1=0;
		public void run()
		{
			while(true)
			{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("i hate u");
			time1++;
			if(time1==10)
			{
				break;
			}
			}
		}
	}


