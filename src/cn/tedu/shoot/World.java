package cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;			//监听器
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
//主窗口
public class World extends JPanel{
	public static final int WIDTH =400;   //窗口的宽
	public static final int HEIGHT=700;	//窗口的高
	
	public static final int START=0;//启动状态
	public static final int RUNNING=1;//运行状态
	public static final int PAUSE=2;//暂停状态
	public static final int GAME_OVER=3;//游戏结束状态
	private int state = START;	//当前状态为启动状态
	
	private static BufferedImage start;	//启动图
	private static BufferedImage pause;	//暂停图
	private static BufferedImage gameover;	//游戏结束图

	static {//初始化静态图片
		start =FlyingObject.loadImage("start.png");
		pause=FlyingObject.loadImage("pause.png");
		gameover =FlyingObject.loadImage("gameover.png");
	}
	
	private Sky	sky=new Sky();//声明了一个 Sky的类
	private Hero hero=new Hero();
	private FlyingObject[] enemies= {

	};
	private Bullet[] bullets= {

	};
	public FlyingObject nextOne() {
		Random rand=new Random();
		int type=rand.nextInt(20);//随机数0-19
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
		enterIndex++;//每10毫秒增1
		if(enterIndex%40==0) {
			FlyingObject obj=nextOne();//或许敌人对象
			enemies= Arrays.copyOf(enemies, enemies.length+1);
			enemies[enemies.length-1]=obj;
		}
	}
	int shootIndex=0;//子弹计数
	public void shootAction() {	//子弹
		shootIndex++;
		if(shootIndex%30==0) {
			Bullet[] bs=hero.shoot();//获取子弹对象
			bullets =Arrays.copyOf(bullets, bullets.length+bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);//扩容数组.把子弹对象的长度扩容到
		}
	}
	//飞行物的移动
	public void stepAction() {
		sky.step();
		for(int i=0;i<enemies.length;i++) {
			enemies[i].step();
		}
		for(int i=0;i<bullets.length;i++) {
			bullets[i].step();
		}
	}
	public void outOfBoundsAction() {	//删除越界的方法
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
	//子弹和敌人的碰撞
	int score=0;	//计分器
	public void bulletBangAction() {		
		for(int i=0;i<bullets.length;i++) {
			Bullet b =bullets[i];
			for(int j=0;j<enemies.length;j++) {
				FlyingObject c=enemies[j];
				if(b.isLife()&&c.isLife()&&c.hit(b)) {
					c.goDead();
					b.goDead();
					if(c instanceof Enemy) {
						Enemy e= (Enemy)c;				//获取得分的接口
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
	//英雄机与敌人撞
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
	//检查结束
	public void checkGaneOverAction() {
		if(hero.getLife()<=0) {
			 state=GAME_OVER;//当前状态改为游戏结束			 
		}
	}
	public void action() {	//测试代码
		MouseAdapter l=new MouseAdapter() {//监听对象
			//重写mouseMoved()鼠标移动
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) {
					int x=e.getX();
					int y=e.getY();
					hero.moveTo(x, y);//获取英雄机里的方法
				}
			}
		//鼠标点击事件	重写mouseClicked
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
		this.addMouseListener(l);//处理鼠标的操作事件
		this.addMouseMotionListener(l);//处理鼠标滑动的事件
		Timer timer=new Timer();//定时器对象
		int interval=10;//以毫秒为单位
		timer.schedule(new TimerTask() {
			public void run() {//定时干的事
				if(state==RUNNING) {
					enterAction();//敌人(小敌机,大敌机,小蜜蜂)
					shootAction();//子弹入场
					stepAction();//飞行物移动
					outOfBoundsAction();//越界删除
					bulletBangAction();//子弹和敌人撞
					heroBangAction();//英雄机与敌人的碰撞
					checkGaneOverAction();  //游戏结束检查
				}

				repaint();//重画(重新调用paint();)
			}
		},interval,interval);//定时计划
		
	}
	//重新paint() 画
	public void paint(Graphics g) {

		sky.paintObject(g);//画天空
		hero.paintObject(g);//画英雄机
		
		for(int i=0;i<enemies.length;i++) {//画敌机
			enemies[i].paintObject(g);
		}
		for(int i=0;i<bullets.length;i++) {//遍历所有子弹
			bullets[i].paintObject(g);//画子弹
		}
		
		g.drawString("SCORE:" +score, 10,20);		//画分
		g.drawString("LIFE:" +hero.getLife(), 10,45);	//画命
		
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
