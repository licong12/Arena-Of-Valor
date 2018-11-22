package com.Leo.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * <em>Common wall</em>
 * <p>Ordinary walls have the following attributes: the fixed length ({@link #length}) and 
 * width({@link #width}) of the wall, the position coordinates of the wall({@link #x},{@link #y}), and the wall 
 * is represented by the image added to the graphical user interface.
 * <p>There is a constructor for passing arguments, a {@code #draw(Graphics)} method to draw the wall at the specified position, 
 * and a {@code #getRect()} method to construct the rectangle instance.
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 *
 */
public class CommonWall {
	public static final int width = 20;
	public static final int length = 20;
	int x, y;

	BattleField battleField;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] {
		tk.getImage(CommonWall.class.getResource("/com/Leo/Game/Images/commonWall.gif")), };
	}
 
	/**
	 * <b><em>Construction method of common wall</em></b>
	 * <p>Position parameter for passing the wall
	 * @param x Common wall upper left horizontal coordinate
	 * @param y Common wall upper left ordinate
	 * @param battleField battleField
	 */
	public CommonWall(int x, int y, BattleField battleField) {
		this.x = x;
		this.y = y;
		this.battleField = battleField; 
	}

	/**
	 * <b><em>The method of drawing an common wall</em></b>
	 * @param g Brush tool
	 */
	public void draw(Graphics g) {
		g.drawImage(wallImags[0], x, y, null);
	}

	/**
	 * <b><em>Rectangular instance</em></b>
	 * @return Return a rectangle instance
	 */
	public Rectangle getRect() {  
		return new Rectangle(x, y, width, length);
	}
}