package hate4;
import java.util.*;
import java.io.*;

import hate4.TankWar2Thread;

//记录类，记录玩家的各种信息，在战场的下面显示剩余坦克，右面显示战绩
class Recorder
{
	 //记录每关有多少敌人
  private static int enNum=20;
   //记录我有多少可以用的人
  private static int myLife=3;
  //记录总共消灭了多少敌人
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
	//想打中敌人的时候，减少敌人的数量
	public static void reduceEnNum()
	{
		enNum--;
	}
	//当消灭敌人的时候,战场右面显示的我的战绩增长
	public static void addDieEnNum()
	{
		dieEnNum++;
	}
	//敌人打中我的时候我的生命减少
	public static void reducemyNum()
	{
		myLife--;
	}
	
	//写一个函数，把玩家机会敌人坦克数量保存到文件中
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	public static void keepRecording()
	{
		try {
			//穿件文件流
			fw=new FileWriter("F:/javaTest/gameRecord.txt");
			bw=new BufferedWriter(fw);
			bw.write(dieEnNum+"\r\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				//后来的先关闭
				bw.close();
				fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
//炸弹类
class Bomb
{
	//准备图片，建立炸弹类，击中时建立炸弹加入到素组中，最后画出炸弹
	//坐标
	int x,y;
	//炸弹的生命
	int life=9;
	boolean isLive=true;
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	//减少炸弹的生命值
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

//子弹类
class Shoot implements Runnable
{
	int x;
	int y;
    int direct;
	int speed=3;
	//子弹是否还活着
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
			//System.out.println("子弹坐标的x="+x+"y="+y);
			//解决子弹死亡的问题
			//判断盖子但是否碰到墙边缘
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
		
	}
}


//坦克类
	class Tank
	{
		//表示坦克的横坐标
		int x=0;
		//坦克纵坐标
		int y=0;
		//坦克的方向
		int direct=0;
		//坦克的速度
		int speed=3;
		//坦克的color
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
		

		//坦克方向，上0，右1，下2，左3
				
		public int getDirect() {
			return direct;
		}
		public void setDirect(int direct) {
			this.direct = direct;
		}
		
		
		//坦克坐标
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
	
	//敌人的坦克类，将其做成线程类
	class HighCool extends Tank  implements Runnable
	{
		int times=0;
		//定义一个响亮，用来装MyPanel上所有敌人的坦克
		Vector<HighCool>  hcs=new Vector<HighCool>();
		//定义向量，装子弹
		Vector<Shoot> hcShoot=new Vector<Shoot>();
		//敌人添加子弹，应当放在杠杆创建坦克好人敌人的坦克死亡之后
		public HighCool(int x,int y)
		{
			super(x,y);
		}
		
		//得到MyPanel上的敌人向量
		public void setHcs(Vector<HighCool> vv)
		{
			this.hcs=vv;
		}
		
		
		//判断是否撞到别的敌人的坦克了
		public boolean isTouchOtherEnemy()
		{
			boolean b=false;
			
			switch(this.direct)
			{
			case 0:
				//这个坦克向上，
				//去除所有塔克
				for(int i=0;i<hcs.size();i++)
				{
					//取出第一个坦克
					HighCool hc=hcs.get(i);
					//如果这个取出的坦克不是这个坦克
					if(hc!=this)
					{
						//其他的坦克向上或者向下
						if(hc.direct==0||hc.direct==2)
						{
							//左点
							if(this.x>=hc.x&&this.x<=hc.x+20&&this.y>=hc.y&&this.y<=hc.y+30)
							{
								return true;
							}
							//右点
							if(this.x+20>=hc.x&&this.x+20<=hc.x+20&&this.y<=hc.y&&this.y<=hc.y+30)
							{
								return true;
							}
						}
						
						
						//敌人的坦克是向右或者左
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
				//这个坦克向右
				for(int i=0;i<hcs.size();i++)
				{
					//取出第一个坦克
					HighCool hc=hcs.get(i);
					//如果这个取出的坦克不是这个坦克
					if(hc!=this)
					{
						//其他的坦克向上或者向下
						if(hc.direct==0||hc.direct==2)
						{
							//上点
							if(this.x+30>=hc.x&&this.x+30<=hc.x+20&&this.y>=hc.y&&this.y<=hc.y+30)
							{
								return true;
							}
							//下点
							if(this.x+30>=hc.x&&this.x+30<=hc.x+20&&this.y+20<=hc.y&&this.y+20<=hc.y+30)
							{
								return true;
							}
						}
						
						
						//敌人的坦克是向右或者左
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
				//这个坦克向下
				for(int i=0;i<hcs.size();i++)
				{
					//取出第一个坦克
					HighCool hc=hcs.get(i);
					//如果这个取出的坦克不是这个坦克
					if(hc!=this)
					{
						//其他的坦克向上或者向下
						if(hc.direct==0||hc.direct==2)
						{
							//左点
							if(this.x>=hc.x&&this.x<=hc.x+20&&this.y+30>=hc.y&&this.y+30<=hc.y+30)
							{
								return true;
							}
							//右点
							if(this.x+20>=hc.x&&this.x+20<=hc.x+20&&this.y+30<=hc.y&&this.y+30<=hc.y+30)
							{
								return true;
							}
						}
						
						
						//敌人的坦克是向右或者左
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
				//这个坦克向左
				for(int i=0;i<hcs.size();i++)
				{
					//取出第一个坦克
					HighCool hc=hcs.get(i);
					//如果这个取出的坦克不是这个坦克
					if(hc!=this)
					{
						//其他的坦克向上或者向下
						if(hc.direct==0||hc.direct==2)
						{
							//上点
							if(this.x>=hc.x&&this.x<=hc.x+20&&this.y>=hc.y&&this.y<=hc.y+30)
							{
								return true;
							}
							//下点
							if(this.x>=hc.x&&this.x<=hc.x+20&&this.y<=hc.y+20&&this.y+20<=hc.y+30)
							{
								return true;
							}
						}
						
						
						//敌人的坦克是向右或者左
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
					//说明坦克正在向上移动,坦克在一个方向上走30
					//再换方向
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
				//让敌人的额坦克可以连续打子弹
				this.times++;
				if(times%2==0)
				{
					if(isLive)
					{
						if(hcShoot.size()<5)
						{	
						             Shoot s=null;
						             //没有子弹，添加
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
										//启动子弹线程
										Thread t=new Thread(s);
										t.start();
									}
				              }
					    }
				//让坦克随机产生一个新的方向
				this.direct=(int)(Math.random()*4);
				//判断敌人的坦克是否死亡
				if(this.isLive==false)
				{      
					//敌人的坦克死亡后退出线程
					break;
				}
				
			}
	}
	}
	
	
	//我的坦克，继承刚才的坦克
	class Masa extends Tank
	{
		//我的子弹
		//Shoot s=null;
		Vector<Shoot> ss=new Vector<Shoot>();
		Shoot s=null;
		
	 
		public Masa(int x,int y)
		{
			super(x,y );
		}
		//开火
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
					//启动子弹线程
					Thread t=new Thread(s);
					t.start();
					
				}
		//如果想我自己的坦克快点可以重新弄一下坦克速度
		//坦克向上移动
		public void moveUp()
		{
			y-=speed;
		}
		
		//坦克向right移动
		public void moveRight()
		{
			x+=speed;
		}
		//坦克向down移动
		public void moveDown()
		{
		    y+=speed;
		}
		//坦克向left移动
		public void moveLeft()
		{
			x-=speed;
		}
		
	} 
	


