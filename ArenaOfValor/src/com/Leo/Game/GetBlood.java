package com.Leo.Game;
import java.awt.*;
import java.util.Random;

/**
 * <em>血包类 </em>
 * <p>The blood bag will appear randomly on the battlefield. 
 * When the hero approaches the blood bag, the hero's health increases.
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 */
public class GetBlood {
	
	public static final int width = 36;
	public static final int length = 36;

	private int x, y;
	BattleField tc;
	private static Random r = new Random();

	int step = 0; 
	private boolean live = false;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bloodImags = null;
	static {
		bloodImags = new Image[] { tk.getImage(GetBlood.class
				.getResource("/com/Leo/Game/Images/Blood.gif")), };
	}

	private int[][] poition = { { 155, 196 }, { 500, 58 }, { 80, 340 },
			{ 99, 199 }, { 345, 456 }, { 123, 321 }, { 258, 413 } };

	/**
	 * <b><em>Method of drawing a bleeding packet</em></b>
	 * <p>Determine whether the generated random number (0-100) is greater than 98. If yes, move the blood packet draw (Graphics) to determine the blood bag survival status. If it does not survive, exit the method. If it survives, draw a blood bag g.drawImage(bloodImags) [0], x, y, null)
	 * @param g Brush tool
	 */
	public void draw(Graphics g) {
		if (r.nextInt(100) > 98) {
			this.live = true;
			move();
		}
		if (!live)
			return;
		g.drawImage(bloodImags[0], x, y, null);
	}

	/**
	 * <b><em>Blood bag moving method</em></b>
	 * <p>Determine the upcoming position of the blood bag according to the step value
	 * 
	 */
	private void move() {
		step++;
		if (step == poition.length) {
			step = 0;
		}
		x = poition[step][0];
		y = poition[step][1];
		
	}

	/**
	 * <b><em>Rectangular instance</em></b>
	 * @return Return a rectangle instance
	 */
	public Rectangle getRect() { 
		return new Rectangle(x, y, width, length);
	}

	/**
	 * <b><em>Determine whether it is alive, whether there is a blood bag</em></b>
	 * @return Blood bag survival status
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * <b><em>Set the survival status and set the existence of the blood package</em></b>
	 * @param live Blood bag survival status
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

}