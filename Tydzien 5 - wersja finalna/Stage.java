package strzelanka;

import java.awt.image.ImageObserver;
import java.awt.Toolkit;
import java.awt.Dimension;

public interface Stage extends ImageObserver {
Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

public static final int SZEROKOSC = d.width;
public static final int WYSOKOSC =  d.height;
public static final int SZYBKOSC = 10;
public static final int WYSOKOSC_GRY = WYSOKOSC-50;
public SpriteCache getSpriteCache();
public void addActor(Actor a);
public Player getPlayer();
public SoundCache getSoundCache();
public void gameOver();

}