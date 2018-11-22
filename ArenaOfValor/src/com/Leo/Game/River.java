package com.Leo.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * <em>River class</em>
 * <p>The river has the following attributes: the fixed {@link #riverLength} and width of the 
 * river{@link #riverWidth}, the location coordinates of the river({@link #x},{@link #y}), and 
 * the river is represented  by the image added to the graphical user interface.
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 *
 */ 
public class River {
	public static final int riverWidth = 40;
	public static final int riverLength = 100;
	private int x, y;
	BattleField battleField ;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] riverImags = null;
	
	static { 
		riverImags = new Image[]{
				tk.getImage(CommonWall.class.getResource("/com/Leo//Game/Images/river.jpg")),
		};
	}
	
	/**
	 * <b><em>River construction method</em></b>
	 * <p>Location parameter for passing rivers
	 * @param x The upper left axis of the river
	 * @param y River upper left ordinate
	 * @param battleField battleField 
	 */
	public River(int x, int y, BattleField battleField) {   
		this.x = x;
		this.y = y;
		this.battleField = battleField;           
	}
	
	/**
	 * <b><em>Method of drawing a river</em></b>
	 * @param g Brush tool
	 */
	public void draw(Graphics g) {
		g.drawImage(riverImags[0],x, y, null);     
	}
	
	/**
	 * <b><em>Return to river width method</em></b>
	 * @return riverWidth
	 */
	public static int getRiverWidth() {
		return riverWidth;
	}

	/**
	 * <b><em>Return to river length method</em></b>
	 * @return riverLength
	 */
	public static int getRiverLength() {
		return riverLength;
	}
	
	/**
	 * <b><em>Return to the river horizontal coordinate method</em></b>
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * <b><em>Set the river horizontal coordinate method</em></b>
	 * @param x The abscissa of the river
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * <b><em>Return river ordinate method
</em></b>
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param y The ordinate of the upper left of the river
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * <b><em>闀挎柟褰㈠疄渚�</em></b>
	 * @return 杩斿洖闀挎柟褰㈠疄渚�
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, riverWidth, riverLength);
	}


}
