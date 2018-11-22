package com.Leo.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * <em>Hero explosion</em>
 * <p>When the bullet is fired onto the tank, it will have an explosion effect.
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 **/
public class BombHero {
	private int x, y;
	private boolean live = true;
	private BattleField battleField; 
	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Image[] imgs = { 
			tk.getImage(BombHero.class.getClassLoader().getResource(
					"com/Leo/Game/images/1.gif")),
			tk.getImage(BombHero.class.getClassLoader().getResource(
					"com/Leo/Game/images/2.gif")),
			tk.getImage(BombHero.class.getClassLoader().getResource(
					"com/Leo/Game/images/3.gif")),
			tk.getImage(BombHero.class.getClassLoader().getResource(
					"com/Leo/Game/images/4.gif")),
			tk.getImage(BombHero.class.getClassLoader().getResource(
					"com/Leo/Game/images/5.gif")),
			tk.getImage(BombHero.class.getClassLoader().getResource(
					"com/Leo/Game/images/6.gif")),
			tk.getImage(BombHero.class.getClassLoader().getResource(
					"com/Leo/Game/images/7.gif")),
			tk.getImage(BombHero.class.getClassLoader().getResource(
					"com/Leo/Game/images/8.gif")),
			tk.getImage(BombHero.class.getClassLoader().getResource(
					"com/Leo/Game/images/9.gif")),
			tk.getImage(BombHero.class.getClassLoader().getResource(
					"com/Leo/Game/images/10.gif")), };
	int step = 0;

	/**
	 * <b><em>Explosion effect position constructor</em></b>
	 * @param x The abscissa of the top left of the explosion image
	 * @param y	The ordinate of the top left of the explosion image
	 * @param battleField battlefield
	 */ 
	public BombHero(int x, int y, BattleField battleField) { 
		this.x = x;
		this.y = y;
		this.battleField = battleField;
	}

	/**
	 * <b><em>Explosion effect generation method</em></b>
	 * <p>Determine the survival status of the explosion image ({@code if (!live)}), if not, remove the explosion image
	 * <p>Determine if it is the last explosion image if {@code (step == imgs.length)}, and if so, change the inventory status of 
	 * the exploded image {@code (live = false)} , and reset {@code (step = 0)}, and finally return
	 * <p>If the explosion image survives, draw the explosion image g.drawImage{@code (imgs[step], x, y, null)} and continue to the next 
	 * {@code (step++)}  (continuous explosion image)
	 * @param g Brush tool
	 */
	public void draw(Graphics g) {

		if (!live) { 
			battleField.bombHeros.remove(this);
			return;
		}
		if (step == imgs.length) {
			live = false;
			step = 0;
			return;
		}

		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}
