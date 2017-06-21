package strzelanka;

public class Bullet extends Actor {
protected static final int BULLET_SPEED=7;
public Bullet(Stage stage) {
super(stage);
setSpriteNames( new String[] {"bullet.gif"});
}

public void act() {
	super.act();
	
	if (type==5)
	{	
		y-=BULLET_SPEED;
		if (y < 0)	
		remove();
	}
	else
	{
		y+=BULLET_SPEED;
		if (y>stage.WYSOKOSC_GRY)	
		remove();
	}
		
}

public void fire(int x,int y,int type) {
	
	Bullet b = new Bullet(stage);
	b.setX(x+11); ///korekta aby strzelanie by³o na œrodku rysunku
	b.setY(y + b.getHeight()+100);
	stage.addActor(b);
	
}


}
