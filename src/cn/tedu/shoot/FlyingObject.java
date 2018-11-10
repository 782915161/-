package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.Graphics;

//����
public abstract class FlyingObject {
	public static final int  LIFE = 0;//����
	public static final int DEAD=1;//����.ͼƬ�仯
	public static final int REMOVE=2;//ɾ��
	protected int state=LIFE;

	protected int width;
	protected int height;
	protected int x;
	protected int y;
	//ר�Ÿ����,�ӵ�,Ӣ�ۻ��Ĺ���
	public FlyingObject(int width,int height,int x,int y){
		this.width=width;
		this.height=height;
		this.x=x;
		this.y=y;	 
	}
	//ר�Ÿ�С�ɻ�, ��ɻ�,С�۷�Ĺ���
	public FlyingObject(int width,int height){
		this.width=width; 
		this.height=height;
		Random rand= new Random();
		x = rand.nextInt(World.WIDTH-this.width);
		y=-this.height;
	}
	public abstract void step();//С�л���Y�����ƶ�
	
	//��ȡͼƬ
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img= ImageIO.read(FlyingObject.class.getResource(fileName));//ͬ���¶�ȡͼƬ
			return img;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();			
		}
	}
	//��ȡͼƬ//���󷽷���//�ṹ//���󲻼�{}
	public abstract BufferedImage getImage();
	//�ж��Ƿ����
	public boolean isLife() {
		return state==LIFE;
	}
	//�ж��Ƿ�����
	public boolean isDead() {
		return state==DEAD;
	}
	//�ж��Ƿ�ɾ��
	public boolean isRemove() {
		return state==REMOVE;
	}
	//��ʼ������ g:����
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
	}
	//���˵�Խ����
	public boolean outOfBounds() {
		return this.y>=World.HEIGHT;//���˵�y���ڴ��ڵĸ�.Խ��
	}
	//�����ײ	this :����(FlyingObject)		other:�ӵ�/Ӣ�ۻ�
	public boolean hit(FlyingObject other) {
		int x1 =this.x-other.width;	//
		int x2=this.x+this.width;
		int y1=this.y-other.height;
		int y2=this.y+this.height;
		int x=other.x;
		int y=other.y;
		        return x>=x1 && x<=x2	//x��x1��x2֮��
		        			&&								//����
		        			y>=y1 &&y<=y2;//y��y1��y2z֮��
	}
	
	public void goDead() {
		state=DEAD;
	}

}
