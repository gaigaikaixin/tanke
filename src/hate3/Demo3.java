/**
 * �����߳�ͬʱ���еİ���
 */
package hate3;

public class Demo3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pig p=new Pig(10);
		Bird b=new Bird(10);
		Thread t1=new Thread(p);
		Thread t2=new Thread(b);
		t1.start();
		t2.start();
		

	}

}

//��ӡ
class Pig implements Runnable
{
	public Pig(int n)
	{
			this.n=n;
	}
	int n=0;
	int time1=0;
	public void run()
	{
		while(true)
		{
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time1++;
			System.out.println("����һ���̣߳����������"+time1+"�߳�");
			if(time1==n)
			{
				break;
			}
			
		}
	}
	}

//����ѧ��
class Bird implements Runnable
{
	int n=0;
	int res=0;
	int time1=0;
	public Bird(int n)
	{
		this.n=n;
	}
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
			res+=(++time1);
			System.out.println("��ǰ�ڵ���"+res);
			if(time1==n)
			{
				System.out.println("�������"+res);
				break;
			}
			
		}
	}
	}
