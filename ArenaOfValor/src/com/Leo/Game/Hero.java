package com.Leo.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

/**
 * <b><em>Hero abstract class</em></b>
 * <p>There are two types of heroes, enemy heroes 
 * and user-controlled heroes.Both the user side 
 * and the enemy hero can fire bullets, which can 
 * change the path of walking, and change direction 
 * when encountering obstacles such as walls and game 
 * boundaries during walking, but not always holding 
 * obstacles. Heroes can't cross each other, and 
 * automatically change direction after collision.
 * <p>The attributes of the hero: speed ({@link #speedX},
 * {@link #speedY}), the size of the hero ({@link #length},
 * {@link #width}), the new position of the hero 
 * ({@link #x},{@link #y}), whether the hero is still 
 * alive ({@link #live}), the direction of action ({@link #direction}),
 *  etc. All of these properties have an initialization 
 *  value that can be run from the beginning of the game.
 * 
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 */
public abstract class Hero {
	public static  int speedX = 6, speedY =6; 
	public static int count = 0;
	public static final int width = 35, length = 35; 
	protected Direction direction = Direction.STOP; 
	protected Direction Kdirection = Direction.U; 
	BattleField battleField;

	protected boolean good;
	protected boolean robot;
	protected int x, y;
	protected int oldX, oldY;
	protected boolean live = true;
	protected int HP = 200; 
	protected int MP = 100;

	protected static Random r = new Random();
	protected int step = r.nextInt(10)+5 ; 

	protected boolean bL = false, bU = false, bR = false, bD = false;
	

	protected static Toolkit tk = Toolkit.getDefaultToolkit();
	protected static Image[] BlueHeroImags = null; 
	protected static Image[] RedHeroImags = null; 
	static {
		BlueHeroImags = new Image[] {
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/RoleD.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/RoleU.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/RoleL.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/RoleR.gif"))};
	}
	static {
		RedHeroImags = new Image[] {
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/RedD.png")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/RedU.png")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/RedL.png")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/RedR.png")) };
	}
	
	/**
	 * <b><em>Initialize the hero with three parameters</em></b>
	 * @param x Hero's abscissa
	 * @param y	Hero's ordinate
	 * @param good	Our side or enemy
	 * @param robot User or machine
	 */
	Hero(int x, int y, boolean good,boolean robot) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
		this.robot=robot;
	}
	/**
	 * <b><em>Initialize the hero with five parameters</em></b>
	 * @param x Hero's abscissa
	 * @param y	Hero's ordinate
	 * @param good	Our side or enemy
	 * @param robot User or machine
	 * @param dir Hero's direction
	 * @param battlefield battlefield
	 */
	public Hero(int x, int y, boolean good,boolean robot, Direction dir, BattleField battlefield) {
		this(x, y, good,robot);
		this.direction = dir;
		this.battleField = battlefield;
	}
	
	/**
	 *  <b><em>Hero drawing abstract method</em></b>
	 * @param g Graphics
	 */
	public abstract void draw(Graphics g);
	

	/**
	 * <b><em>Heros move method</em></b>
	 * <p>The position after the user side is monitored by the keyboard 
	 * and forwards at a constant speed in the specified direction - 
	 * this speed is a global static variable and remains stationary 
	 * when no keyboard control is received. 
	 * <p>The enemy's hero controls random directions and 
	 * paths based on random numbers.
	 */
	void move( ) {
		
		this.oldX = x;
		this.oldY = y;
		
		switch (direction) {
		case L:
			x -= speedX;
			break;
		case U:
			y -= speedY;
			break;
		case R:
			x += speedX;
			break;
		case D:
			y += speedY;
			break;
		case STOP:
			break;
		}

		if (this.direction != Direction.STOP) {
			this.Kdirection = this.direction;
		}

		if (x < 0)
			x = 0;
		if (y < 40) 
			y = 40;
		if (x + Shooter.width > BattleField.Fram_width)  
			x = BattleField.Fram_width - Shooter.width;
		if (y + Shooter.length > BattleField.Fram_length)
			y = BattleField.Fram_length - Shooter.length;

		if (robot) {
			Direction[] directons = Direction.values();
			if (step == 0) {                  
				step = r.nextInt(12) + 3; 
				int rn = r.nextInt(directons.length);
				direction = directons[rn];
			}
			step--;

			if (r.nextInt(40) > 38)
				this.fire();
		}
	}

	
	/**
	 * <em><b>Hero movement method</b></em>
	 * <p>When the enemy hero hits the obstacle, it will return to 
	 * the position of the previous step, thus solving the problem 
	 * that the hero hits the obstacle and does not look back. 
	 * This function is implemented by the {@link #changToOldDir()} method 
	 * in the hero class.
**/
	protected void changToOldDir() {  
		x = oldX;
		y = oldY;
	}

	/**
	 *<em><b>Keyboard operation method</b></em>
	 *<p>The keyPressed() method of the hero class is used to 
	 *accept the keystrokes of the keyboard. After receiving the 
	 *corresponding keyboard information, if F is received, 
	 *it means that the bullet is fired, so the fire() method
	 *in the hero class is called at this time, fire() The
	 *method does not pass arguments because the direction 
	 *of the bullet is always subordinate to the direction and 
	 *position of the hero.
	 * @param e Keyboard event
	 */
	public void keyPressed(KeyEvent e) { 
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_R:  
			battleField.heros.clear(); 
			battleField.bullets.clear();
//			battleField.trees.clear();
//			battleField.otherWall.clear();
			battleField.metalWall.clear();
			battleField.MyHero.setLive(false);
			if (battleField.heros.size() == 0) {  
					battleField.heros.add(new Shooter(150 + 70 *2, 40, false, true,
						Direction.R, battleField));
			}

			
		battleField.MyHero = new Master(300, 560, true, true, Direction.STOP, battleField);
			
			if (!battleField.RedHome.isLive())  
				battleField.RedHome.setLive(true);
			new BattleField(); 
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
			
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		
		case KeyEvent.VK_UP:
			bU = true;
			break;
		
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		decideDirection();
	}

	/**
	 * <b><em>Hero direction decision method</em></b>
	 */
	void decideDirection() {
		if (!bL && !bU && bR && !bD)
			direction = Direction.R;

		else if (bL && !bU && !bR && !bD)
			direction = Direction.L;

		else if (!bL && bU && !bR && !bD)
			direction = Direction.U;

		else if (!bL && !bU && !bR && bD)
			direction = Direction.D;

		else if (!bL && !bU && !bR && !bD)
			direction = Direction.STOP;  
	}

	/**
	 * <b><em>Keyboard release method</em></b>
	 * @param e Keyboard event
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		
		case KeyEvent.VK_F:
			fire();
			break;
			
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		
		case KeyEvent.VK_UP:
			bU = false;
			break;
		
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		decideDirection(); 
	}

	/**
	 * <b><em>Hero fire attack effect</em></b>
	 * <p>Determines whether the fire attack exists, does not exist, returns null, if it exists, initializes the new bullet 
	 * position coordinates and adds it to the bullet container
	 * 	@return Bullet object
	 */
	public  Bullets fire() {
		if (!live)
			return null;
		int x = this.x + Hero.width / 2 - Bullets.width / 2;  
		int y = this.y + Hero.length / 2 - Bullets.length / 2;
		Bullets m = new Bullets(x, y + 2, good, Kdirection, this.battleField); 
		battleField.bullets.add(m);                                                
		return m;
	}


	/**
	 * <b><em>Rectangular instance</em></b>
	 * @return Return a rectangle instance
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	/**
	 * <b><em>Method for determining hero survival status</em></b>
	 * @return boolean live
	 */
	public boolean isLive() {
		return live;
	}

/**
 * <b><em>Set hero survival status</em></b>
 * @param live Hero survival
 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**<b><em>Return hero is enemy or our side</em></b>
	 * @return Boolean good
	 */
	public boolean isGood() {
		return good;
	}

	/**
	 * <b><em>Hero collision ordinary wall processing method</em></b>
	 * <p>The hero has to change direction after encountering the ordinary 
	 * wall {@code this.changToOldDir();}
	 * <p>Determine whether the hero and the wall collide, that is, determine 
	 * whether the rectangle to which the hero belongs and the rectangle to 
	 * which the ordinary wall belongs intersect{@code this.getRect().intersects(w.getRect()}
	 * @param w	CommonWall
	 * @return Boolean
	 */
	public boolean collideWithWall(CommonWall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			 this.changToOldDir();   
			return true;
		}
		return false;
	}

	/**
	 * <b><em>Hero collision metal wall treatment method</em></b>
	 * <p>The hero has to change direction after encountering the 
	 * metal wall {@code this.changToOldDir();}
	 * <p>Determine whether the hero and the wall collide, that is, 
	 * determine whether the rectangle to which the hero belongs and 
	 * the rectangle to which the gold wall belongs intersect {@code this.getRect().intersects(w.getRect()}
	 * @param w MetalWall
	 * @return Boolean
	 */
	public boolean collideWithWall(MetalWall w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();     
			return true;
		}
		return false;
	}

	/**
	 * <b><em>Hero collision river wall treatment method</em></b>
	 * <p>The hero has to change direction after encountering the 
	 * metal wall {@code this.changToOldDir();}
	 * <p>Determine whether the hero and the river collide, that is, 
	 * determine whether the rectangle to which the hero belongs and 
	 * the rectangle to which the River belongs intersect {@code this.getRect().intersects(r.getRect()}
	 * @param r River
	 * @return Boolean
	 */
	public boolean collideRiver(River r) {
		if (this.live && this.getRect().intersects(r.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	/**
	 * <b><em>Hero collision home treatment method</em></b>
	 * <p>The hero has to change direction after encountering the 
	 * home {@code this.changToOldDir();}
	 * <p>Determine whether the hero and the home collide, that is, 
	 * determine whether the rectangle to which the hero belongs and 
	 * the rectangle to which the River belongs intersect {@code this.getRect().intersects(r.getRect()}
	 * @param h Home
	 * @return Boolean
	 */
	public boolean collideHome(Home h) {  
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	/**
	 * <b><em>Hero collision Hero treatment method</em></b>
	 * <p>The hero has to change direction after encountering the 
	 * Hero {@code this.changToOldDir();}
	 * <p>Determine whether the hero and the Hero collide, that is, 
	 * determine whether the rectangle to which the hero belongs and 
	 * the rectangle to which the Hero belongs intersect {@code this.getRect().intersects(r.getRect()}
	 * @param heros robot Hero
	 * @return Boolean
	 */
	public boolean collideWithHeros(java.util.List<Hero> heros) {
		for (int i = 0; i < heros.size(); i++) {
			Hero t = heros.get(i);
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRect().intersects(t.getRect())) {
					this.changToOldDir();
					t.changToOldDir();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <b><em>Return HP value</em></b>
	 * @return HP 
	 */
	public int getLife() {
		return HP;
	}
	
	/**
	 * <b><em>Set the HP value</em></b>
	 * @param HP HP
	 */
	public void setLife(int HP) {
		this.HP = HP;
	}

	/**
	 * <b><em>Return MP value</em></b>
	 * @return MP
	 */
	public int getMP() {
		return MP;
	}

	/**
	 * <b><em>Set the MP value</em></b>
	 * @param MP MP
	 */
	public void setMP(int MP) {
		MP = MP;
	}

	/**
	 * <b><em>Blood strip</em></b>
	 */
	private class DrawBloodbBar {
		/**
		 *<b><em>Drawing a blood strip method</em></b> 
		 * @param g Brush Tool
		 */
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(375, 585, width, 10);
			int w = width * HP / 200;
			g.fillRect(375, 585, w, 10);
			g.setColor(c);
		}
	}

	/**
	 * <b><em>Hero eating blood packet method</em></b> 
	 * <p>Determine whether the hero {@link Hero#live} survives whether the blood 
	 * package is alive {@link GetBlood#isLive()} and whether the hero and the blood bag 
	 * rectangle intersect {@link GetBlood#getRect()}, and if so, increase the hero's health
	 * @param b Blood bag
	 * @return Boolean
	 */
	public boolean eat(GetBlood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if(this.HP<=100)
				this.HP = this.HP+100;   
			else
				this.HP = 200;
			b.setLive(false);
			return true;
		}
		return false;
	}

	/**
	 * <b><em>Return hero's abscissa</em></b> 
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * <b><em>Return hero ordinate</em></b> 
	 * @return y
	 */
	public int getY() {
		return y;
	}

}
