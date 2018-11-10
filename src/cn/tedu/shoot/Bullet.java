package cn.tedu.shoot;
import java.awt.image.BufferedImage;
public class Bullet extends	FlyingObject{
	private static BufferedImage image;
	static {
		image=loadImage("bullet.png ");
	}
	private int speed;	//�����ƶ��ٶȵ�
	public Bullet(int x,int y){
		super(8,14,x,y);
		speed=3;
	}
	
	public void step() { //С�л���Y�����ƶ�
		y-=speed;   //�ӵ�����
	}
	//��д��ȡͼƬgetImage()
	public BufferedImage getImage() {
		if(isLife()) {
			return image;
		}else if(isDead()) {
			state=REMOVE;
		}
		return null;
	}
	//���˵�Խ����
	public boolean outOfBounds() {
		return this.y<=-this.height;
	}
}
