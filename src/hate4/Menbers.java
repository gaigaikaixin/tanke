package hate4;
import java.util.*;
import java.io.*;

import hate4.TankWar2Thread;

//��¼�࣬��¼��ҵĸ�����Ϣ����ս����������ʾʣ��̹�ˣ�������ʾս��
class Recorder
{
	 //��¼ÿ���ж��ٵ���
  private static int enNum=20;
   //��¼���ж��ٿ����õ���
  private static int myLife=3;
  //��¼�ܹ������˶��ٵ���
  private static int dieEnNum=0;
  public static int getDieEnNum() {
	return dieEnNum;
}
  public static void setDieEnNum(int dieEnNUm) {
	Recorder.dieEnNum = dieEnNum;
  }
  public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	//����е��˵�ʱ�򣬼��ٵ��˵�����
	public static void reduceEnNum()
	{
		enNum--;
	}
	//��������˵�ʱ��,ս��������ʾ���ҵ�ս������
	public static void addDieEnNum()
	{
		dieEnNum++;
	}
	//���˴����ҵ�ʱ���ҵ���������
	public static void reducemyNum()
	{
		myLife--;
	}
	
	//дһ������������һ������̹���������浽�ļ���
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	public static void keepRecording()
	{
		try {
			//�����ļ���
			fw=new FileWriter("F:/javaTest/gameRecord.txt");
			bw=new BufferedWriter(fw);
			bw.write(dieEnNum+"\r\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				//�������ȹر�
				bw.close();
				fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
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
		//����һ������������װMyPanel�����е��˵�̹��
		Vector<HighCool>  hcs=new Vector<HighCool>();
		//����������װ�ӵ�
		Vector<Shoot> hcShoot=new Vector<Shoot>();
		//��������ӵ���Ӧ�����ڸܸ˴���̹�˺��˵��˵�̹������֮��
		public HighCool(int x,int y)
		{
			super(x,y);
		}
		
		//�õ�MyPanel�ϵĵ�������
		public void setHcs(Vector<HighCool> vv)
		{
			this.hcs=vv;
		}
		
		
		//�ж��Ƿ�ײ����ĵ��˵�̹����
		public boolean isTouchOtherEnemy()
		{
			boolean b=false;
			
			switch(this.direct)
			{
			case 0:
				//���̹�����ϣ�
				//ȥ����������
				for(int i=0;i<hcs.size();i++)
				{
					//ȡ����һ��̹��
					HighCool hc=hcs.get(i);
					//������ȡ����̹�˲������̹��
					if(hc!=this)
					{
						//������̹�����ϻ�������
						if(hc.direct==0||hc.direct==2)
						{
							//���
							if(this.x>=hc.x&&this.x<=hc.x+20&&this.y>=hc.y&&this.y<=hc.y+30)
							{
								return true;
							}
							//�ҵ�
							if(this.x+20>=hc.x&&this.x+20<=hc.x+20&&this.y<=hc.y&&this.y<=hc.y+30)
							{
								return true;
							}
						}
						
						
						//���˵�̹�������һ�����
						if(hc.direct==3||hc.direct==1)
						{
							if(this.x>=hc.x&&this.x<=hc.x+30&&this.y>=hc.y&&this.y<=hc.y+20)
							{
								return true;
							}
							if(this.x+20>=hc.x&&this.x+20<=hc.x+30&&this.y<=hc.y&&this.y<=hc.y+20)
							{
								return true;
							} 
						}
					}
					
				}
				
				break;
			case 1:
				//���̹������
				for(int i=0;i<hcs.size();i++)
				{
					//ȡ����һ��̹��
					HighCool hc=hcs.get(i);
					//������ȡ����̹�˲������̹��
					if(hc!=this)
					{
						//������̹�����ϻ�������
						if(hc.direct==0||hc.direct==2)
						{
							//�ϵ�
							if(this.x+30>=hc.x&&this.x+30<=hc.x+20&&this.y>=hc.y&&this.y<=hc.y+30)
							{
								return true;
							}
							//�µ�
							if(this.x+30>=hc.x&&this.x+30<=hc.x+20&&this.y+20<=hc.y&&this.y+20<=hc.y+30)
							{
								return true;
							}
						}
						
						
						//���˵�̹�������һ�����
						if(hc.direct==3||hc.direct==1)
						{
							if(this.x+30>=hc.x&&this.x+30<=hc.x+30&&this.y>=hc.y&&this.y<=hc.y+20)
							{
								return true;
							}
							if(this.x+30>=hc.x&&this.x+30<=hc.x+30&&this.y+20<=hc.y&&this.y+20<=hc.y+20)
							{
								return true;
							} 
						}
					}
					
				}
				
				break;
			case 2:
				//���̹������
				for(int i=0;i<hcs.size();i++)
				{
					//ȡ����һ��̹��
					HighCool hc=hcs.get(i);
					//������ȡ����̹�˲������̹��
					if(hc!=this)
					{
						//������̹�����ϻ�������
						if(hc.direct==0||hc.direct==2)
						{
							//���
							if(this.x>=hc.x&&this.x<=hc.x+20&&this.y+30>=hc.y&&this.y+30<=hc.y+30)
							{
								return true;
							}
							//�ҵ�
							if(this.x+20>=hc.x&&this.x+20<=hc.x+20&&this.y+30<=hc.y&&this.y+30<=hc.y+30)
							{
								return true;
							}
						}
						
						
						//���˵�̹�������һ�����
						if(hc.direct==3||hc.direct==1)
						{
							if(this.x>=hc.x&&this.x<=hc.x+30&&this.y+30>=hc.y&&this.y+30<=hc.y+20)
							{
								return true;
							}
							if(this.x+20>=hc.x&&this.x+20<=hc.x+30&&this.y+30<=hc.y&&this.y+30<=hc.y+20)
							{
								return true;
							} 
						}
					}
					
				}
				
				
				break;
			case 3:
				//���̹������
				for(int i=0;i<hcs.size();i++)
				{
					//ȡ����һ��̹��
					HighCool hc=hcs.get(i);
					//������ȡ����̹�˲������̹��
					if(hc!=this)
					{
						//������̹�����ϻ�������
						if(hc.direct==0||hc.direct==2)
						{
							//�ϵ�
							if(this.x>=hc.x&&this.x<=hc.x+20&&this.y>=hc.y&&this.y<=hc.y+30)
							{
								return true;
							}
							//�µ�
							if(this.x>=hc.x&&this.x<=hc.x+20&&this.y<=hc.y+20&&this.y+20<=hc.y+30)
							{
								return true;
							}
						}
						
						
						//���˵�̹�������һ�����
						if(hc.direct==3||hc.direct==1)
						{
							if(this.x>=hc.x&&this.x<=hc.x+30&&this.y>=hc.y&&this.y<=hc.y+20)
							{
								return true;
							}
							if(this.x>=hc.x&&this.x<=hc.x+30&&this.y+20<=hc.y&&this.y+20<=hc.y+20)
							{
								return true;
							} 
						}
					}
					
				}
				
				
				break;
			}
			
			return b;
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
						if(y>0&&!this.isTouchOtherEnemy())
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
						if(x<360&&!this.isTouchOtherEnemy())
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
						if(y<235&&!this.isTouchOtherEnemy())
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
						if(x>0&&!this.isTouchOtherEnemy())
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
	


