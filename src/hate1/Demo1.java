/**
 * ��ʾ���ͨ��Tread�������߳�
 * ÿ��һ���һ��hello
 */
package hate1;

public class Demo1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cat cat=new Cat();
		//�����̣߳��ᵼ��run����������
		cat.start();
	}
}
	class Cat extends Thread
	{
		//��дrun
		public void run()
		{
			int time1=0;
			while(true)
			{
			//����1�룬1000��ʾ���룬
		    //sleep���ý��̽���Blocked״̬�����ͷ���Դ
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


