
/**
 * 1������̹��
 * 2.�ҵ�̹�˿��������ƶ�
 * 3.���Է����ӵ����ӵ�����
 * 4.���ҵ��ӵ����м��˵�̹��ʱ�����˵�̹����ʧ
 * 5.���е��˵�̹�ˣ�̹�˱�ը
 * 6.���˵�̹�˿��Զ�����
 * 7.�����˻����ҵ�̹��ʱ���ҵ�̹������
 * 6.1 ��ֹ���˵�̹���ص��˶�
 *   6.1.1�ж��Ƿ���ײ�ĺ���д��HighCool��
 * 8.�����йؿ�
 *   8.1 ����һ��panel ���ǿյ�,myStartPanel
 *   8.2 ������������˸�ģ���һ�ử����һ�᲻������
 * 9.������ͣ�ͼ���
 *   9.1 ���û���������ͣ��ʱ��
 *      ���ӵ����ٶȺ�̹�˵��ٶ���Ϊ0����̹�˵ķ���Ҫ�仯
 * 10.�ɼ�¼��ҳɼ�
 *    10.1���ļ�������ʽ��С��Ϸ���ļ�������Ϸ�����ݿ⣩
 *    10.2��дһ���࣬�����ҵļ�¼
 *    10.3 ����ɱ�Ѷ���������˶��ٵ��˵�̹��
 * 11java���������ļ�
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

public class TankWar2Thread extends JFrame implements ActionListener{
 
	  MyTankPanel mytp=null;
	//����һ����ʼ��ʾ��panel
	myStartPanel msp=null;
	//��������Ҫ�Ĳ˵�
	JMenuBar jmb=null;
	//��ʼ��Ϸ
	JMenu jm1=null;
	JMenuItem jmi1=null;
	//�˳�ϵͳ�˵�
	JMenuItem jmi2=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankWar2Thread tankwar1=new TankWar2Thread();		

	}
	
	
	//TankWar1�Ĺ��캯��
	public TankWar2Thread()
	{
		
		//�Բ˵��Լ��˵�ѡ����д���
		jmb=new JMenuBar();
		jm1=new JMenu("game(G)");
		jm1.setMnemonic('G');
		jmi1=new JMenuItem("start new game(N)");
		jmi2=new JMenuItem("exit game(E)");
		jmi2.setMnemonic('E');
		
		//��jmi1,jmi2�Ӽ���
		jmi1.addActionListener(this);
		jmi1.setActionCommand("new game");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit game");
		
		//���
        jm1.add(jmi1);
        jm1.add(jmi2);
		jmb.add(jm1);
		
		msp=new myStartPanel();
		Thread t=new Thread(msp);
		t.start();

		this.setJMenuBar(jmb);
		this.add(msp);
		this.setSize(600,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	
	}
	//�����˵���ѡ��
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//�Բ˵���ͬ��������ͬ�Ĵ���
		if(e.getActionCommand().equals("new game"))
		{
			//����ս�����
			mytp=new MyTankPanel();
			Thread t=new Thread(mytp);
			t.start();
			//��ɾ����ʾ��ʼ��Ϸ����壬������µ�ս�����
			this.remove(msp);
			this.add(mytp);
			this.addKeyListener(mytp);
			this.setVisible(true);
		}else if(e.getActionCommand().equals("exit game"))
		{
			//���������˵�����
			Recorder.keepRecording();
			//�û��˳�ϵͳ�˵�
			System.exit(0);
		}
}
	
	
	
	//����һ����ʾ������
	class myStartPanel extends JPanel implements Runnable
	{
		int times=0;//���ƿ��أ��жϻ����ǲ���
		public void paint(Graphics g) {
			super.paint(g);
			g.fillRect(0, 0, 400, 300);
			//��ʾ��Ϣ
			//������Ϣ������
			if(times%2==0)
			{
			g.setColor(Color.YELLOW);
			Font myFont=new Font("������κ",Font.BOLD,30);
			g.setFont(myFont);
			g.drawString("stage:1", 160, 100);
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				times++;
				//�ػ�
				this.repaint();
			}
		}
		
	}
	
	
	
	//�ҵ����
	class MyTankPanel extends JPanel implements KeyListener,Runnable
	{
	//����һ���Լ���̹��
		Masa masa=null;
	//������˵�tanke
		Vector<HighCool> highcool=new Vector<HighCool>();
		//����ը������
	    Vector<Bomb> bombs=new Vector<Bomb>();
	    int hcNum=3;
	    //��ը������ͼƬ,���һ��ը��
	    Image ima1=null;
	    Image ima2=null;
	    Image ima3=null;
	    
	    
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
				HighCool hc=new HighCool((i+1)*70,10);
			    //hc.setColor(1);
				//���뵽highcool
				hc.setDirect(2);
				// ��MyPanel�ĵ���̹��ϲ�������ing�������˵�̹��
				hc.setHcs(highcool);
				//�������˵�̹��
				Thread t=new Thread(hc);
				t.start();
				//�����˵�̹�˸ɸɴ�����ʱ��������һ���ӵ�,���ӵ����˵���
				Shoot hcs=new Shoot(hc.x+10,hc.y+30,2);
				hc.hcShoot.add(hcs);
				//��Ҫ�������˵��ӵ�
				Thread t2=new Thread(hcs);
				t2.start();
				highcool.add(hc);
			}
			
			//��ʼ����ըͼƬ
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
		
		//����������̹�˵���Ϣ,
		public void showInfo(Graphics g)
		{
			//����̹����ʾ��Ϣ����̹�˲�����ս������ʾ������
			//�ʻ����˻�ʣ�µ�̹����
			this.drawTank(70, 350, g, 0, 1);
			g.setColor(Color.black);
			g.drawString(Recorder.getEnNum()+"", 100, 370);
			//�ٻ����һ�ʣ�µ�̹������
			this.drawTank(130, 350, g, 0, 0);
			g.setColor(Color.black);
			g.drawString(Recorder.getMyLife()+"", 160, 370);	
			//������ҵ��ܳɼ����Ȼ�̹��
			g.setColor(Color.BLACK);
			Font f=new Font("����",Font.BOLD,20);
			g.setFont(f);
			g.drawString("all score", 420, 30);
			this.drawTank(420, 60, g, 0, 1);
			//�ٻ����������˵�����
			g.setColor(Color.BLACK);
			g.drawString(Recorder.getDieEnNum()+"", 470, 80);
			
		}
		
	//��дpaint����,�����Լ���̹��
		public void paint(Graphics g)
		{
			super.paint(g);
			//�趨һ��̹�˵Ļ�����Լ���ɫ
			g.fillRect(0, 0, 400, 300);
			//��������̹�˵���ʾ��Ϣ
		    this.showInfo(g);
			
			//�����Լ���̹��
			if(masa.isLive)
			{
			this.drawTank(masa.getX(),masa.getY(),g,masa.getDirect(),0);
			}
			//��ss��ȡ��ÿ���ӵ���������
			for(int i=0;i<masa.ss.size();i++)
			{
				Shoot myShoot=masa.ss.get(i);
			
			if(myShoot!=null&myShoot.isLive==true)
			{
			  g.draw3DRect(myShoot.x, myShoot.y, 1,1, false);
			}
			if(myShoot.isLive==false)
			{
				//ɾ���ӵ�
				masa.ss.remove(myShoot);
			}
			}
			
			//����ը��
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
				//֮����b������ֵ��С
				b.lifeDown();
				//�������ֵΪ0�����ը����bombs��ȥ��
				if(b.life==0)
				{
					bombs.remove(b);
				}
			}
			//�������˵�̹��
			for(int i=0;i<highcool.size();i++)
			{
				HighCool h=highcool.get(i);
				if(h.isLive)
				{
				this.drawTank(h.getX(), h.getY(), g, h.direct, 1);
			      //�ٻ��������˵��ӵ�
				for(int j=0;j<h.hcShoot.size();j++)
				{
					//ȡ���ӵ�
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
		//�ж��ҵ��ӵ����е��˵�̹��
		public void hitEnemyTank()
		{
			//�ж��Ƿ���е��˵�̹��
		     for(int i=0;i<masa.ss.size();i++)
		     {
		    	 //ȡ���ӵ�
		    	 Shoot myShoot=masa.ss.get(i);
		    	 //�ж��ӵ��Ƿ���Ч
		    	 if(myShoot.isLive)
		    	 {
		    		 for(int j=0;j<highcool.size();j++)
		    		 {
		    			 HighCool h=highcool.get(j);
		    			 if(h.isLive)
		    			 {
		    				 this.hitTank(myShoot, h);
		    				 if(h.isLive==false)
		    				 {
		    				//�������true˵�����е���
		    				//���ٵ��ˣ��������ҵļ�¼
		    				 Recorder.reduceEnNum();
		    				 Recorder.addDieEnNum();
		    				 }
		    			 }
		    		 }
		    		 
		    	 }
		     }
			
		}
		//���˵��ӵ��Ƿ�����ҵ�̹��
		public void hitMe()
		{
			//ȡ�����˵�һ��̹��
			for(int i=0;i<this.highcool.size();i++)
			{
				
			HighCool h=highcool.get(i);
			//ȡ��ÿһ���ӵ�
			for(int j=0;j<h.hcShoot.size();j++)
			{
				Shoot s=h.hcShoot.get(j);
				if(masa.isLive)
				{
				this.hitTank(s,masa);
				if(masa.isLive==false)
				{
				Recorder.reducemyNum();
				}
				}
			}
			}
		}
		
		
		//дһ�������ж�һ���ӵ��Ƿ���е�̹��
		public void hitTank(Shoot s,Tank h)
		{
			
			//�ж�̹�˵ķ���
			switch(h.direct)
			{ 
			//������˵�̹�����ϻ�������
			case 0:
			case 2:
				if(s.x>h.x&&s.x<h.x+20&&s.y>h.y&&s.y<h.y+30)
				{
					
					//�ӵ�������̹������
					s.isLive=false;
					h.isLive=false;
					//Recorder.reduceEnNum();
					//��̹�˱�����ʱ������ը��ͼƬ
					Bomb b=new Bomb(h.x,h.y);
					bombs.add(b);
				}
			     break;
			case 1:
			case 3:
				if(s.x>h.x&&s.x<h.x+30&&s.y>h.y&&s.y<h.y+20)
				{
					//�ӵ�������̹������
					s.isLive=false;
					h.isLive=false;
					//Recorder.reduceEnNum();
					//��̹�˱�����ʱ������ը��ͼƬ
					Bomb b=new Bomb(h.x,h.y);
					bombs.add(b);
					
				}
				break;
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
					g.fill3DRect(x+15, y, 5, 30,false);
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
					//����
					if(this.masa.ss.size()<=4)
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
				//���ú����ж��Ƿ���е��˵�̹��
				this.hitEnemyTank();
				//�������ж��ǵ��˵�̹���Ƿ�����ҵ�tank
				this.hitMe();
				
				
			    
				//�ػ�
				this.repaint();
				
			}
		}
	}
			
}

