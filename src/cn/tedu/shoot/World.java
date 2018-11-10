package cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;			//������
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
//������
public class World extends JPanel{
	public static final int WIDTH =400;   //���ڵĿ�
	public static final int HEIGHT=700;	//���ڵĸ�
	
	public static final int START=0;//����״̬
	public static final int RUNNING=1;//����״̬
	public static final int PAUSE=2;//��ͣ״̬
	public static final int GAME_OVER=3;//��Ϸ����״̬
	private int state = START;	//��ǰ״̬Ϊ����״̬
	
	private static BufferedImage start;	//����ͼ
	private static BufferedImage pause;	//��ͣͼ
	private static BufferedImage gameover;	//��Ϸ����ͼ

	static {//��ʼ����̬ͼƬ
		start =FlyingObject.loadImage("start.png");
		pause=FlyingObject.loadImage("pause.png");
		gameover =FlyingObject.loadImage("gameover.png");
	}
	
	private Sky	sky=new Sky();//������һ�� Sky����
	private Hero hero=new Hero();
	private FlyingObject[] enemies= {

	};
	private Bullet[] bullets= {

	};
	public FlyingObject nextOne() {
		Random rand=new Random();
		int type=rand.nextInt(20);//�����0-19
		if(type<5) {
			return new Bee();
		}else if(type<12) {
			return new Airplane(); 
		}else {
			return new BigAirplane();
		}
	}
	int enterIndex=0;
	public void enterAction() { 
		enterIndex++;//ÿ10������1
		if(enterIndex%40==0) {
			FlyingObject obj=nextOne();//������˶���
			enemies= Arrays.copyOf(enemies, enemies.length+1);
			enemies[enemies.length-1]=obj;
		}
	}
	int shootIndex=0;//�ӵ�����
	public void shootAction() {	//�ӵ�
		shootIndex++;
		if(shootIndex%30==0) {
			Bullet[] bs=hero.shoot();//��ȡ�ӵ�����
			bullets =Arrays.copyOf(bullets, bullets.length+bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);//��������.���ӵ�����ĳ������ݵ�
		}
	}
	//��������ƶ�
	public void stepAction() {
		sky.step();
		for(int i=0;i<enemies.length;i++) {
			enemies[i].step();
		}
		for(int i=0;i<bullets.length;i++) {
			bullets[i].step();
		}
	}
	public void outOfBoundsAction() {	//ɾ��Խ��ķ���
		int index= 0;
		FlyingObject [] enemyLives=new FlyingObject[enemies.length];
		for(int i=0;i<enemies.length;i++) {
			FlyingObject f= enemies[i];
			if(!f.outOfBounds()&&!f.isRemove()) {//
				enemyLives[index] =f;
				index++;			
			}
		}
		enemies =Arrays.copyOf(enemyLives, index);	
		System.out.println(index);
	index=0;
	Bullet[ ] bulletsLife =new Bullet[bullets.length];
	for(int i=0;i<bullets.length;i++) {
		Bullet g=bullets[i];
		if(!g.outOfBounds()&&!g.isRemove()) {
			bulletsLife[index]=g;
			index++;
		}
	}
	bullets=Arrays.copyOf(bulletsLife, index);
	}
	//�ӵ��͵��˵���ײ
	int score=0;	//�Ʒ���
	public void bulletBangAction() {		
		for(int i=0;i<bullets.length;i++) {
			Bullet b =bullets[i];
			for(int j=0;j<enemies.length;j++) {
				FlyingObject c=enemies[j];
				if(b.isLife()&&c.isLife()&&c.hit(b)) {
					c.goDead();
					b.goDead();
					if(c instanceof Enemy) {
						Enemy e= (Enemy)c;				//��ȡ�÷ֵĽӿ�
						score+=e.getScore();
					}
					if(c instanceof Award) {
						Award a=(Award)c;
						int type =a.getAwardType();
						switch (type) {
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();
							break;
						case Award.LIFE:
							hero.addLife();
							break;
						}
					}				
				}
			}
		}
	}
	//Ӣ�ۻ������ײ
	public void heroBangAction() {
		for(int i=0;i<enemies.length;i++) {
			FlyingObject f=enemies[i];
			if(f.isLife()&&hero.isLife()&&f.hit(hero)) {
				f.goDead();
				hero.clearDoubleFire();
				hero.subtractLife();
			}
		}
	}
	//������
	public void checkGaneOverAction() {
		if(hero.getLife()<=0) {
			 state=GAME_OVER;//��ǰ״̬��Ϊ��Ϸ����			 
		}
	}
	public void action() {	//���Դ���
		MouseAdapter l=new MouseAdapter() {//��������
			//��дmouseMoved()����ƶ�
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) {
					int x=e.getX();
					int y=e.getY();
					hero.moveTo(x, y);//��ȡӢ�ۻ���ķ���
				}
			}
		//������¼�	��дmouseClicked
			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case START:
					state=RUNNING;
					break;
				 case GAME_OVER:
					 score =0;
					// sky =new Sky();
					 hero=new Hero();
					 enemies =new FlyingObject[0];
					 bullets =new Bullet[0];
					 state=START;
					break;
				}
			}
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING) {
					state=PAUSE;
				}
			}
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE) {
					state=RUNNING;
				}
			}
		};
		this.addMouseListener(l);//�������Ĳ����¼�
		this.addMouseMotionListener(l);//������껬�����¼�
		Timer timer=new Timer();//��ʱ������
		int interval=10;//�Ժ���Ϊ��λ
		timer.schedule(new TimerTask() {
			public void run() {//��ʱ�ɵ���
				if(state==RUNNING) {
					enterAction();//����(С�л�,��л�,С�۷�)
					shootAction();//�ӵ��볡
					stepAction();//�������ƶ�
					outOfBoundsAction();//Խ��ɾ��
					bulletBangAction();//�ӵ��͵���ײ
					heroBangAction();//Ӣ�ۻ�����˵���ײ
					checkGaneOverAction();  //��Ϸ�������
				}

				repaint();//�ػ�(���µ���paint();)
			}
		},interval,interval);//��ʱ�ƻ�
		
	}
	//����paint() ��
	public void paint(Graphics g) {

		sky.paintObject(g);//�����
		hero.paintObject(g);//��Ӣ�ۻ�
		
		for(int i=0;i<enemies.length;i++) {//���л�
			enemies[i].paintObject(g);
		}
		for(int i=0;i<bullets.length;i++) {//���������ӵ�
			bullets[i].paintObject(g);//���ӵ�
		}
		
		g.drawString("SCORE:" +score, 10,20);		//����
		g.drawString("LIFE:" +hero.getLife(), 10,45);	//����
		
		switch(state) {
		case START:
			g.drawImage(start, 0,0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0,0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0,0, null);
			break;
		}
	} 
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); 
		
		world.action();
	}

}
