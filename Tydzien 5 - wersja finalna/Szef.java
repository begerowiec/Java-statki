package strzelanka;

public class Szef extends Actor {
	
	protected int vx,mspeed,mpx,mpy,szef;
	protected static final double FIRING_FREQUENCY = 0.1;

	private Laser laser;


	public Szef(Stage stage) {
	super(stage);
	
	        switch (stage.getPlayer().getLevel()) {
	            case 5:  setSpriteNames( new String[] {"szef1l5.gif","szef2l5.gif"});	        	
	            break;
	            case 10:  setSpriteNames( new String[] {"szef1l10.gif","szef2l10.gif"});	        	
	            break;
	            case 15:  setSpriteNames( new String[] {"szef1l15.gif","szef2l15.gif"});	        	
	            break;        	
	            case 20:  setSpriteNames( new String[] {"szef1l20.gif","szef2l20.gif"});        	
	            break;
	            case 25:  setSpriteNames( new String[] {"szef1l25.gif","szef2l25.gif"});        	
	            break;
	            default: setSpriteNames( new String[] {"szef1l5.gif","szef2l5.gif"});        	
	            break;	            
	        }
		
		
	setFrameSpeed(25);
	}


	public void act() {
	super.act();
	
	x+=vx;
	
	if (x < (Stage.SZEROKOSC/4) || x > (Stage.SZEROKOSC/2)+(Stage.SZEROKOSC/4)){vx = -vx;}
	

	laser = new Laser(stage);
	if (Math.random()<FIRING_FREQUENCY){
		laser.fire(x,y,type);
		}
	if (Math.random()<FIRING_FREQUENCY){
		laser.fire(x+50,y,type);
		}
	if (Math.random()<FIRING_FREQUENCY){
		laser.fire(x+100,y,type);
		}
	}


	public int getVx() { return vx; }
	public void setVx(int i) {vx = i; }

	public void collision(Actor a) {
		if (a instanceof Bullet) {		
		a.remove();	
		stage.getPlayer().setSzef(-1);

		if (stage.getPlayer().getSzef()<=0){	
				remove();
				stage.getPlayer().addScore(800);
				stage.getPlayer().setSzef(5*stage.getPlayer().getLevel());
				stage.getPlayer().setLevels();
				stage.getPlayer().setSzefexist(false);  
				stage.getSoundCache().playSound("monster_kill1.wav");
			}
		}
		}

	public void spawn() {
		Szef m = new Szef(stage);
		//mpx = Math.abs((int)(Math.random()*Stage.SZEROKOSC-100));
		
		mpx = Stage.SZEROKOSC/2;	
		m.setX(mpx);
		mpy = Stage.WYSOKOSC_GRY/4;
		m.setY(mpy);
		mspeed = 5;
		m.setVx(mspeed);
		m.type = 20;
		stage.addActor(m);
		stage.getSoundCache().playSound("monster1.wav");
	}




}
