package hate4;
import java.util.Vector;

import hate4.TankWar2Thread;


//�ӵ���
class Shoot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed=3;
	//�ӵ��Ƿ񻹻���
	boolean isLive=true;
	public Shoot(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	
	public void run()
	{
		while(true)
		{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//�ӵ�������
			switch(direct)
			{
			case 0:
				y-=speed;
				break;
			case 1:
				x+=speed;
				break;
			case 2:
				y+=speed;
				break;
			case 3:
				x-=speed;
				break;
				
			}
			//System.out.println("�ӵ������x="+x+"y="+y);
			//����ӵ�����������
			//�жϸ��ӵ��Ƿ�����ǽ��Ե
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
		
	}
}


//̹����
	class Tank
	{
		//��ʾ̹�˵ĺ�����
		int x=0;
		//̹��������
		int y=0;
		//̹�˵ķ���
		int direct=0;
		//̹�˵��ٶ�
		int speed=3;
		//̹�˵�color
		int color=0;
		
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
		public int getSpeed() {
			return speed;
		}
		public void setSpeed(int speed) {
			this.speed = speed;
		}
		

		//̹�˷�����0����1����2����3
				
		public int getDirect() {
			return direct;
		}
		public void setDirect(int direct) {
			this.direct = direct;
		}
		
		
		//̹������
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		
		public Tank(int x,int y)
		{
			this.x=x;
			this.y=y;
		}
		
		
	}
	
	//���˵�̹����
	class HighCool extends Tank
	{
		boolean isLive=true;
		public HighCool(int x,int y)
		{
			super(x,y);
		}
	}
	
	//�ҵ�̹�ˣ��̳иղŵ�̹��
	class Masa extends Tank
	{
		//�ҵ��ӵ�
		Vector<Shoot> sh=new Vector<Shoot>();
		Shoot s=null;
	
	 
		public Masa(int x,int y)
		{
			super(x,y );
		}
		//����
				public void shootEnemy()
				{
					
					switch(this.direct)
					{
					case 0:
						s=new Shoot(x+10,y,0);
						sh.add(s);
						break;
					case 1:
						s=new Shoot(x+30,y+10,1);
						sh.add(s);
						break;
					case 2:
						s=new Shoot(x+10,y+30,2);
						sh.add(s);
						break;
					case 3:
						s=new Shoot(x,y+10,3);
						sh.add(s);
						break;
					}
					//�����ӵ��߳�
					Thread t=new Thread(s);
					t.start();
					
				}
		//��������Լ���̹�˿���������Ūһ��̹���ٶ�
		//̹�������ƶ�
		public void moveUp()
		{
			y-=speed;
		}
		
		//̹����right�ƶ�
		public void moveRight()
		{
			x+=speed;
		}
		//̹����down�ƶ�
		public void moveDown()
		{
		    y+=speed;
		} 
		//̹����left�ƶ�
		public void moveLeft()
		{
			x-=speed;
		}
		
	} 


