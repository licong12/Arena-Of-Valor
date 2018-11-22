package com.Leo.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * <strong>Master class</strong>
 * <p>{@linkplain Hero}
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 */
public class Master extends Hero{
	/**
	 * {@linkplain Hero#Hero(int, int, boolean, boolean)}
	 * @param x Mage abscissa
	 * @param y Master ordinate
	 * @param good Our side or the enemy
	 * @param robot Robot or user
	 */
	public Master(int x, int y, boolean good, boolean robot) {
		super(x, y, good,robot);
	}

	/**
	 * {@linkplain Hero#Hero(int, int, boolean, boolean, Direction, BattleField)}
	* @param x Mage abscissa
	 * @param y Master ordinate
	 * @param good Our side or the enemy
	 * @param robot Robot or user
	 * @param dir Mage direction
	 * @param battleField battleField
	 */
	public Master(int x, int y, boolean good, boolean robot, Direction dir, BattleField battleField) {
		super(x, y, good, robot, dir, battleField);
	}

	/**
	 * {@linkplain Hero#draw(Graphics)}
	 */
	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				battleField.heros.remove(this); 
			}
			return;
		}
		new DrawBloodbBar().draw(g);
		new DrawMPbBar().draw(g);

		switch (Kdirection) {
							
		case D:
			g.drawImage(RedHeroImags[0], x, y, null);
			break;

		case U:
			g.drawImage(RedHeroImags[1], x, y, null);
			break;
		case L:
			g.drawImage(RedHeroImags[2], x, y, null);
			break;

		case R:
			g.drawImage(RedHeroImags[3], x, y, null);
			break;

		}
		move();   
	}
	
	/**
	 * {@linkplain Hero#move()}
	 * @param heros Robot
	 */
	void move(List<Hero> heros) {
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
		if (x + Master.width > BattleField.Fram_width)  
			x = BattleField.Fram_width - Master.width;
		if (y + Master.length > BattleField.Fram_length)
			y = BattleField.Fram_length - Master.length;

		if (robot) {
			Direction[] directons = Direction.values();
			if (step == 0) {                  
				step = r.nextInt(12) + 3;  
				int rn = r.nextInt(directons.length);
				direction = directons[rn];      
			}
			step--;

			if (r.nextInt(40) < 38)
				this.MasterFire();
		}
	}
	/**
	 * {@linkplain Hero#changToOldDir()}
	 */
	protected void changToOldDir() {  
		x = oldX;
		y = oldY;
	}

	/**
	 * {@linkplain Hero#keyPressed(KeyEvent)}
	 */
	public void keyPressed(KeyEvent e) {  
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_R:  
			battleField.heros.clear();
			battleField.balls.clear();
//			battleField.trees.clear();
//			battleField.otherWall.clear();
			battleField.metalWall.clear();
			battleField.MyHero.setLive(false);
			if (battleField.heros.size() == 0) {  
				battleField.heros.add(new Master(150 + 70 *3, 40, false, true,
								Direction.R, battleField));
			}
			
			battleField.MyHero = new Master(300, 560, true, false, Direction.STOP, battleField);
			
			if (!battleField.RedHome.isLive()) 
				battleField.RedHome.setLive(true);
			if (!battleField.BlueHome.isLive()) 
				battleField.BlueHome.setLive(true);
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
	 * {@linkplain Hero#decideDirection()}
	 */
	void decideDirection() {
		super.decideDirection();
	}

	/**
	 * {@linkplain Hero#keyPressed(KeyEvent)}
	 */
	public void keyReleased(KeyEvent e) { 
		int key = e.getKeyCode();
		switch (key) {
		
		case KeyEvent.VK_F:
			MasterFire();
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
	 * {@linkplain Hero#fire()}
	 * @return m
	 */
	public Balls MasterFire(){  
		if (!live)
			return null;
		int x = this.x + Master.width / 2 - Balls.width / 2; 
		int y = this.y + Master.length / 2 - Balls.length / 2;
		Balls m = new Balls(x, y , good, Kdirection, this.battleField); 
		battleField.balls.add(m);                                         
		return m;
	}

	/**
	 * {@linkplain Hero#getRect()}
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	/**
	 * {@linkplain Hero#isLive()}
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * {@linkplain Hero#setLive(boolean)}
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * {@linkplain Hero#isGood()}
	 */
	public boolean isGood() {
		return good;
	}

	/**
	 * {@linkplain Hero#collideWithWall(CommonWall)}
	 */
	public boolean collideWithWall(CommonWall w) {  
		if (this.live && this.getRect().intersects(w.getRect())) {
			 this.changToOldDir();    
			return true;
		}
		return false;
	}

	/**
	 * {@linkplain Hero#collideWithWall(MetalWall)}
	 */
	public boolean collideWithWall(MetalWall w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();     
			return true;
		}
		return false;
	}

	/**
	 * {@linkplain Hero#collideRiver(River)}
	 */
	public boolean collideRiver(River r) {   
		if (this.live && this.getRect().intersects(r.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	/**
	 * {@linkplain Hero#collideHome(Home)}
	 */
	public boolean collideHome(Home h) {  
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	/**
	 * {@linkplain Hero#collideWithHeros(java.util.List)}
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
	 * {@linkplain Hero#getLife()}
	 */
	public int getLife() {
		return HP;
	}

	/**
	 * {@linkplain Hero#setLife(int)}
	 */
	public void setLife(int life) {
		this.HP = life;
	}

	/**
	 * {@linkplain DrawBloodbBar}
	 */
	private class DrawBloodbBar {
		
		/**
		 * {@linkplain DrawBloodbBar#draw(Graphics)}
		 * @param g Brush Tool
		 */
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(getX()-3, getY()-30, width, 5);
			int w = width * HP / 200;
			g.fillRect(getX()-3, getY()-30, w, 5);
			g.setColor(c);
		}
	}
	private class DrawMPbBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLUE);
			g.drawRect(getX()-3, getY()-20, width, 5);
			int w = width * MP / 100;
			g.fillRect(getX()-3, getY()-20, w, 5);
			g.setColor(c);
		}
	}

	/**
	 * {@linkplain Hero#eat(GetBlood)}
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
	 * {@linkplain Hero#getX()}
	 */
	public int getX() {
		return x;
	}

	/**
	 * {@linkplain Hero#getY()}
	 */
	public int getY() {
		return y;
	}


}
