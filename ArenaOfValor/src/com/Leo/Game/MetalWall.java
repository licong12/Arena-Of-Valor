package com.Leo.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * <em>Metal wall</em>
 * <p>The metal wall has the following attributes: the fixed {@link #length} 
 * and {@link #width} of the wall, the position coordinates of the wall ({@link #x},{@link #y}), 
 * and the wall is represented by the image added to the graphical user 
 * interface.
 * <p> There is a constructor for passing arguments, a {@link #draw(Graphics)} method to draw the wall at 
 * the specified position, and a getRect() method to construct the rectangle instance {@link #getRect()}.
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 *
 */
public class MetalWall {
	public static final int width = 30; 
	public static final int length = 30;
	private int x, y;
	BattleField tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("/com/Leo/Game/Images/metalWall.gif")), };
	}

	/**
	 * <b><em>Construction method of Metal wall</em></b>
	 * <p>Position parameter for the transfer wall
	 * @param x Metal wall upper left horizontal axis
	 * @param y Metal wall upper left ordinate
	 * @param battleField battleField
	 */
	public MetalWall(int x, int y, BattleField battleField) {
		this.x = x;
		this.y = y;
		this.tc = battleField;
	}

	/**
	 * <b><em>The method of drawing an Metal wall</em></b>
	 * @param g Brush tool
	 */
	public void draw(Graphics g) { // 鐢婚噾灞炲
		g.drawImage(wallImags[0], x, y, null);
	}

	/**
	 * <b><em>Rectangular instance</em></b>
	 * @return Return a rectangle instance
	 */
	public Rectangle getRect() { // 鏋勯�犳寚瀹氬弬鏁扮殑闀挎柟褰㈠疄渚�
		return new Rectangle(x, y, width, length);
	}
}