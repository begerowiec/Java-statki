package strzelanka;

public class Laser extends Actor {
protected static final int BULLET_SPEED=10;


public Laser(Stage stage) {
super(stage);
setSpriteNames( new String[]{"fire1.gif","fire2.gif"});
setFrameSpeed(10);

}
public void act() {

x=x+target;


if (target==0)
{
	if (type==2)
	{
		target = (stage.getPlayer().x)/80;
	}
	else
	{
		target = 1; 
	}
}

y+=BULLET_SPEED + stage.getPlayer().getLevel();


if (y > Stage.WYSOKOSC_GRY){remove();}

if (x < 0 || x > Stage.SZEROKOSC)
	vx = -vx;
	super.act();
	
if (x > Stage.SZEROKOSC){
	target = -target;
}


}
public void fire(int x,int y,int type) {
	Laser m = new Laser(stage);
	m.setX(x+11); ///korekta aby strzelanie by³o na œrodku rysunku
	m.setY(y+40);
	m.type = type;
	stage.addActor(m);
	
	stage.getSoundCache().playSound("fire2.wav");
}


}

