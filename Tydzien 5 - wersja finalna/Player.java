package strzelanka;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Player extends Actor {
protected int vx;
protected int vy;
protected static final int PLAYER_SPEED = 4;
public static final int MAX_SHIELDS = 200;
public static final int MAX_BOMBS = 5;
private int score;
private int shields;
private int level = 1;
private int szef = 25;

private boolean up,down,left,right,szef_exist;
private int space = 0;


public Player(Stage stage) {
super(stage);
setSpriteNames( new String[] {"statek1.gif","statek2.gif"});
shields = MAX_SHIELDS;
}

public void act() {
	super.act();
if (left || right){
	if (x-vx < 0){x = 0;}
	if (x+vx > Stage.SZEROKOSC-50){x = Stage.SZEROKOSC-50;}	
	x+=vx;
	};

	
//y=0;

//if (y < 0 || y > Stage.WYSOKOSC-50)
//vy = -vy;

}

public int getVx() { return vx; }
public void setVx(int i) {vx = i; }
public int getVy() { return vy; }
public void setVy(int i) {vy = i; }

protected void updateSpeed() {
	vx=0;vy=0;
	
	//if (down && y < Stage.WYSOKOSC-100 ) vy = PLAYER_SPEED;
	//if (up && y > 0) vy = -PLAYER_SPEED;
	if (left && x > 0) vx = -PLAYER_SPEED;
	if (right && x < Stage.SZEROKOSC-50) vx = PLAYER_SPEED;

}

public void keyReleased(KeyEvent e) {
	switch (e.getKeyCode()) {
	//case KeyEvent.VK_DOWN : down = false; break;
	//case KeyEvent.VK_UP : up = false; break;
	case KeyEvent.VK_LEFT : left = false; break;
	case KeyEvent.VK_RIGHT : right = false; break;
	case KeyEvent.VK_SPACE : space = 0; break;
	}
	updateSpeed();
}
public void keyPressed(KeyEvent e) {
	switch (e.getKeyCode()) {
//	case KeyEvent.VK_UP : up = true; break;
	case KeyEvent.VK_LEFT : left = true; break;
	case KeyEvent.VK_RIGHT : right = true; break;
//	case KeyEvent.VK_DOWN : down = true;break;
	case KeyEvent.VK_SPACE : fire(); break;
	}
	updateSpeed();
}


//// strzelanie - nowy pocisk
public void fire() {
	if (space==0){
	space++;		
	Bullet b = new Bullet(stage);
	b.setX(x+11); ///korekta aby strzelanie by³o na œrodku rysunku
	b.setY(y - b.getHeight());
	b.type = 5;
	stage.addActor(b);
	stage.getSoundCache().playSound("fire1.wav");
	}
}

public int getScore() { return score; }
public void setScore(int i) { score = i; }
public int getShields() { return shields; }
public void setShields(int i) { shields = i; }

public int getLevel() { return level; }
public void setLevel() {if (score % 800 == 0){level++;}}  //// po ilu punktach zmieniæ poziom
public void setLevels() {level++;}  //// po ilu punktach zmieniæ poziom

public boolean getSzefexist() { return szef_exist; }
public void setSzefexist(boolean i) {szef_exist = i; }

public int getSzef() { return szef; }
public void setSzef(int i) { szef += i; }

public void addScore(int i) { score += i; }
public void addShields(int i) { shields += i; }



public void collision(Actor a) {
if (a instanceof Monster ) {
	a.remove();
	addScore(40);
	addShields(-40);
}
if (a instanceof SuperMonster1 ) {
	a.remove();
	addShields(-50);
}

if (a instanceof Laser ) {
	a.remove();
	addShields(-10);
	Shield m = new Shield(stage);
	m.spawn();
}
if (getShields() < 0){
	stage.gameOver();
	}
}

}

