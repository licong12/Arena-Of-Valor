package com.Leo.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * <b><em>Home Class</em></b>
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 *
 */
public class Home {
	private int x, y;
	private BattleField battleField;
	public static final int width = 30, length = 30; 
	private boolean live = true;
	private boolean red;
	private int HP = 200; 

	private static Toolkit tk = Toolkit.getDefaultToolkit(); 
	private static Image[] homeImags = null;
	static {
		homeImags = new Image[] { tk.getImage(CommonWall.class.getResource("/com/Leo/Game/Images/RedHome.jpg")), 
				tk.getImage(CommonWall.class.getResource("/com/Leo/Game/Images/BlueHome.jpg"))};
	}

	/**
	 * <b><em>Home construction method</em></b>
	 * @param x Home left upper horizontal axis
	 * @param y Home upper left ordinate
	 * @param red Whether it is red
	 * @param battleField battleField
	 */
	public Home(int x, int y, Boolean red,BattleField battleField) {
		this.x = x;
		this.y = y; 
		this.battleField = battleField; 
		this.red=red;
	}
	
	/**
	 * <b><em>Game ending method</em></b>
	 * <p>After the game is over, clean up the interface elements 
	 * and prompt "Game Over!"
	 * @param g Brush tool
	 */
	public void gameOver(Graphics g) {

		battleField.heros.clear();
		battleField.metalWall.clear();
//		battleField.otherWall.clear();
		battleField.bombHeros.clear();
//		battleField.theRiver.clear();
//		battleField.trees.clear();
		battleField.bullets.clear();
		battleField.balls.clear();
		Color c = g.getColor();
		g.setColor(Color.pink);
		Font f = g.getFont();
		g.setFont(new Font(" ", Font.PLAIN, 40));
		g.drawString("  Game Over! ", 220, 300);
		g.setFont(f);
		g.setColor(c);
	}

	/**
	 * <b><em>Painter method</em></b>
	 * <p>Judging the survival status of the home and whether 
	 * it is red
	 * <ul>
	 * <li>• If the home is alive and red, draw a red square picture 
	 * and set the blood line{@link DrawBloodbBar#draw(Graphics)}</li>
	 * <li>If the home is alive and blue, draw a blue crystal picture 
	 * and set the blood line{@link DrawBloodbBar#draw(Graphics)}</li>
	 * <li>If the home is not alive and is red, draw the "Victory" font and call the {@link Home#gameOver(Graphics)} function.</li>
	 * <li>If the home is not alive and is blue, draw the "Defeat" font and call the {@link Home#gameOver(Graphics)} function.</li>
	 * </ul>
	 * @param g Brush tool
	 */
	public void draw(Graphics g) {

		if (live&&isRed()) {
			g.drawImage(homeImags[0], x, y, null);
			new DrawBloodbBar().draw(g);
		} 
		else if (live&&!isRed()) {
			g.drawImage(homeImags[1], x, y, null);
			new DrawBloodbBar().draw(g);
		} 
		if (!live&&!isRed()) {
			g.setColor(Color.pink);
			g.setFont(new Font(" ", Font.PLAIN, 40));
			g.drawString("Victory! ", 280, 250);
			gameOver(g);
		}
		else if(!live&&isRed()){
			g.setColor(Color.pink);
			g.setFont(new Font(" ", Font.PLAIN, 40));
			g.drawString("Defeat!", 280, 250);
			gameOver(g);

		}
	}
	
	/**
	 * <b><em>Return whether it is the red method</em></b>
	 * @return red
	 */
	public boolean isRed() {
		return red;
	}
	
	/**
	 * <b><em>Return home survival method</em></b>
	 * @return live
	 */
	public boolean isLive() { 
		return live;
	}

	/**
	 * <b><em>Set home survival method</em></b>
	 * @param live home survival
 	 */
	public void setLive(boolean live) { 
		this.live = live;
	}
	
	/**
	 * <b><em>Set home's health method</em></b>
	 * @param life HP
	 */
	public void setLife(int life) {
		this.HP = life;
	}
	
	/**
	 * <b><em>Return home's health method</em></b>
	 * @return HP
	 */
	public int getLife() {
		return HP;
	}
	
	/**
	 * <b><em>Blood strip</em></b>
	 */
	private class DrawBloodbBar {
		
		/**
		 *<b><em>Draw blood line</em></b> 
		 * @param g Brush Tool
		 */
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(getX(), getY()-5, 47, 5);
			int w = 47 * HP / 200;
			g.fillRect(getX(), getY()-5, w, 5);
			g.setColor(c);
		}
	}
	
	/**
	 * <b><em>Rectangular instance</em></b>
	 * <p>Each interface element has a rectangular instance that is used 
	 * to determine if two interface elements are touching each 
	 * other (the rectangles intersect)
	 * @return Rectangular position size
	 */
	public Rectangle getRect() { 
		return new Rectangle(x, y, width, length);
	}

	/**
	 * <b><em>Return x method</em></b>
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * <b><em>Set the x method</em></b>
	 * @param x Home's abscissa
	 */
	public void setX(int x) {
		this.x = x;
	}

/**
	 * <b><em>Retur Home ordinate</em></b>
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * <b><em>Set Home ordinate</em></b>
	 * @param y Home ordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
}
