package cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
//天空
public class Sky extends	FlyingObject{
	private static BufferedImage image;
	static {

		image=loadImage("background.png");
	}
	private int speed;	//控制移动速度的
	private int y1;	//第二张图的坐标
	public Sky(){
		super(World.WIDTH,World.HEIGHT,0,0);
		speed=1;
		y1=-World.HEIGHT;	
	}
	public void step() { //小敌机的Y坐标移动
		y+=speed;//向下
		y1+=speed;
		if(y>World.HEIGHT) {//当y>=窗口的高
			y=-World.HEIGHT;//修改y的值 
		}
		if(y1>World.HEIGHT) {
			y1=-World.HEIGHT;
		}
	
	}
	
	//重写获取图片getImage()
	public BufferedImage getImage() {
		return image;
	}
	//开始画对象 g:画笔
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);			
		g.drawImage(getImage(),x,y1,null);
	}
}
