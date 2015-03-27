/**
 * 坦克1.0版本,用多线程来实现发射子弹
 * 1）画出一个坦克
 * 2)我的坦克可以可以上下左右移动
 * 3)可以连续发子弹，最多可连发5个
 * 4)
 */

package hate4;
import java.awt.*;

import javax.swing.*;

import java.util.*;
import java.awt.event.*;
import java.util.Vector;
public class TankWar2Thread extends JFrame {
 
	MyTankPanel mytp=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankWar2Thread tankwar1=new TankWar2Thread();		

	}
	
	
	//TankWar1的构造函数
	public TankWar2Thread()
	{
		mytp=new MyTankPanel();
		Thread t=new Thread(mytp);
		t.start();
		this.add(mytp);
		this.addKeyListener(mytp);
		this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	
	
	
	//我的面板
	class MyTankPanel extends JPanel implements KeyListener,Runnable
	{
	//定义一个自己的坦克
		Masa masa=null;
	//定义敌人的tanke
		Vector<HighCool> highcool=new Vector<HighCool>();
	    int hcNum=3;
	    
	//构造函数
		public MyTankPanel()
		{
			//初始化自己的坦克
			masa=new Masa(150,200);
			//masa.setColor(0);
			
			//初始化敌人的坦克
			for(int i=0;i<hcNum;i++)
			{ 
				//创建一个敌人坦克
				HighCool hc=new HighCool((i+1)*50,10);
			    //hc.setColor(1);
				//加入到highcool
				hc.setDirect(2);
				highcool.add(hc);
			}
		}
	//重写paint函数,画出自己的坦克
		public void paint(Graphics g)
 		{
			super.paint(g);
			//设定一个坦克的活动区域以及颜色
			g.fillRect(0, 0, 400, 300);
			//画出自己的坦克
		
			this.drawTank(masa.getX(),masa.getY(),g,masa.getDirect(),0);
			//从ss中去邹子弹，并画出
			
			for(int i=0;i<this.masa.sh.size();i++)
			{	
				Shoot myshoot=masa.sh.get(i);
				
			//画出masa的子弹
			if(myshoot!=null&&myshoot.isLive==true)
			{
			  g.draw3DRect(myshoot.x, myshoot.y, 1,1, false);
			}
			  if(myshoot.isLive==false)
			  {
				  masa.sh.remove(myshoot);
			  }
			
			}
			//画出敌人的坦克
			for(int i=0;i<highcool.size();i++)
			{
				HighCool hh=highcool.get(i);
			    
				if(hh.isLive)
					{
				     this.drawTank(highcool.get(i).getX(), highcool.get(i).getY(), g, highcool.get(i).direct, 1);
					}
			}
			
		}
		//写函数判断是否击中了坦克
		public void hitTank(Shoot s,HighCool h)
		{
			//先判断敌人坦克方向
			
			switch(h.direct)
			{
				//如果敌人的坦克方向向上或向下
				case 0:
				case 2:
					if(s.x>h.x&&s.x<h.x+20&&s.y>h.y&&s.y<h.y+30);
					{
						//击中之后，子弹子网，敌人坦克死亡
						s.isLive=false;
						
						h.isLive=false;
						
					}
					
				case 1:
				case 3:
					if(s.x>h.x&&s.x<h.x+30&&s.y>h.y&&s.y<h.y+20);
					{
						s.isLive=false;
						h.isLive=false;
                  }
					
			}
		
			
					
			
		}
			public void drawTank(int x,int y,Graphics g,int tankdirect,int tanktype)
			{  
				//判断是自己的坦克还是敌人的坦克
				switch(tanktype)  
				{
					case 0:
						g.setColor(Color.RED);
						break;
					case 1:
						g.setColor(Color.BLUE);
						break;
				}
				//判断坦克的方向
				switch(tankdirect)
				{
				//向上
				case 0:
				
				
				//画出坦克
				//1.画出左边的矩形
				g.fill3DRect(x, y, 5, 30,false);
				//2画左边的矩形
				g.fill3DRect(x+15, y, 5, 30,false);
				//3画中间的矩形
				g.fill3DRect(x+5, y+5, 10, 20,false);
			    //中间的圆形
				g.fillOval(x+5, y+10, 10,10);
				//5画出炮筒
				g.drawLine(x+10, y,x+10, y+17);
				break;
				case 1:
				//右
				//上面的矩形
				g.fill3DRect(x, y, 30, 5,false);
				g.fill3DRect(x, y+15, 30, 5, false);
				g.fill3DRect(x+5, y+5, 20, 10, false);
				g.fillOval(x+10, y+5, 10, 10);
				g.drawLine(x+15,y+10, x+30, y+10);
				break;
				case 2:
				//下
					g.fill3DRect(x, y, 5, 30,false);
					//2画左边的矩形
					g.fill3DRect(x+15,y, 5, 30,false);
					//3画中间的矩形
					g.fill3DRect(x+5, y+5, 10, 20,false);
				    //中间的圆形
					g.fillOval(x+5, y+10, 10,10);
					//5画出炮筒
					g.drawLine(x+10, y+15,x+10, y+30);
					break;
				case 3:
				//左
					g.fill3DRect(x, y, 30, 5,false);
					g.fill3DRect(x, y+15, 30, 5, false);
					g.fill3DRect(x+5, y+5, 20, 10, false);
					g.fillOval(x+10, y+5, 10, 10);
					g.drawLine(x+15,y+10, x, y+10);
					break;
					
			}
		}
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			
				
				
			}
			//键按下处理   ，按键有a,d,w,s
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_W)
				{
					//设置我的坦克方向
					this.masa.setDirect(0);
					this.masa.moveUp();
				}else if(e.getKeyCode()==KeyEvent.VK_D)
				{
					this.masa.setDirect(1);
					this.masa.moveRight();
				}else if(e.getKeyCode()==KeyEvent.VK_S)
				{
					this.masa.setDirect(2);
					this.masa.moveDown();
					
				}else if(e.getKeyCode()==KeyEvent.VK_A)
				{
					this.masa.setDirect(3);
					this.masa.moveLeft();
				}
				
				//判断玩家是否按下j键，j为shoot
				if (e.getKeyCode()==KeyEvent.VK_J)
				{
					System.out.println("masa的子弹"+this.masa.sh.size());
					
					//开火
					if(this.masa.sh.size()<=4)
					{
					this.masa.shootEnemy();
				    }
				}
				
				     //然后重新绘制图片
				    this.repaint();
				
			 }
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			public void run()
			{
			//每个100毫秒去重新绘制
			while(true)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//判断是否击中
				
				for(int i=0;i<masa.sh.size();i++)
				{
					//取出子弹
					Shoot myShoot=masa.sh.get(i);
					//判断子弹是否有效
					if(myShoot.isLive)
					{
						//取出每个坦克与它判断
						for(int j=0;i<highcool.size();j++)
						{
                            HighCool h=highcool.get(j);
							if(h.isLive)
							{
								this.hitTank(myShoot, h);
							}
						 }
					  }
					}
				//重绘
				this.repaint();
				
			}
		}
	}
}
