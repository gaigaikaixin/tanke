
/**
 * 1，画出坦克
 * 2.我的坦克可以上下移动
 * 3.可以发射子弹，子弹连发
 * 4.当我的子弹击中几人的坦克时，敌人的坦克消失
 * 5.击中敌人的坦克，坦克爆炸
 * 6.敌人的坦克可以动起来
 * 7.当地人击中我的坦克时，我的坦克死掉
 * 6.1 防止敌人的坦克重叠运动
 *   6.1.1判断是否碰撞的函数写到HighCool中
 * 8.可以有关卡
 * 9.可以暂停和继续
 * 10.可记录玩家成绩
 * 11java操作声音文件
 * 
 */
package hate4;
import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;
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
		//定义炸弹集合
	    Vector<Bomb> bombs=new Vector<Bomb>();
	    int hcNum=3;
	    //爆炸的三张图片,组成一颗炸弹
	    Image ima1=null;
	    Image ima2=null;
	    Image ima3=null;
	    
	    
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
				// 将MyPanel的敌人坦克喜爱尼古拉ing交给敌人的坦克
				hc.setHcs(highcool);
				//启动敌人的坦克
				Thread t=new Thread(hc);
				t.start();
				//当敌人的坦克干干创建的时候给其添加一个子弹,并加到敌人的中
				Shoot hcs=new Shoot(hc.x+10,hc.y+30,2);
				hc.hcShoot.add(hcs);
				//需要启动敌人的子弹
				Thread t2=new Thread(hcs);
				t2.start();
				highcool.add(hc);
			}
			
			//初始化爆炸图片
			try{
				ima1=ImageIO.read(new File("big.png"));
				ima2=ImageIO.read(new File("middle.png"));
				ima3=ImageIO.read(new File("small.png"));
			}catch(Exception e){
				
			}
		//	ima1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("image2/big.png"));
		//	ima2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("image2/middle.png"));
		//	ima3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("image2/big.png"));
		//	 
		}
	//重写paint函数,画出自己的坦克
		public void paint(Graphics g)
		{
			super.paint(g);
			//设定一个坦克的活动区域以及颜色
			g.fillRect(0, 0, 400, 300);
			//画出自己的坦克
			if(masa.isLive)
			{
			this.drawTank(masa.getX(),masa.getY(),g,masa.getDirect(),0);
			}
			//从ss中取出每颗子弹，并画出
			for(int i=0;i<masa.ss.size();i++)
			{
				Shoot myShoot=masa.ss.get(i);
			
			if(myShoot!=null&myShoot.isLive==true)
			{
			  g.draw3DRect(myShoot.x, myShoot.y, 1,1, false);
			}
			if(myShoot.isLive==false)
			{
				//删除子弹
				masa.ss.remove(myShoot);
			}
			}
			
			//画出炸弹
			for(int i=0;i<bombs.size();i++)
			{

				Bomb b=bombs.get(i);
				if(b.life>6)
				{
					g.drawImage(ima3, b.x, b.y, 30,30,this);
				}
				else if(b.life>3)
				{
					g.drawImage(ima2, b.x, b.y, 30,30,this);
				}
			    else
				{
					g.drawImage(ima1, b.x, b.y, 30,30,this);
				}
				//之后让b的生命值减小
				b.lifeDown();
				//如果生命值为0，则把炸弹从bombs中去掉
				if(b.life==0)
				{
					bombs.remove(b);
				}
			}
			//画出敌人的坦克
			for(int i=0;i<highcool.size();i++)
			{
				HighCool h=highcool.get(i);
				if(h.isLive)
				{
				this.drawTank(h.getX(), h.getY(), g, h.direct, 1);
			      //再画出做敌人的子弹
				for(int j=0;j<h.hcShoot.size();j++)
				{
					//取出子弹
					Shoot s=h.hcShoot.get(j);
					if(s.isLive)
					{
						g.draw3DRect(s.x, s.y, 1,1, false);

					}else
					{
						h.hcShoot.remove(s);
					}
				}
				}
			}
		}
		//判断我的子弹击中敌人的坦克
		public void hitEnemyTank()
		{
			//判断是否击中敌人的坦克
		     for(int i=0;i<masa.ss.size();i++)
		     {
		    	 //取出子弹
		    	 Shoot myShoot=masa.ss.get(i);
		    	 //判断子弹是否有效
		    	 if(myShoot.isLive)
		    	 {
		    		 for(int j=0;j<highcool.size();j++)
		    		 {
		    			 HighCool h=highcool.get(j);
		    			 if(h.isLive)
		    			 {
		    				 this.hitTank(myShoot, h);
		    			 }
		    		 }
		    		 
		    	 }
		     }
			
		}
		//敌人的子弹是否击中我的坦克
		public void hitMe()
		{
			//取出敌人的一个坦克
			for(int i=0;i<this.highcool.size();i++)
			{
				
			HighCool h=highcool.get(i);
			//取出每一个子弹
			for(int j=0;j<h.hcShoot.size();j++)
			{
				Shoot s=h.hcShoot.get(j);
				this.hitTank(s,masa);
			}
			}
		}
		
		
		//写一个函数判断一颗子弹是否击中的坦克
		public void hitTank(Shoot s,Tank h)
		{
			//判断坦克的方向
			switch(h.direct)
			{
			//如果敌人的坦克想上或者向下
			case 0:
			case 2:
				if(s.x>h.x&&s.x<h.x+20&&s.y>h.y&&s.y<h.y+30)
				{
					
					//子弹死亡。坦克死亡
					s.isLive=false;
					h.isLive=false;
					//当坦克被击中时，出现炸弹图片
					Bomb b=new Bomb(h.x,h.y);
					bombs.add(b);
				}
			
			case 1:
			case 3:
				if(s.x>h.x&&s.x<h.x+30&&s.y>h.y&&s.y<h.y+20)
				{
					//子弹死亡。坦克死亡
					s.isLive=false;
					h.isLive=false;
					//当坦克被击中时，出现炸弹图片
					Bomb b=new Bomb(h.x,h.y);
					bombs.add(b);
					
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
					g.fill3DRect(x+15, y, 5, 30,false);
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
					//开火
					if(this.masa.ss.size()<=4)
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
				//调用函数判断是否击中敌人的坦克
				this.hitEnemyTank();
				//函数，判读那敌人的坦克是否击中我的tank
				this.hitMe();
				
				
			    
				//重绘
				this.repaint();
				
			}
		}
	}
}
