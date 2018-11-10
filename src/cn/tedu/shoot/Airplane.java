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
	private int speed;	//�����ƶ��ٶȵ�
	public Airplane(){
		super(49,36);
		speed =2;
	}
	public void step() { //С�л���Y�����ƶ�
		y+=speed;//   Y+����
		
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
	 * img=images[1]   ����������if��ִ��if	ֱ�ӷ���return img=(images[1])	 ִ��[index++]=2
	 * img=images[2]	����������if��ִ��if	ֱ�ӷ���return img=(images[2])	 ִ��[index++]=3
	 * img=images[3]	����������if��ִ��if	ֱ�ӷ���return img=(images[3])	 ִ��[index++]=4
	 * img=images[4]	����������if��ִ��if	ֱ�ӷ���return img=(images[4])	 ִ��[index++]=5
	 * img=images[5]	��������if					ִ��state=REMOVE			�ж�����������LIFE-DEAD
	 * ����ִ��return null;  //ɾ��ͼƬ
	 * 
	 * */
	public int getScore() {
		return 1;
	}
}
