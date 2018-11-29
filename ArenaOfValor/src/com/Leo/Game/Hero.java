package com.Leo.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Time;
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
public abstract class Hero{
	public static  int speedX = 6, speedY =6; 
	public static int count = 0;
	public static final int width = 35, length = 35, Skill_scope=130; 
	protected Direction direction = Direction.STOP; 
	protected Direction Kdirection = Direction.U; 
	                                                                      protected static int DeadTime=10;
	BattleField battleField;

	protected String name;
	protected boolean good;
	protected boolean robot;
	protected boolean clicked=false;
	protected int x, y;
	protected int oldX, oldY;
	protected int clickX,clickY;
	protected boolean live = true;
	protected int HP = 200; 
	protected int MP = 100;

	protected static Random r = new Random();
	protected int step = r.nextInt(10)+5 ; 

	protected boolean bL = false, bU = false, bR = false, bD = false;
	

	protected static Toolkit tk = Toolkit.getDefaultToolkit();
	protected static Image[] LuBanQiHaoImags = null; 
	protected static Image[] AnQiLaImags = null; 
	protected static Image[] YaSeImags = null; 
	protected static Image[] DaQiaoImags = null; 
	protected static Image[] MESSIImags = null; 
	static { 
		LuBanQiHaoImags = new Image[] { 
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/LuBanQiHaoD.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/LuBanQiHaoU.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/LuBanQiHaoL.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/LuBanQiHaoR.gif"))};
	}
	static {
		AnQiLaImags = new Image[] {
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/AnQiLaD.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/AnQiLaU.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/AnQiLaL.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/AnQiLaR.gif")) };
	}
	static {
		YaSeImags = new Image[] {
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/YaSeD.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/YaSeU.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/YaSeL.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/YaSeR.gif")) };
	}
	static {
		DaQiaoImags = new Image[] {
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/DaQiaoD.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/DaQiaoU.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/DaQiaoL.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/DaQiaoR.gif")) };
	}
	static {
		MESSIImags = new Image[] {
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/MESSID.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/MESSIU.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/MESSIL.gif")),
				tk.getImage(BombHero.class.getResource("/com/Leo/Game/Images/MESSIR.gif")) };
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
	abstract void move( );

	
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
		System.out.println("KeyPressed:"+key);
		switch (key) {
		case KeyEvent.VK_R:  
			battleField.heros.clear(); 
			battleField.MyHero.setLive(false);
			battleField.BlueHome.setLive(false);
			battleField.setVisible(false);
			battleField.time.Reset(battleField);
			battleField.time.setVisible(false);
			new SelectHero(); 
			break;
		case KeyEvent.VK_D:
			bR = true;
			break;
			
		case KeyEvent.VK_A:
			bL = true;
			break;
		
		case KeyEvent.VK_W:
			bU = true;
			break;
		
		case KeyEvent.VK_S:
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
	public abstract void keyReleased(KeyEvent e);

	/**
	 * <b><em>Rectangular instance</em></b>
	 * @return Return a rectangle instance
	 */
	public abstract Rectangle getRect();
	
	public abstract Rectangle getSkillRect();

	/**
	 * <b><em>Method for determining hero survival status</em></b>
	 * @return boolean live
	 */
	public abstract boolean isLive();

/**
 * <b><em>Set hero survival status</em></b>
 * @param live Hero survival
 */
	public abstract void setLive(boolean live);

	/**<b><em>Return hero is enemy or our side</em></b>
	 * @return Boolean good
	 */
	public abstract boolean isGood();


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
		this.MP = MP;
	}

	/**
	 * <b><em>Blood strip</em></b>
	 */
	protected abstract class DrawBloodbBar {
		/**
		 *<b><em>Drawing a blood strip method</em></b> 
		 * @param g Brush Tool
		 */
		public abstract void draw(Graphics g); 
	}

	protected abstract class DrawMPbBar {
		public abstract void draw(Graphics g);
	}
	
	/**
	 * <b><em>Hero eating blood packet method</em></b> 
	 * <p>Determine whether the hero {@link Hero#live} survives whether the blood 
	 * package is alive {@link GetBlood#isLive()} and whether the hero and the blood bag 
	 * rectangle intersect {@link GetBlood#getRect()}, and if so, increase the hero's health
	 * @param b Blood bag
	 * @return Boolean
	 */
	public abstract boolean eat(GetBlood b);

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
	public void MouseClicked(MouseEvent e) { 
		System.out.println("MouseClicked");
		clickX=e.getX();
		clickY=e.getY();
		clicked=true;
		if(clickX>this.getX()) {
			bR=true;
		}
		else if(clickX<this.getX()){
			bL=true;
		}
		if(clickY>this.getY()) {
			bD=true;
		}
		else if(clickY<this.getY()) {
			bU=true;
		}
		if(clicked) {
			if(bL&&clickX<this.getX()) 
				direction=Direction.L;
			else if(bU&&clickY<this.getY())
				direction=Direction.U;
			else if(bR&&clickX>this.getX())
				direction=Direction.R;
			else if(bD&&clickY>this.getY())
				direction=Direction.D;
			else 
				clicked=false;
		}
	}

}
