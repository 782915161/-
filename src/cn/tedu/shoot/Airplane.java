package cn.tedu.shoot;
import java.util.Random;
import java.awt.image.BufferedImage;
public class Airplane extends	FlyingObject implements Enemy{
	private static BufferedImage[ ] images;
	static {
		images=new BufferedImage[5];
		for(int i=0;i<images.length;i++) {
			images[i]= loadImage("airplane"+i+".png");
		}
	}
	private int speed;	//控制移动速度的
	public Airplane(){
		super(49,36);
		speed =2;
	}
	public void step() { //小敌机的Y坐标移动
		y+=speed;//   Y+向下
		
	}
	int index=1;
	public BufferedImage getImage() {
		if(isLife()) {
			return images[0];
		}else if(isDead()){
			BufferedImage img = images[index++];
			if(index==images.length) {
				state=REMOVE;
			}
			return img;
		}
		return null;
	}
	/*?
	 * index =1;
	 * img=images[1]   条件不满足if不执行if	直接返回return img=(images[1])	 执行[index++]=2
	 * img=images[2]	条件不满足if不执行if	直接返回return img=(images[2])	 执行[index++]=3
	 * img=images[3]	条件不满足if不执行if	直接返回return img=(images[3])	 执行[index++]=4
	 * img=images[4]	条件不满足if不执行if	直接返回return img=(images[4])	 执行[index++]=5
	 * img=images[5]	条件满足if					执行state=REMOVE			判断条件不符合LIFE-DEAD
	 * 返回执行return null;  //删除图片
	 * 
	 * */
	public int getScore() {
		return 1;
	}
}
