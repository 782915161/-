package cn.tedu.shoot;
import java.awt.image.BufferedImage;
public class Bullet extends	FlyingObject{
	private static BufferedImage image;
	static {
		image=loadImage("bullet.png ");
	}
	private int speed;	//控制移动速度的
	public Bullet(int x,int y){
		super(8,14,x,y);
		speed=3;
	}
	
	public void step() { //小敌机的Y坐标移动
		y-=speed;   //子弹向上
	}
	//重写获取图片getImage()
	public BufferedImage getImage() {
		if(isLife()) {
			return image;
		}else if(isDead()) {
			state=REMOVE;
		}
		return null;
	}
	//敌人的越界检查
	public boolean outOfBounds() {
		return this.y<=-this.height;
	}
}
