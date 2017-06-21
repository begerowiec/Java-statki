package strzelanka;

public class Cloud extends Actor {
	
	protected int vx,mspeed,mpx,mpy;
	protected static final double FIRING_FREQUENCY = 0.02;

	private Bullet Bullet;


	public Cloud(Stage stage) {
	super(stage);
	setSpriteNames( new String[] {"cloud1.gif","cloud2.gif"});
	setFrameSpeed(25);
	}


	public void act() {
	super.act();
	x+=vx;
	if (x < 0 || x > Stage.SZEROKOSC-50){
	
		if (Math.random()>0.3)
		{
		remove();		
		}
		else
		{
		vx = -vx;	
		}
	
	}

    Bullet = new Bullet(stage);
	if (Math.random()<FIRING_FREQUENCY){
		Bullet.fire(x,y,type);
	}

	}


	public int getVx() { return vx; }
	public void setVx(int i) {vx = i; }

	public void collision(Actor a) {
		if (a instanceof Bullet) //|| a instanceof Bomb)
		{	
		remove();
		a.remove();
		stage.getPlayer().addScore(60);
		stage.getSoundCache().playSound("health1.wav");
			if (stage.getPlayer().getShields()<=160)
			{	
				stage.getPlayer().addShields(40);
			}
		}
	}

	public void spawn() {
		Cloud m = new Cloud(stage);
		//mpx = Math.abs((int)(Math.random()*Stage.SZEROKOSC-100));
		mpx = 0;
		m.setX(mpx);
		//mpy = Math.abs((int)(Math.random()*Stage.WYSOKOSC_GRY/2));
		mpy = 0;
		m.setY(mpy);
		mspeed = Math.abs((int)(Math.random()*10-10));
		if (mspeed==0){mspeed=1;}
		m.setVx(mspeed);
		m.type = 3;
		stage.addActor(m);
	}




}
