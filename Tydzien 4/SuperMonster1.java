package strzelanka;

public class SuperMonster1 extends Actor {
	
	protected int vx,mspeed,mpx,mpy;
	protected static final double FIRING_FREQUENCY = 0.1;

	private Laser laser;


	public SuperMonster1(Stage stage) {
	super(stage);
	setSpriteNames( new String[] {"smonster3.gif","smonster4.gif","smonster5.gif","smonster6.gif"});
	setFrameSpeed(25);
	}


	public void act() {
	super.act();
	x+=vx;
	if (x < 0 || x > Stage.SZEROKOSC-50){vx = -vx;}
	
	y++;
	
	if(stage.WYSOKOSC_GRY < y){ 
	remove();
	}

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
			if (!stage.getPlayer().getSzefexist()){	
			stage.getPlayer().addScore(60);
			stage.getPlayer().setLevel();
			stage.getSoundCache().playSound("monster_kill2.wav");
			}
		}
	}

	public void spawn() {
		SuperMonster1 m = new SuperMonster1(stage);
		//mpx = Math.abs((int)(Math.random()*Stage.SZEROKOSC-100));
		
		if(Math.random()<0.5)
		{	
		mpx = 0;
		}
		else
		{
		mpx = Stage.SZEROKOSC-51;	
		}
	
		m.setX(mpx);
		mpy = Math.abs((int)(Math.random()*Stage.WYSOKOSC_GRY/2));
		m.setY(mpy);
		mspeed = Math.abs((int)(Math.random()*10-10));
		if (mspeed==0){mspeed=1;}
		m.setVx(mspeed);
		m.type = 2;
		stage.addActor(m);
		stage.getSoundCache().playSound("monster3.wav");
	}




}
