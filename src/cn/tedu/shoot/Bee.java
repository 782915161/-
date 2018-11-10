package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;

//小蜜蜂
public class Bee extends	FlyingObject implements Award{
	private static BufferedImage[ ] images;
	static {

		images=new BufferedImage[5];
		for(int i=0;i<images.length;i++) {
			images[i]= loadImage("bee"+i+".png");
		}
	}
	private int xSpeed;	//x移动速度
	private int ySpeed;	//Y移动速度
	private int awardType; //奖励类型
	public Bee(){
		super(60,50);
		xSpeed =1;
		ySpeed =2; 
		Random rand= new Random();
		awardType=rand.nextInt(2);//0-1之间的随机数
	}
	public void step() { //小敌机的Y坐标移动
		x+=xSpeed;	//向左向右
		y+=ySpeed;	
		if(x<=0||x>=World.WIDTH-this.width) {
			xSpeed*=-1; //切换条件
		}
	}
	int index=0;
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
	public int getAwardType() {
		return awardType;
	}
}
