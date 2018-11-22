package com.Leo.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * <strong>Tree class</strong>
 *<p>The tree has the following attributes: the fixed {@link #length} and {@link #width} h of the tree, the 
 *position coordinates of the tree ({@link #x},{@link #y}), and the tree is represented by the image added to 
 *the graphical user interface. 
 *<p> There is a constructor for passing arguments, a ({@link #draw(Graphics)}) method to draw the tree at the specified position, 
 *and a ({@link #getRect()}) method to construct the rectangle instance.
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 *
 */
public class Tree {
	public static final int width = 30;
	public static final int length = 30;
	int x, y;
	BattleField battleField ;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] treeImags = null;
	static {
		treeImags = new Image[]{
				tk.getImage(CommonWall.class.getResource("/com/Leo//Game/Images/tree.gif")),
		};
	}
	
	/**
	 * <b><em>Tree construction method</em></b>
	 * <p>Location parameters for passing the tree
	 * @param x The upper left axis of the tree
	 * @param y Tree upper left ordinate
	 * @param battleField battleField
	 */
	public Tree(int x, int y, BattleField battleField) {
		this.x = x;
		this.y = y;
		this.battleField = battleField;
	}
	
	/**
	 * <b><em>The method of drawing the tree</em></b>
	 * @param g Brush tool

	 */
	public void draw(Graphics g) {
		g.drawImage(treeImags[0],x, y, null);
	}
	
	/**
	 * <b><em>Rectangular instance</em></b>
	 * @return Return a rectangle instance
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}
}