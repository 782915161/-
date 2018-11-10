package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.Graphics;

//父类
public abstract class FlyingObject {
	public static final int  LIFE = 0;//活着
	public static final int DEAD=1;//死了.图片变化
	public static final int REMOVE=2;//删除
	protected int state=LIFE;

	protected int width;
	protected int height;
	protected int x;
	protected int y;
	//专门给天空,子弹,英雄机的构造
	public FlyingObject(int width,int height,int x,int y){
		this.width=width;
		this.height=height;
		this.x=x;
		this.y=y;	 
	}
	//专门给小飞机, 大飞机,小蜜蜂的构造
	public FlyingObject(int width,int height){
		this.width=width; 
		this.height=height;
		Random rand= new Random();
		x = rand.nextInt(World.WIDTH-this.width);
		y=-this.height;
	}
	public abstract void step();//小敌机的Y坐标移动
	
	//读取图片
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img= ImageIO.read(FlyingObject.class.getResource(fileName));//同包下读取图片
			return img;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();			
		}
	}
	//获取图片//抽象方法体//结构//抽象不加{}
	public abstract BufferedImage getImage();
	//判断是否活着
	public boolean isLife() {
		return state==LIFE;
	}
	//判断是否死了
	public boolean isDead() {
		return state==DEAD;
	}
	//判断是否删除
	public boolean isRemove() {
		return state==REMOVE;
	}
	//开始画对象 g:画笔
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
	}
	//敌人的越界检查
	public boolean outOfBounds() {
		return this.y>=World.HEIGHT;//敌人的y大于窗口的高.越界
	}
	//检测碰撞	this :敌人(FlyingObject)		other:子弹/英雄机
	public boolean hit(FlyingObject other) {
		int x1 =this.x-other.width;	//
		int x2=this.x+this.width;
		int y1=this.y-other.height;
		int y2=this.y+this.height;
		int x=other.x;
		int y=other.y;
		        return x>=x1 && x<=x2	//x在x1与x2之间
		        			&&								//并且
		        			y>=y1 &&y<=y2;//y在y1与y2z之间
	}
	
	public void goDead() {
		state=DEAD;
	}

}
