package com.Leo.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <strong>Mage Fireball</strong>
 *<p>The properties of the fireball: the {@link #speedX} and {@link #speedY} of the fireball - 
 *{@code SpeedX=10,SpeedY=10}, the {@link #length} and {@link #width} of the fireball - 
 *{@code length=10,width=10}, the position of the fireball, the direction of 
 *the fireball({@link Balls#diretion}), whether the fireball is live({@link #live}), etc. 
 *
 *<p>Since the fireballs in different directions are actually pictures, it is necessary to 
 *consider the selection of different pictures with the corresponding direction, so to establish 
 *a Map key-value pair, use the direction of the String attribute to specify different pictures.
 *
 *<p>There is a constructor in the class that is used to pass the position and direction by default. 
 *Of course, isomorphism is used here, and another constructor is used to get the state and interface 
 *of the fireball. ({@link Balls#draw(Graphics)}) and ({@link #move()}) to control drawing fireballs and moving fireballs
 *
 *<p>The next step is to consider the reaction of the fireball to each element. When the enemy's 
 *fireball hits the enemy, it will not kill the person, so just return true directly, without 
 *removing the fireball that the fireball encounters. Not to mention the explosion to show the explosion effect.
 * @author 李聪
 * @version 2.0
 * @since 2018.11.20
 */
public class Balls {
	public static  int speedX = 10;
	public static  int speedY = 10;

	public static final int width = 10;
	public static final int length = 10;

	private int x, y;
	Direction diretion;

	private boolean good;
	private boolean live = true;

	private BattleField battleField;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] ballImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
				
	static {
		ballImages = new Image[] { 
				tk.getImage(Balls.class.getClassLoader().getResource(
						"com/Leo/Game/images/BallL.png")),

				tk.getImage(Balls.class.getClassLoader().getResource(
						"com/Leo/Game/images/BallU.png")),

				tk.getImage(Balls.class.getClassLoader().getResource(
						"com/Leo/Game/images/BallR.png")),

				tk.getImage(Balls.class.getClassLoader().getResource(
						"com/Leo/Game/images/BallD.png")),

		};

		imgs.put("L", ballImages[0]); 

		imgs.put("U", ballImages[1]);
		
		imgs.put("R", ballImages[2]);
		
		imgs.put("D", ballImages[3]);

	}
	/**
	 * <b><em>Mage fireball construction method with three parameters</em></b>
	 * <p>Where the fireball appears
	 * @param x	Fireball upper left horizontal axis
	 * @param y Fireball upper left ordinate
	 * @param dir Direction of the fireball
	 */
	public Balls(int x, int y, Direction dir) { 
		this.x = x;
		this.y = y;
		this.diretion = dir;
	}
	/**
	 * <b><em>Mage fireball construction method with five parameters</em></b>
	 * <p>The position and definition of the fireball is the enemy fireball or our fireball.
	 * @param x	Fireball upper left horizontal axis
	 * @param y Fireball upper left ordinate
	 * @param dir Direction of the fireball 
	 * @param good	Whether the source of the fireball is our (true) or enemy (false)
	 * @param battleField battlefield
	 */
	public Balls(int x, int y, boolean good, Direction dir, BattleField battleField) {
		this(x, y, dir);
		this.good = good;
		this.battleField = battleField;
	}
	/**
	 * <b><em>Method of portraying the fireball of the wizard</em></b>
	 * <p>According to the enumeration direction to determine the change of the horizontal 
	 * and vertical coordinate values of the fireball, it is necessary to determine whether 
	 * the fireball coordinate value is out of the battlefield interface, and if so, the fireball
	 * {@code live=false}.
	 */
	private void move() {

		switch (diretion) {
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

		if (x < 0 || y < 0 || x > BattleField.Fram_width
				|| y > BattleField.Fram_length) {
			live = false;
		}
	}
	/**
	 * <b><em>Method of drawing a fireball</em></b>
	 * <p>According to the {@code direction} of the fireball, the {@code image[]} 
	 * pattern of the fireball is determined, and finally the fireball is moved {@code move()}.
	 * @param g Brush tool
	 */
	public void draw(Graphics g) {
		if (!live) {
			battleField.balls.remove(this);
			return;
		}

		switch (diretion) {
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;

		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;

		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;

		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;

		}

		move();
	}
	/**
	 * <b><em>Determine if the wizard's fireball is alive</em></b>
	 * @return boolean life
	 */
	public boolean isLive() { 
		return live;
	}
	
	/**
	 * <b><em>Rectangular instance</em></b>
	 * <p>Each interface element has a rectangular instance that is used to determine if 
	 * two interface elements are touching each other (the rectangles intersect)
	 * @return Rectangular position size
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}
	
	public boolean SkillScopes(List<Hero> heros) {
		for (int i = 0; i < heros.size(); i++) {
			if (SkillScope(heros.get(i))) { 
				return true;
			}
		}
		return false; 
	}
	
	public boolean SkillScope(Hero hero) {
		System.out.println(this.getRect().intersects(hero.getSkillRect()));
		if (this.live && !this.getRect().intersects(hero.getSkillRect()) && hero.isLive()
				&&hero.name=="AnQiLa"&&this.good == hero.isGood()) {
			this.live=false;
			return true;
		}
		return false;
	}
	
	/**
	 * <b><em>The way fireball hits a hero</em></b>
	 * <p>Determine if the fireball hits the enemy hero, and if so, perform the effect after hitting{@code hitHero(heros.get(i))}
	 * @param heros Hero container
	 * @return Boolean Hit the hero
	 */
	public boolean hitHeros(List<Hero> heros) {
		for (int i = 0; i < heros.size(); i++) {
			if (hitHero(heros.get(i))) { 
				return true;
			}
		}
		return false; 
	}

	/**
	* <b><em>The way fireball hits a hero</em></b>
	* <p>Determine if the hit hero is an enemy hero, and if so, the enemy's health will decrease.
	* @param hero hero
	* @return Boolean Hit the hero
	*/
	public boolean hitHero(Hero hero) { 
		if (this.live && this.getRect().intersects(hero.getRect()) && hero.isLive()
				&& this.good != hero.isGood()) {
			BombHero e = new BombHero(hero.getX(), hero.getY(), battleField);
			battleField.bombHeros.add(e);
			if(hero.getLife()>50) {
				hero.setLife(hero.getLife() - 50);
//				hero.setMP(hero.getMP()+25); 
			}
			else if(hero.getLife()<=50)
				hero.setLife(hero.getLife() - 5);
			if (hero.getLife() <= 0)
				hero.setLive(false); 
			this.live = false;
			return true;
		}
		return false;
	}
	/**
	* <b><em>Method of hitting a fireball into a metal wall</em></b>
	* <p>After the fireball hits the metal wall, it only changes the survival state of the fireball.{@code this.live = false}
	* @param w Metal wall
	* @return Boolean Hit the metal wall
	*/
	public boolean hitWall(MetalWall w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}

	
	public boolean hitHome(Home home) { 
		if (this.live && this.getRect().intersects(home.getRect()) && this.good != home.isRed()) {
			this.live = false;
			home.setLife(home.getLife()-10); 
			if(home.getLife()<=0)
				home.setLive(false);
			return true;
		}
		return false;
	}



}
