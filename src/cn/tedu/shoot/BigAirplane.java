package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BigAirplane extends	FlyingObject implements Enemy{
	private static BufferedImage[ ] images;
	static {
		images=new BufferedImage[5];
		for(int i=0;i<images.length;i++) {
			images[i]= loadImage("bigplane"+i+".png");
		}
	}
	private int speed;	//移动速度
	public  BigAirplane(){
		super(69,99);
		speed =2;
	}
	public void step() { //大敌机的Y坐标移动
		y+=speed;//向下
	}
	int index=1;
	public BufferedImage getImage() {
		if(isLife()) {
			return images[0];
		}else if(isDead()) {
			BufferedImage img=images[index++];
			if(index==images.length) {
				state=REMOVE;
			}
			return img;
		}
		return null;
	}
	public int getScore() {
		return 3;
	}
}
