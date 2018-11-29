package com.Leo.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <strong>Mage river</strong>
 *<p>The properties of the river: the {@link #speedX} and {@link #speedY} of the river - 
 *{@code SpeedX=10,SpeedY=10}, the {@link #length} and {@link #width} of the river - 
 *{@code length=10,width=10}, the position of the river, the direction of 
 *the river({@link Rivers#diretion}), whether the river is live({@link #live}), etc. 
 *
 *<p>Since the Rivers in different directions are actually pictures, it is necessary to 
 *consider the selection of different pictures with the corresponding direction, so to establish 
 *a Map key-value pair, use the direction of the String attribute to specify different pictures.
 *
 *<p>There is a constructor in the class that is used to pass the position and direction by default. 
 *Of course, isomorphism is used here, and another constructor is used to get the state and interface 
 *of the river. ({@link Rivers#draw(Graphics)}) and ({@link #move()}) to control drawing Rivers and moving Rivers
 *
 *<p>The next step is to consider the reaction of the river to each element. When the enemy's 
 *river hits the enemy, it will not kill the person, so just return true directly, without 
 *removing the river that the river encounters. Not to mention the explosion to show the explosion effect.
 * @author ¿Ó¥œ
 * @version 2.0
 * @since 2018.11.20
 */
public class Rivers {
	public static  int speedX = 0;
	public static  int speedY = 0;

	public static final int width = 10;
	public static final int length = 10;

	private int x, y;
	Direction diretion;

	private boolean good;
	private boolean live = true;

	private BattleField battleField;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] riverImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
				
	static {
		riverImages = new Image[] { 
				tk.getImage(Rivers.class.getClassLoader().getResource(
						"com/Leo/Game/images/riverL.jpg")),

				tk.getImage(Rivers.class.getClassLoader().getResource(
						"com/Leo/Game/images/riverU.jpg")),

				tk.getImage(Rivers.class.getClassLoader().getResource(
						"com/Leo/Game/images/riverR.jpg")),

				tk.getImage(Rivers.class.getClassLoader().getResource(
						"com/Leo/Game/images/riverD.jpg")),

		};

		imgs.put("L", riverImages[0]); 

		imgs.put("U", riverImages[1]);
		
		imgs.put("R", riverImages[2]);
		
		imgs.put("D", riverImages[3]);

	}
	/**
	 * <b><em>Mage river construction method with three parameters</em></b>
	 * <p>Where the river appears
	 * @param x	river upper left horizontal axis
	 * @param y river upper left ordinate
	 * @param dir Direction of the river
	 */
	public Rivers(int x, int y, Direction dir) { 
		this.x = x;
		this.y = y;
		this.diretion = dir;
	}
	/**
	 * <b><em>Mage river construction method with five parameters</em></b>
	 * <p>The position and definition of the river is the enemy river or our river.
	 * @param x	river upper left horizontal axis
	 * @param y river upper left ordinate
	 * @param dir Direction of the river 
	 * @param good	Whether the source of the river is our (true) or enemy (false)
	 * @param battleField battlefield
	 */
	public Rivers(int x, int y, boolean good, Direction dir, BattleField battleField) {
		this(x, y, dir);
		this.good = good;
		this.battleField = battleField;
	}
	/**
	 * <b><em>Method of portraying the river of the wizard</em></b>
	 * <p>According to the enumeration direction to determine the change of the horizontal 
	 * and vertical coordinate values of the river, it is necessary to determine whether 
	 * the river coordinate value is out of the battlefield interface, and if so, the river
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
	 * <b><em>Method of drawing a river</em></b>
	 * <p>According to the {@code direction} of the river, the {@code image[]} 
	 * pattern of the river is determined, and finally the river is moved {@code move()}.
	 * @param g Brush tool
	 */
	public void draw(Graphics g) {
		if (!live) {
			battleField.rivers.remove(this);
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
	 * <b><em>Determine if the wizard's river is alive</em></b>
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
		if (this.live && !this.getRect().intersects(hero.getSkillRect()) && hero.isLive()
				&&hero.name=="DaQiao"&&this.good == hero.isGood()) {
			this.live=false;
			return true;
		}
		return false;
	}
	/**
	 * <b><em>The way river hits a hero</em></b>
	 * <p>Determine if the river hits the enemy hero, and if so, perform the effect after hitting{@code hitHero(heros.get(i))}
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
	* <b><em>The way river hits a hero</em></b>
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
	* <b><em>Method of hitting a river into a metal wall</em></b>
	* <p>After the river hits the metal wall, it only changes the survival state of the river.{@code this.live = false}
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
