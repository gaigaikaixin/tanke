package hate4;
import java.util.Vector;

import hate4.TankWar2Thread;


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
			//子弹跑起来
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
	
	//敌人的坦克类
	class HighCool extends Tank
	{
		boolean isLive=true;
		public HighCool(int x,int y)
		{
			super(x,y);
		}
	}
	
	//我的坦克，继承刚才的坦克
	class Masa extends Tank
	{
		//我的子弹
		Vector<Shoot> sh=new Vector<Shoot>();
		Shoot s=null;
	
	 
		public Masa(int x,int y)
		{
			super(x,y );
		}
		//开火
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


