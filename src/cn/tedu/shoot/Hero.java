package cn.tedu.shoot;
import java.awt.image.BufferedImage;
//Ӣ�ۻ�
public class Hero extends FlyingObject{
	private static BufferedImage[ ] images;
	static {

		images=new BufferedImage[2];
		images[0]= loadImage("hero0.png");
		images[1]= loadImage("hero1.png");
	}
	private int life;		//��
	private int doubleFire;	//����
	//���췽��
	public Hero(){
		super(97,124,140,400);
		life=3;
		doubleFire=0;		
	}
	public void moveTo(int x ,int y) {//Ӣ�ۻ�������궯, ��������ƶ�����X,y��
		this.x=x-this.width/2;
		this.y=y-this.height/2;
	}
	public void step() { //С�л���Y�����ƶ�
		
	}
	int index=0;
	public BufferedImage getImage() {
		if(isLife()) {
			return images[index++%images.length];	//������������+1
		}
		return null;
	}
	/*?
	 * index=0;											0/2=0%0
	 * 10m ����images[0]  index=1;		1/2=0%1
	 * 20m ����images[1]	 index=2;			2/2=1%0
	 * 30m ����images[0] index=3;			3/2=1%1
	 * 40m ����images[1] index=4;			4/2=2%0
	 * 50m ����--------[0]	index=5;			5/2=2%1
	 * 60m ����--------[1] index=6;			6/2=3%0
	 * -------
	 * */
	//Ӣ�ۻ������ӵ�
	public Bullet[] shoot() {
		int xStep=this.width/4;  //1/4Ӣ�ۻ��Ŀ�
		int yStep=20;	//�̶�20
		if(doubleFire>0) {//˫
			Bullet[] bs=new Bullet[2];
			bs[0] = new Bullet (this.x+1*xStep,y-yStep); //Ӣ�ۻ���
			bs[1] = new Bullet (this.x+3*xStep,y-yStep); 
			doubleFire-=2;//����һ��˫������,�����ֵ��-2
			return bs;
		}else {//��
			Bullet[] bs=new Bullet[1];
			bs[0] = new Bullet (this.x+2*xStep,y-yStep); 
			return bs;
		}
	}
	//
	public void	addLife() {
		life++;
	}
	//��ȡ����ֵ
	public int getLife() {
		return life;	//��������
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
