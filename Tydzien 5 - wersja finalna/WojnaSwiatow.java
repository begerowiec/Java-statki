package strzelanka;

import java.awt.Canvas;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.image.BufferedImage;


public class WojnaSwiatow extends Canvas implements Stage, KeyListener{
public void gameOver() { gameEnded = true;}
public long usedTime;
public BufferStrategy strategia;
private SpriteCache spriteCache;
private ArrayList actors;
private Player player;
private boolean gameEnded=false;
private SoundCache soundCache;
private int level,llevel = 1;

public Player getPlayer() { return player;}

public WojnaSwiatow() {
spriteCache = new SpriteCache();
soundCache = new SoundCache();
JFrame okno = new JFrame(".: Wojna Swiatow :.");
JPanel panel = (JPanel)okno.getContentPane();
setBounds(0,0,Stage.SZEROKOSC,Stage.WYSOKOSC);
panel.setPreferredSize(new Dimension(Stage.SZEROKOSC,Stage.WYSOKOSC));
panel.setLayout(null);
panel.add(this);
okno.setBounds(0,0,Stage.SZEROKOSC,Stage.WYSOKOSC);
okno.setVisible(true);

okno.addWindowListener( new WindowAdapter() {
public void windowClosing(WindowEvent e) {
System.exit(0);
}
});
okno.setResizable(false);
createBufferStrategy(2);
strategia = getBufferStrategy();
requestFocus();
addKeyListener(this);

}

public SoundCache getSoundCache() {
return soundCache;
}

public void addActor(Actor a) {
actors.add(a);
}

public void keyPressed(KeyEvent e) {
	
	switch (e.getKeyCode()) {
	case KeyEvent.VK_F1 :gameEnded=false;this.game();break;
	}
	
player.keyPressed(e);
}
public void keyReleased(KeyEvent e) {
player.keyReleased(e);
}
public void keyTyped(KeyEvent e) {}

public void initWorld() {
actors = new ArrayList();
	for (int i = 0; i < 15; i++){
		Monster m = new Monster(this);
		
		m.mpx = Math.abs((int)(Math.random()*Stage.SZEROKOSC-100));
		m.setX(m.mpx);
		m.setY( i*20 );
		m.mspeed = (int)(Math.abs(Math.random()*10-5));
		if (m.mspeed==0){m.mspeed=1;}{m.setVx(m.mspeed);}
		actors.add(m);
	}
player = new Player(this);
player.setX(Stage.SZEROKOSC/2);
player.setY(Stage.WYSOKOSC - 2*player.getHeight());
player.setVx(5);
player.type = 5;

}

public void paintWorld() {
	Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
	g.setColor(Color.black);
	g.fillRect(0,0,getWidth(),getHeight());
	for (int i = 0; i < actors.size(); i++) {
	Actor m = (Actor)actors.get(i);
	m.paint(g);
	}
	player.paint(g);
	paintStatus(g);
	strategia.show();
}

public void updateWorld() {

	int i = 0;
	while (i < actors.size()) {
		Actor m = (Actor)actors.get(i);
		if (m.isMarkedForRemoval()) {
		actors.remove(i);
		} else {
		m.act();
		i++;
		}
		
	}
	
		player.act();
		
		if (player.getLevel() % 5 == 0){
			    if (!player.getSzefexist())
			    {
				Szef m = new Szef(this);
				m.spawn();
				player.setSzefexist(true);
			    }		    
				    if (player.getLevel()>16){
				    	
							if (Math.random()<0.001){ 
							SuperMonster m = new SuperMonster(this);
							m.spawn();
							}
							if (Math.random()<0.001){ 
							SuperMonster1 m = new SuperMonster1(this);
							m.spawn();
							}
				    }			    	
		}	
		else
		{
			
			if (Math.random()<0.001){ 
			SuperMonster m = new SuperMonster(this);
			m.spawn();
			}
			if (Math.random()<0.001){ 
			SuperMonster1 m = new SuperMonster1(this);
			m.spawn();
			}
			if (Math.random()<0.001){ 
			Cloud m = new Cloud(this);
			m.spawn();
			}
		}

}

public SpriteCache getSpriteCache() {
return spriteCache;
}


public void checkCollisions() {
	Rectangle playerBounds = player.getBounds();
	for (int i = 0; i < actors.size(); i++) {
		Actor a1 = (Actor)actors.get(i);
		Rectangle r1 = a1.getBounds();
		if (r1.intersects(playerBounds)) {
			player.collision(a1);
			a1.collision(player);
		}
		for (int j = i+1; j < actors.size(); j++) {
			Actor a2 = (Actor)actors.get(j);
				if (a2 != null)
				{
					Rectangle r2 = a2.getBounds();
					if (r1.intersects(r2)) {
					a1.collision(a2);
					a2.collision(a1);				
				}
			}
		}
	}
}

public void game() {
	usedTime=1000;
	initWorld();
	while (isVisible() && !gameEnded) {

		long startTime = System.currentTimeMillis();
		updateWorld();
		checkCollisions();
		paintWorld();
		usedTime = System.currentTimeMillis()-startTime;
		try {
		Thread.sleep(20);
		} catch (InterruptedException e) {}
	}
	paintGameOver();
}

public void paintGameOver() {
Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
g.setColor(Color.white);
g.setFont(new Font("Arial",Font.BOLD,50));
g.drawString("GAME OVER",Stage.SZEROKOSC/2-50,Stage.WYSOKOSC/2);
strategia.show();
}    

public static void main(String[] args) {
WojnaSwiatow inv = new WojnaSwiatow();
inv.game();
}

public void paintShields(Graphics2D g) {
//g.setPaint(Color.red);
//g.fillRect(280,Stage.WYSOKOSC_GRY,Player.MAX_SHIELDS,30);
g.setPaint(Color.DARK_GRAY);
g.fillRect(390,Stage.WYSOKOSC_GRY+10,player.getShields(),10);
g.setFont(new Font("Arial",Font.PLAIN,18));
g.setPaint(Color.gray);
g.drawString("Shields:",300,Stage.WYSOKOSC_GRY+20);
}
public void paintScore(Graphics2D g) {
g.setFont(new Font("Arial",Font.PLAIN,18));
g.setPaint(Color.gray);
g.drawString("Score: "+player.getScore()+"",20,Stage.WYSOKOSC_GRY + 20);
}
public void paintfps(Graphics2D g) {
g.setFont( new Font("Arial",Font.BOLD,12));
g.setColor(Color.white);
if (usedTime > 0)
g.drawString(String.valueOf(1000/usedTime)+" fps",Stage.SZEROKOSC-50,Stage.WYSOKOSC_GRY);
else
g.drawString("--- fps",Stage.WIDTH-50,Stage.WYSOKOSC_GRY);
}
public void paintLevel(Graphics2D g) {
	g.setFont(new Font("Arial",Font.PLAIN,18));
	g.setPaint(Color.gray);
	g.drawString("Level: "+player.getLevel(),Stage.SZEROKOSC-150,Stage.WYSOKOSC_GRY + 20);
}

public void paintfLevel(Graphics2D g) {
	if (player.getLevel()!=level)
	{
		g.setColor(Color.darkGray);
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("LEVEL "+player.getLevel(),Stage.SZEROKOSC/2-50,Stage.WYSOKOSC/2);
		strategia.show();
		llevel++;
		if(llevel>100)
		{ 
			level = player.getLevel();
			llevel=0;
		}	
	}
}
public void paintStatus(Graphics2D g) {
paintScore(g);
paintShields(g);
paintLevel(g);
paintfps(g);
paintfLevel(g);
}


}