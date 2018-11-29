package com.Leo.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


/**
 * <strong>Assistant class</strong>
 * <p>{@linkplain Hero}
 * @author Leo
 * @version 2.0
 * @since 2018.11.20 
 */ 
public class Assistant extends Hero {
	/**
	 * {@linkplain Hero#Hero(int, int, boolean, boolean)}
	 * @param x Mage abscissa
	 * @param y Assistant ordinate
	 * @param good Our side or the enemy
	 * @param robot Robot or user
	 */
	public Assistant(int x, int y, boolean good, boolean robot) {
		super(x, y, good,robot);
		name="DaQiao";
	}

	/**
	 * {@linkplain Hero#Hero(int, int, boolean, boolean, Direction, BattleField)}
	* @param x Mage abscissa
	 * @param y Assistant ordinate
	 * @param good Our side or the enemy
	 * @param robot Robot or user
	 * @param dir Mage direction
	 * @param battleField battleField
	 */
	public Assistant(int x, int y, boolean good, boolean robot, Direction dir, BattleField battleField) {
		super(x, y, good, robot, dir, battleField);
		name="DaQiao";
	}

	/**
	 * {@linkplain Hero#draw(Graphics)}
	 */
	public void draw(Graphics g) {
		if (!live) {
			if(DeadTime>0) {
				direction=Direction.STOP;
				DeadTime--;
			}
			else if(DeadTime==0) {
				this.setLive(true);
				this.HP=200;
				this.setLife(HP);
				DeadTime=100;
			}
			return;
		}
		if(!robot) {
			System.out.println("clickx:"+clickX+"::"+"x:"+this.getX());
			System.out.println("clicky:"+clickY+"::"+"y:"+this.getY());
		}
		new DrawBloodbBar().draw(g);
		new DrawMPbBar().draw(g);

		switch (Kdirection) {
							
		case D:
			g.drawImage(DaQiaoImags[0], x, y, null);
			break;

		case U:
			g.drawImage(DaQiaoImags[1], x, y, null);
			break;
		case L:
			g.drawImage(DaQiaoImags[2], x, y, null);
			break;

		case R:
			g.drawImage(DaQiaoImags[3], x, y, null);
			break;

		}
		this.move();   
	}
	
	/**
	 * {@linkplain Hero#move()}
	 * @param heros Robot
	 */
	void move() {
		this.oldX = x;
		this.oldY = y;
		
		if(clicked&&!robot) {
			if(bL&&clickX<this.getX()) 
				direction=Direction.L;
			else if(bU&&clickY<this.getY())
				direction=Direction.U;
			else if(bR&&clickX>this.getX())
				direction=Direction.R;
			else if(bD&&clickY>this.getY())
				direction=Direction.D;
			if(bL&&clickX>=this.getX()) {
				bL=false;
				direction=Direction.STOP;
			}
			System.out.println(direction);
			if(bU&&clickY>=this.getY()) {
				bU=false;
				direction=Direction.STOP;
			}
			if(bR&&clickX<=this.getX()) {
				bR=false;
				direction=Direction.STOP;
			}
			if(bD&&clickY<=this.getY()) {
				bD=false;
				direction=Direction.STOP;
			}
			if(!bL&&!bD&&!bU&&!bR)
				clicked=false;
		}
		
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
		if (x + Assistant.width > BattleField.Fram_width)  
			x = BattleField.Fram_width - Assistant.width;
		if (y + Assistant.length > BattleField.Fram_length)
			y = BattleField.Fram_length - Assistant.length;

		if (robot) {
			Direction[] directons = Direction.values();
			if (step == 0) {                  
				step = r.nextInt(12) + 3;  
				int rn = r.nextInt(directons.length);
				direction = directons[rn];      
			}
			step--;

			if (r.nextInt(40) > 38)
				this.AssistantFire();
		}
	}
	/**
	 * {@linkplain Hero#changToOldDir()}
	 */
	protected void changToOldDir() {  
		super.changToOldDir();
	}
	/**
	 * {@linkplain Hero#keyPressed(KeyEvent)}
	 */
	public void keyPressed(KeyEvent e) {  
		super.keyPressed(e);
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
		
		case KeyEvent.VK_J:
			AssistantFire();
			break;
			
		case KeyEvent.VK_D:
			bR = false;
			break;
		
		case KeyEvent.VK_A:
			bL = false;
			break;
		
		case KeyEvent.VK_W:
			bU = false;
			break;
		
		case KeyEvent.VK_S:
			bD = false;
			break;
		}
		decideDirection(); 
	}
	
	/**
	 * {@linkplain Hero#fire()}
	 * @return m
	 */
	public Rivers AssistantFire(){  
		if (!live)
			return null;
		int x = this.x + Assistant.width / 2 - Rivers.width / 2; 
		int y = this.y + Assistant.length / 2 - Rivers.length / 2;
		Rivers m = new Rivers(x, y , good, Kdirection, this.battleField); 
		battleField.rivers.add(m);                                         
		return m;
	}

	/**
	 * {@linkplain Hero#getRect()}
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	public Rectangle getSkillRect() {
		return new Rectangle(x - Skill_scope / 2 ,y - Skill_scope / 2 , Skill_scope, Skill_scope);
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
	 * {@linkplain Hero#collideWithWall(MetalWall)}
	 */
	public boolean collideWithWall(MetalWall w) { 
		return super.collideWithWall(w);
	}

	/**
	 * {@linkplain Hero#collideHome(Home)}
	 */
	public boolean collideHome(Home h) {  
		return super.collideHome(h);
	}

	/**
	 * {@linkplain Hero#collideWithHeros(java.util.List)}
	 */
	public boolean collideWithHeros(java.util.List<Hero> heros) {
		return super.collideWithHeros(heros);
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
			g.drawRect(getX()+20, getY()-30, width, 5);
			int w = width * HP / 200;
			g.fillRect(getX()+20, getY()-30, w, 5);
			g.setColor(c);
		}
	}
	private class DrawMPbBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.BLUE);
			g.drawRect(getX()+20, getY()-20, width, 5);
			int w = width * MP / 100;
			g.fillRect(getX()+20, getY()-20, w, 5);
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

	public void MouseClicked(MouseEvent e) { 
		super.MouseClicked(e);
	}

}
