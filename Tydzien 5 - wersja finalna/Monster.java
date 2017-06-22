package strzelanka;


public class Monster extends Actor {
protected int vx,mspeed,mpx,mpy;
protected static final double FIRING_FREQUENCY = 0.01;

private Laser laser;


public Monster(Stage stage) {
super(stage);
//setSpriteName("potworek1.gif");
setSpriteNames( new String[] {"potworek1.gif","potworek2.gif"});
setFrameSpeed(25);
}


public void act() {
super.act();
x+=vx;
if (x < 0 || x > Stage.SZEROKOSC-50){vx = -vx;}

laser = new Laser(stage);
if (Math.random()<FIRING_FREQUENCY){
	laser.fire(x,y,type);
	}

}


public int getVx() { return vx; }
public void setVx(int i) {vx = i; }

public void collision(Actor a) {
	if (a instanceof Bullet) //|| a instanceof Bomb)
	{	
	remove();
	a.remove();
	stage.getSoundCache().playSound("monster_kill.wav");
	spawn();
	if (!stage.getPlayer().getSzefexist()){
		stage.getPlayer().addScore(20);
		stage.getPlayer().setLevel();}
	}
}

public void spawn() {
	Monster m = new Monster(stage);
	mpx = Math.abs((int)(Math.random()*Stage.SZEROKOSC-100));
	m.setX(mpx);
	mpy = Math.abs((int)(Math.random()*Stage.WYSOKOSC_GRY/2));
	m.setY(mpy);
	mspeed = Math.abs((int)(Math.random()*20-10));
	if (mspeed==0){mspeed=1;}
	m.setVx(mspeed);
	m.type = 1;
	stage.addActor(m);
	
	
}




}



