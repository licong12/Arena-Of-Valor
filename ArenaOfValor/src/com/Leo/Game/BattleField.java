package com.Leo.Game;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * <em><b>Battlefield interface class</b></em>
 * <p>Graphical user interface, all the elements of the game can 
 * be used for the interface, the interface can accept the user's 
 * operation, with human-computer interaction. Users can choose to
 *  restart the game, quit the game, pause the game and find game 
 *  help.
 * <p>The interface contains heroes, trees, rivers, barrier walls 
 * and "homes" that game users want to protect.
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 */
public class BattleField extends Frame implements ActionListener {

	private static Logger logger = Logger.getLogger(BattleField.class);
	
	private static final long serialVersionUID = 1L;
	public static final int Fram_width = 800; 
	public static final int Fram_length = 600;
	public static boolean printable = true;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	static Timer time = new Timer("游戏时间");  

	private static Image[] map= null;
	static {
			map=new Image[] {
					tk.getImage(BattleField.class.getClassLoader().getResource(
						"com/Leo/Game/Images/map.jpg")), };
	}
    
	MenuBar jmb = null;
	Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null;
	MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
			jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null;
	Image screenImage = null;

	Master MyHero = new Master(300, 560, true, false, Direction.STOP, this);
	Home RedHome = new Home(50, 550,true, this);
	Home BlueHome = new Home(700, 55,false, this);
	GetBlood blood = new GetBlood();
	
	List<Hero> heros = new ArrayList<Hero>();
	List<BombHero> bombHeros = new ArrayList<BombHero>();
	List<Bullets> bullets = new ArrayList<Bullets>();
	List<Balls> balls= new ArrayList<Balls>();
	List<MetalWall> metalWall = new ArrayList<MetalWall>();

	/**
	 * <b><em>Game interface dynamic update method</em></b>
	 * <p>Since the picture is still, and the picture requirements 
	 * of our game are dynamic, the tanks and bullets in the game 
	 * are constantly moving, and the ordinary wall that can be 
	 * penetrated is also “moving”, so for the picture, it must 
	 * be continuous. Update, redraw to produce dynamic effects.
	 **/
	public void update(Graphics g) {

		screenImage = this.createImage(Fram_width, Fram_length);
		Graphics graphics = screenImage.getGraphics();
		Color c = graphics.getColor();
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, Fram_width, Fram_length);
		graphics.setColor(c);
		framPaint(graphics);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	/**
	 *<b><em>Game interface drawing method</em></b>
	 *<p>Heroes, rivers, trees, homes, walls, etc. are all called 
	 *by the draw function in their respective classes.
	 *@param g Brush tool
	 */
	public void framPaint(Graphics g) {

		draw(g);
		RedHome.draw(g); 
		BlueHome.draw(g);
		MyHero.draw(g);
		MyHero.eat(blood);
		
		for (int i = 0; i < bullets.size(); i++) {
			Bullets m = bullets.get(i);
			m.hitHeros(heros);
			m.hitHero(MyHero); 
			m.hitHome(RedHome);
			m.hitHome(BlueHome); 

			for (int j = 0; j < metalWall.size(); j++) {
				MetalWall mw = metalWall.get(j);
				m.hitWall(mw);
			}
			m.draw(g);
		}
		for (int i = 0; i < balls.size(); i++) { 
			Balls m = balls.get(i);
			m.hitHeros(heros); 
			m.hitHero(MyHero); 
			m.hitHome(RedHome);
			m.hitHome(BlueHome); 

			for (int j = 0; j < metalWall.size(); j++) {
				MetalWall mw = metalWall.get(j);
				m.hitWall(mw);
			}
			m.draw(g);
		}
		for (int i = 0; i < heros.size(); i++) {
			Hero hero = heros.get(i);
			for (int j = 0; j < metalWall.size(); j++) { 
				MetalWall mw = metalWall.get(j);
				hero.collideWithWall(mw);
//				mw.draw(g);s
			}
			hero.collideWithHeros(heros); 
			hero.collideHome(RedHome);
			hero.collideHome(BlueHome);
			hero.draw(g);
		}

		blood.draw(g);
		for (int i = 0; i < bombHeros.size(); i++) {
			BombHero bombHero = bombHeros.get(i);
			bombHero.draw(g);
		}

//		for (int i = 0; i < metalWall.size(); i++) { 
//			MetalWall mw = metalWall.get(i);
//			mw.draw(g);
//		}

		MyHero.collideWithHeros(heros);
		MyHero.collideHome(RedHome);
		MyHero.collideHome(BlueHome);

		for (int i = 0; i < metalWall.size(); i++) {
			MetalWall w = metalWall.get(i);
			MyHero.collideWithWall(w);
//			w.draw(g);
		}

	}
	/**
	 *<b><em> Interface background drawing method</em></b> 
	 * @param g Brush Tool
	 */
	public void draw(Graphics g) {
		g.drawImage(map[0],0, 0, null);     
	}
	/**
	 * <b><em>How to add form menus and game elements </em></b>
	 */
	public BattleField() {
		jmb = new MenuBar();
		jm1 = new Menu("Game");
		jm2 = new Menu("Stop/Continue");
		jm3 = new Menu("Help");
		jm4 = new Menu("Game Level");
		jm1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm4.setFont(new Font("TimesRoman", Font.BOLD, 15));

		jmi1 = new MenuItem("New Game");
		jmi2 = new MenuItem("Exit");
		jmi3 = new MenuItem("Stop");
		jmi4 = new MenuItem("Continue");
		jmi5 = new MenuItem("Game instruction");
		jmi6 = new MenuItem("Level1");
		jmi7 = new MenuItem("Level2");
		jmi8 = new MenuItem("Level3");
		jmi9 = new MenuItem("Level4");
		jmi1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi5.setFont(new Font("TimesRoman", Font.BOLD, 15));

		jm1.add(jmi1);
		jm1.add(jmi2);
		jm2.add(jmi3);
		jm2.add(jmi4);
		jm3.add(jmi5);
		jm4.add(jmi6);
		jm4.add(jmi7);
		jm4.add(jmi8);
		jm4.add(jmi9);

		jmb.add(jm1);
		jmb.add(jm2);

		jmb.add(jm4);
		jmb.add(jm3);

		jmi1.addActionListener(this);
		jmi1.setActionCommand("NewGame");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("Stop");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Continue");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("help");
		jmi6.addActionListener(this);
		jmi6.setActionCommand("level1");
		jmi7.addActionListener(this);
		jmi7.setActionCommand("level2");
		jmi8.addActionListener(this);
		jmi8.setActionCommand("level3");
		jmi9.addActionListener(this);
		jmi9.setActionCommand("level4");

		this.setMenuBar(jmb);
		this.setVisible(true);

		
		for (int i = 0; i < 10; i++) {
			metalWall.add(new MetalWall(0, 60 * i, this));
			metalWall.add(new MetalWall(760, 60 * i, this));
			metalWall.add(new MetalWall(460-30*i, 83+40*i, this));
			metalWall.add(new MetalWall(550-30*i, 160+40*i, this));
		}
		
		for (int i = 0; i < 9; i++) { 
			if (i < 5) 
				heros.add(new Shooter(430+70 * i, 80, false, true, Direction.D, this));
			else
				heros.add(new Master(150*(i-4), 520, true, true, Direction.D,this));
		}
		
		this.setSize(Fram_width, Fram_length); 
		this.setLocation(280, 50);
		this.setTitle("Arean OF Valor ");

		this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.setVisible(true);

		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start(); 
		time.Reset(this);
		time.Continue(this);
		time.pack();  
		time.setVisible(true);
	}

	public static void main(String[] args) {
		BattleField battleField=new BattleField();	
		logger.info("Info");
		
		logger.debug("Debug");
		
	}

	/**
	 * <em>Draw thread inherits Runnable interface</em>
	 * <p>Redraw the thread of the game interface element
	 * 
	 * @author Leo
	 * @version 2.0
	 * @since 2018.11.20
	 *
	 */
	private class PaintThread implements Runnable {
		public void run() {
			while (printable) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * <em>键盘监视器</em>
	 * @author Leo
	 * @version 2.0
	 * @since 2018.11.20
	 *
	 */
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) { 
			MyHero.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) { 
			MyHero.keyPressed(e);
		}
	}
	
	/**
	 * <b><em>Method of listening to menu items</em></b>
	 * <ul>
	 * <li>Determine if the mouse clicks on "new game", if so, pause the thread  
	 * {@code printable = false}，Pop-up message box (OK or Cancel)，
	 * If you click OK, release the local screen resources  {@code this.dispose()}，
	 * And start a new thread  {@code new BattleField()}
	 * If you click Cancel, continue the thread  {@code printable = true}</li>
	 * <li>Determine if the mouse clicks "pause", and if so, pause the thread 
	 * {@code printable = false}</li>
	 * <li>Determine if the mouse clicks "Continue", and if so, continue the thread 
	 *  {@code printable = true}</li>
	 * <li>Determine if the mouse clicks "Exit", and if so, pause the thread  
	 * {@code printable = false}，A pop-up message box ("Are you sure you want to 
	 * quit the game?" OK or Cancel),
	 * If you click OK, exit the program  {@code System.exit(0)}
	 * If you click Cancel, continue the thread {@code printable = true}</li>
	 * <li>Determine if the mouse clicks "Help" - "Game Description", if so, pause 
	 * the thread {@code printable = false}，Pop up the game description;
	 * If you click OK, continue the thread {@code printable = true}</li>
	 * <li>Determine if the mouse clicks "game level", and if so, release the native 
	 * screen resource {@code this.dispose()}，Change the game speed depending on the 
	 * level of the game and Start a new thread {@code new BattleField()}</li>
	 * </ul>
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("NewGame")) {
			printable = false;
			time.Stop(this);
			Object[] options = { "OK", "Cancel" };
			int response = JOptionPane.showOptionDialog(this, "Are you sure you want to start a new game?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				printable = true;
				this.dispose();
				new BattleField();
			} 
			else {
				printable = true;
				time.Continue(this);
				new Thread(new PaintThread()).start(); 
			}
		}
		else if (e.getActionCommand().endsWith("Stop")) {
			printable = false;
			time.Stop(this);
			
		} 
		else if (e.getActionCommand().equals("Continue")) {
			if (!printable) {
				printable = true;
				time.Continue(this);
				new Thread(new PaintThread()).start();
			}
		} 
		else if (e.getActionCommand().equals("Exit")) {
			printable = false;
			time.Stop(this);
			Object[] options = { "OK", "Cancel" };
			int response = JOptionPane.showOptionDialog(this, "Are you sure you want to quit?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.out.println("Exit");
				System.exit(0);
			} 
			else {
				printable = true;
				time.Continue(this);
				new Thread(new PaintThread()).start();
			}
		}
		else if (e.getActionCommand().equals("help")) {
			printable = false;
			time.Stop(this);
			JOptionPane.showMessageDialog(null, "Use → ← ↑ ↓ to control the direction, F keyboard general attack, R restarts!",
					"Tip！", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			time.Continue(this);
			new Thread(new PaintThread()).start(); 
		}
		else if (e.getActionCommand().equals("level1")) {
			Hero.count = 12;
			Hero.speedX = 6;
			Hero.speedY = 6;
			Bullets.speedX = 10;
			Bullets.speedY = 10;
			Balls.speedX = 10;
			Balls.speedY = 10;
			this.dispose();
			new BattleField();
		}
		else if (e.getActionCommand().equals("level2")) {
			Hero.count = 12;
			Hero.speedX = 10;
			Hero.speedY = 10;
			Bullets.speedX = 12;
			Bullets.speedY = 12;
			Balls.speedX = 12;
			Balls.speedY = 12;
			this.dispose();
			new BattleField();

		}
		else if (e.getActionCommand().equals("level3")) {
			Hero.count = 20;
			Hero.speedX = 14;
			Hero.speedY = 14;
			Bullets.speedX = 16;
			Bullets.speedY = 16;
			Balls.speedX = 16;
			Balls.speedY = 16;
			this.dispose();
			new BattleField();
		}
		else if (e.getActionCommand().equals("level4")) {
			Hero.count = 20;
			Hero.speedX = 16;
			Hero.speedY = 16;
			Bullets.speedX = 18;
			Bullets.speedY = 18;
			Balls.speedX = 18;
			Balls.speedY = 18;
			this.dispose();
			new BattleField();
		}
	}
}
