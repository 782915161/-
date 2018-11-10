package cn.tedu.shoot;
import java.awt.image.BufferedImage;
//英雄机
public class Hero extends FlyingObject{
	private static BufferedImage[ ] images;
	static {

		images=new BufferedImage[2];
		images[0]= loadImage("hero0.png");
		images[1]= loadImage("hero1.png");
	}
	private int life;		//命
	private int doubleFire;	//火力
	//构造方法
	public Hero(){
		super(97,124,140,400);
		life=3;
		doubleFire=0;		
	}
	public void moveTo(int x ,int y) {//英雄机随着鼠标动, 返回鼠标移动参数X,y轴
		this.x=x-this.width/2;
		this.y=y-this.height/2;
	}
	public void step() { //小敌机的Y坐标移动
		
	}
	int index=0;
	public BufferedImage getImage() {
		if(isLife()) {
			return images[index++%images.length];	//先运算再自增+1
		}
		return null;
	}
	/*?
	 * index=0;											0/2=0%0
	 * 10m 返回images[0]  index=1;		1/2=0%1
	 * 20m 返回images[1]	 index=2;			2/2=1%0
	 * 30m 返回images[0] index=3;			3/2=1%1
	 * 40m 返回images[1] index=4;			4/2=2%0
	 * 50m 返回--------[0]	index=5;			5/2=2%1
	 * 60m 返回--------[1] index=6;			6/2=3%0
	 * -------
	 * */
	//英雄机发射子弹
	public Bullet[] shoot() {
		int xStep=this.width/4;  //1/4英雄机的宽
		int yStep=20;	//固定20
		if(doubleFire>0) {//双
			Bullet[] bs=new Bullet[2];
			bs[0] = new Bullet (this.x+1*xStep,y-yStep); //英雄机的
			bs[1] = new Bullet (this.x+3*xStep,y-yStep); 
			doubleFire-=2;//发射一次双倍火力,则火力值减-2
			return bs;
		}else {//单
			Bullet[] bs=new Bullet[1];
			bs[0] = new Bullet (this.x+2*xStep,y-yStep); 
			return bs;
		}
	}
	//
	public void	addLife() {
		life++;
	}
	//获取命的值
	public int getLife() {
		return life;	//返回命数
	}
	public void subtractLife() {
		life--;
	}
	public void addDoubleFire() {
		doubleFire+=40;
	}
	public void clearDoubleFire() {
		doubleFire=0;
	}
}
