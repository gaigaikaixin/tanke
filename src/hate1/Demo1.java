/**
 * 演示如何通过Tread来开发线程
 * 每个一秒打一次hello
 */
package hate1;

public class Demo1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cat cat=new Cat();
		//启动线程，会导致run函数的运行
		cat.start();
	}
}
	class Cat extends Thread
	{
		//重写run
		public void run()
		{
			int time1=0;
			while(true)
			{
			//休眠1秒，1000表示毫秒，
		    //sleep会让进程进入Blocked状态，并释放资源
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("hello");
			time1++;
			if(time1==10)
			{
				break;
			}
			}
		}
	}


