package hate4;
import java.util.Vector;

import hate4.TankWar2Thread;
//ը����
class Bomb
{
	//׼��ͼƬ������ը���࣬����ʱ����ը�����뵽�����У���󻭳�ը��
	//����
	int x,y;
	//ը��������
	int life=9;
	boolean isLive=true;
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	//����ը��������ֵ
	public void lifeDown()
	{
		if(life>0)
		{
			life--;
		}
		else
		{
			this.isLive=false;
		}
	}
}

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
		boolean isLive=true;
		
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
	
	//���˵�̹���࣬���������߳���
	class HighCool extends Tank  implements Runnable
	{
		int times=0;
		
		//����������װ�ӵ�
		Vector<Shoot> hcShoot=new Vector<Shoot>();
		//��������ӵ���Ӧ�����ڸܸ˴���̹�˺��˵��˵�̹������֮��
		public HighCool(int x,int y)
		{
			super(x,y);
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
								
				switch(this.direct)
				{
				case 0:
					//˵��̹�����������ƶ�,̹����һ����������30
					//�ٻ�����
					for(int i=0;i<30;i++)
					{
						if(y>0)
						{
					    y-=speed;
						}

					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					break;
					
				case 1:
					for(int i=0;i<30;i++)
					{
						if(x<360)
						{
				           x+=speed;
						}
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				     break;
				case 2:
					for(int i=0;i<30;i++)
					{
						if(y<235)
						{
					       y+=speed;
						}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					break;
				case 3:
					for(int i=0;i<30;i++)
					{
						if(x>0)
						{
					      x-=speed;
						}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					break;
				}
				//�õ��˵Ķ�̹�˿����������ӵ�
				this.times++;
				if(times%2==0)
				{
					if(isLive)
					{
						if(hcShoot.size()<5)
						{	
						             Shoot s=null;
						             //û���ӵ������
										switch(direct)
										{
										case 0:
											s=new Shoot(x+10,y,0);
											hcShoot.add(s);
											break;
										case 1:
											s=new Shoot(x+30,y+10,1);
											hcShoot.add(s);
											break;
										case 2:
											s=new Shoot(x+10,y+30,2);
											hcShoot.add(s);
											break;
										case 3:
											s=new Shoot(x,y+10,3);
											hcShoot.add(s);
											break;
										}
										//�����ӵ��߳�
										Thread t=new Thread(s);
										t.start();
									}
				              }
					    }
				//��̹���������һ���µķ���
				this.direct=(int)(Math.random()*4);
				//�жϵ��˵�̹���Ƿ�����
				if(this.isLive==false)
				{      
					//���˵�̹���������˳��߳�
					break;
				}
				
			}
	}
	}
	
	
	//�ҵ�̹�ˣ��̳иղŵ�̹��
	class Masa extends Tank
	{
		//�ҵ��ӵ�
		//Shoot s=null;
		Vector<Shoot> ss=new Vector<Shoot>();
		Shoot s=null;
		
	 
		public Masa(int x,int y)
		{
			super(x,y );
		}
		//����
				public void shootEnemy()
				{
					
					switch(this.getDirect())
					{
					case 0:
						s=new Shoot(x+10,y,0);
						ss.add(s);
						break;
					case 1:
						s=new Shoot(x+30,y+10,1);
						ss.add(s);
						break;
					case 2:
						s=new Shoot(x+10,y+30,2);
						ss.add(s);
						break;
					case 3:
						s=new Shoot(x,y+10,3);
						ss.add(s);
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
	


