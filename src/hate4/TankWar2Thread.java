/**
 * ̹��1.0�汾,�ö��߳���ʵ�ַ����ӵ�
 * 1������һ��̹��
 * 2)�ҵ�̹�˿��Կ������������ƶ�
 * 3)�����������ӵ�����������5��
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
	
	
	//TankWar1�Ĺ��캯��
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
	
	
	
	
	//�ҵ����
	class MyTankPanel extends JPanel implements KeyListener,Runnable
	{
	//����һ���Լ���̹��
		Masa masa=null;
	//������˵�tanke
		Vector<HighCool> highcool=new Vector<HighCool>();
	    int hcNum=3;
	    
	//���캯��
		public MyTankPanel()
		{
			//��ʼ���Լ���̹��
			masa=new Masa(150,200);
			//masa.setColor(0);
			
			//��ʼ�����˵�̹��
			for(int i=0;i<hcNum;i++)
			{ 
				//����һ������̹��
				HighCool hc=new HighCool((i+1)*50,10);
			    //hc.setColor(1);
				//���뵽highcool
				hc.setDirect(2);
				highcool.add(hc);
			}
		}
	//��дpaint����,�����Լ���̹��
		public void paint(Graphics g)
 		{
			super.paint(g);
			//�趨һ��̹�˵Ļ�����Լ���ɫ
			g.fillRect(0, 0, 400, 300);
			//�����Լ���̹��
		
			this.drawTank(masa.getX(),masa.getY(),g,masa.getDirect(),0);
			//��ss��ȥ���ӵ���������
			
			for(int i=0;i<this.masa.sh.size();i++)
			{	
				Shoot myshoot=masa.sh.get(i);
				
			//����masa���ӵ�
			if(myshoot!=null&&myshoot.isLive==true)
			{
			  g.draw3DRect(myshoot.x, myshoot.y, 1,1, false);
			}
			  if(myshoot.isLive==false)
			  {
				  masa.sh.remove(myshoot);
			  }
			
			}
			//�������˵�̹��
			for(int i=0;i<highcool.size();i++)
			{
				HighCool hh=highcool.get(i);
			    
				if(hh.isLive)
					{
				     this.drawTank(highcool.get(i).getX(), highcool.get(i).getY(), g, highcool.get(i).direct, 1);
					}
			}
			
		}
		//д�����ж��Ƿ������̹��
		public void hitTank(Shoot s,HighCool h)
		{
			//���жϵ���̹�˷���
			
			switch(h.direct)
			{
				//������˵�̹�˷������ϻ�����
				case 0:
				case 2:
					if(s.x>h.x&&s.x<h.x+20&&s.y>h.y&&s.y<h.y+30);
					{
						//����֮���ӵ�����������̹������
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
				//�ж����Լ���̹�˻��ǵ��˵�̹��
				switch(tanktype)  
				{
					case 0:
						g.setColor(Color.RED);
						break;
					case 1:
						g.setColor(Color.BLUE);
						break;
				}
				//�ж�̹�˵ķ���
				switch(tankdirect)
				{
				//����
				case 0:
				
				
				//����̹��
				//1.������ߵľ���
				g.fill3DRect(x, y, 5, 30,false);
				//2����ߵľ���
				g.fill3DRect(x+15, y, 5, 30,false);
				//3���м�ľ���
				g.fill3DRect(x+5, y+5, 10, 20,false);
			    //�м��Բ��
				g.fillOval(x+5, y+10, 10,10);
				//5������Ͳ
				g.drawLine(x+10, y,x+10, y+17);
				break;
				case 1:
				//��
				//����ľ���
				g.fill3DRect(x, y, 30, 5,false);
				g.fill3DRect(x, y+15, 30, 5, false);
				g.fill3DRect(x+5, y+5, 20, 10, false);
				g.fillOval(x+10, y+5, 10, 10);
				g.drawLine(x+15,y+10, x+30, y+10);
				break;
				case 2:
				//��
					g.fill3DRect(x, y, 5, 30,false);
					//2����ߵľ���
					g.fill3DRect(x+15,y, 5, 30,false);
					//3���м�ľ���
					g.fill3DRect(x+5, y+5, 10, 20,false);
				    //�м��Բ��
					g.fillOval(x+5, y+10, 10,10);
					//5������Ͳ
					g.drawLine(x+10, y+15,x+10, y+30);
					break;
				case 3:
				//��
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
			//�����´���   ��������a,d,w,s
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_W)
				{
					//�����ҵ�̹�˷���
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
				
				//�ж�����Ƿ���j����jΪshoot
				if (e.getKeyCode()==KeyEvent.VK_J)
				{
					System.out.println("masa���ӵ�"+this.masa.sh.size());
					
					//����
					if(this.masa.sh.size()<=4)
					{
					this.masa.shootEnemy();
				    }
				}
				
				     //Ȼ�����»���ͼƬ
				    this.repaint();
				
			 }
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			public void run()
			{
			//ÿ��100����ȥ���»���
			while(true)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//�ж��Ƿ����
				
				for(int i=0;i<masa.sh.size();i++)
				{
					//ȡ���ӵ�
					Shoot myShoot=masa.sh.get(i);
					//�ж��ӵ��Ƿ���Ч
					if(myShoot.isLive)
					{
						//ȡ��ÿ��̹�������ж�
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
				//�ػ�
				this.repaint();
				
			}
		}
	}
}
