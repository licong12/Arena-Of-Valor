package com.Leo.Game;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SelectHero extends JFrame implements ActionListener{
	
	JPanel jPanel,RedjPanel,BluejPanel;
	JButton AnQiLa_Button,YaSe_Button,PeiQinHu_Button,DaQiao_Button,LuBanQiHao_Button,Start_Button;
	JLabel []Heros=new JLabel[10];
	Icon AnQiLa_Icon,YaSe_Icon,PeiQinHu_Icon,DaQiao_Icon,LuBanQiHao_Icon;
	public static final int Fram_width = 600; 
	public static final int Fram_length = 300;
	BattleField battleField;
	static List<Hero> heros = new ArrayList<Hero>();
	Hero myHero;
	protected int HeroNum=0;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] HeroImags= null;
	static {
			HeroImags=new Image[] {
					tk.getImage(SelectHero.class.getClassLoader().getResource("com/Leo/Game/Images/AnQiLa.jpg")),
					tk.getImage(SelectHero.class.getClassLoader().getResource("com/Leo/Game/Images/YaSe.jpg")),
					tk.getImage(SelectHero.class.getClassLoader().getResource("com/Leo/Game/Images/PeiQinHu.jpg")),
					tk.getImage(SelectHero.class.getClassLoader().getResource("com/Leo/Game/Images/DaQiao.jpg")),
					tk.getImage(SelectHero.class.getClassLoader().getResource("com/Leo/Game/Images/LuBanQiHao.jpg"))};
	}
	
	public SelectHero() {
		battleField=new BattleField();
		jPanel=new JPanel();
		RedjPanel=new JPanel();
		RedjPanel.setLayout(new BoxLayout(RedjPanel,BoxLayout.Y_AXIS));
		BluejPanel=new JPanel();
		BluejPanel.setLayout(new BoxLayout(BluejPanel,BoxLayout.Y_AXIS));
		
		AnQiLa_Button=new JButton(" ");
		YaSe_Button=new JButton(" ");
		PeiQinHu_Button=new JButton(" ");
		DaQiao_Button=new JButton(" ");
		LuBanQiHao_Button=new JButton(" ");
		Start_Button=new JButton("Start");
		
		AnQiLa_Icon=new ImageIcon(HeroImags[0]);
		YaSe_Icon=new ImageIcon(HeroImags[1]);
		PeiQinHu_Icon=new ImageIcon(HeroImags[2]);
		DaQiao_Icon=new ImageIcon(HeroImags[3]);
		LuBanQiHao_Icon=new ImageIcon(HeroImags[4]);
		
		AnQiLa_Button.setIcon(AnQiLa_Icon);
		YaSe_Button.setIcon(YaSe_Icon);
		PeiQinHu_Button.setIcon(PeiQinHu_Icon);
		DaQiao_Button.setIcon(DaQiao_Icon);
		LuBanQiHao_Button.setIcon(LuBanQiHao_Icon);

		AnQiLa_Button.addActionListener(this);
		AnQiLa_Button.setActionCommand("AnQiLa");
		YaSe_Button.addActionListener(this);
		YaSe_Button.setActionCommand("YaSe");
		PeiQinHu_Button.addActionListener(this);
		PeiQinHu_Button.setActionCommand("PeiQinHu");
		DaQiao_Button.addActionListener(this);
		DaQiao_Button.setActionCommand("DaQiao");
		LuBanQiHao_Button.addActionListener(this);
		LuBanQiHao_Button.setActionCommand("LuBanQiHao");
		Start_Button.addActionListener(this);
		Start_Button.setActionCommand("Start");
		Start_Button.setEnabled(false);
		
		jPanel.add(AnQiLa_Button);
		jPanel.add(YaSe_Button);
		jPanel.add(PeiQinHu_Button);
		jPanel.add(DaQiao_Button);
		jPanel.add(LuBanQiHao_Button);
		
		this.add(Start_Button,BorderLayout.SOUTH);
		for(int i=0;i<10;i++) {
			Heros[i] = new JLabel();
			if(i<5)
				RedjPanel.add(Heros[i]);
			else
				BluejPanel.add(Heros[i]);
		}
		
		this.add(jPanel,BorderLayout.CENTER);
		this.add(RedjPanel,BorderLayout.LINE_START);
		this.add(BluejPanel,BorderLayout.LINE_END);
		this.setSize(Fram_width, Fram_length); 
		this.setLocation(280, 50);
		this.setTitle("Arean Of Valor ");
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(HeroNum==9) {
			AnQiLa_Button.setEnabled(false);
			YaSe_Button.setEnabled(false);
			PeiQinHu_Button.setEnabled(false);
			DaQiao_Button.setEnabled(false);
			LuBanQiHao_Button.setEnabled(false);
			Start_Button.setEnabled(true);
//			battleField=new BattleField();
			battleField.MyHero=this.myHero;
			battleField.heros=this.heros;
		}
		if(e.getActionCommand().equals("AnQiLa")) {
			if(HeroNum==0)
				myHero=new Master(40 + 70 , 520 , true, false, Direction.D, battleField);
			else if(HeroNum<5)
				heros.add(new Master(40 + 70 * (HeroNum+1), 520, true, true, Direction.D, battleField));
			else
				heros.add(new Master(430 + 70 * (HeroNum-4), 80, false, true, Direction.D, battleField));
			Heros[HeroNum++].setIcon(AnQiLa_Icon);
		}
		else if(e.getActionCommand().equals("YaSe")) {
			if(HeroNum==0)
				myHero=new Tank(40 + 70 , 520 , true, false, Direction.D, battleField);
			else if(HeroNum<5)
				heros.add(new Tank(40 + 70 * (HeroNum+1), 520, true, true, Direction.D, battleField));
			else
				heros.add(new Tank(430 + 70 * (HeroNum-4), 80, false, true, Direction.D, battleField));
			Heros[HeroNum++].setIcon(YaSe_Icon);
		}
		else if(e.getActionCommand().equals("PeiQinHu")) {
			if(HeroNum==0)
				myHero=new MESSI(40 + 70 , 520 , true, false, Direction.D, battleField);
			else if(HeroNum<5)
				heros.add(new MESSI(40 + 70 * (HeroNum+1), 520, true, true, Direction.D, battleField));
			else
				heros.add(new MESSI(430 + 70 * (HeroNum-4), 80, false, true, Direction.D, battleField));
			Heros[HeroNum++].setIcon(PeiQinHu_Icon);
		}
		else if(e.getActionCommand().equals("DaQiao")) {
			if(HeroNum==0)
				myHero=new Assistant(40 + 70 , 520 , true, false, Direction.D, battleField);
			else if(HeroNum<5)
				heros.add(new Assistant(40 + 70 * (HeroNum+1), 520, true, true, Direction.D, battleField));
			else
				heros.add(new Assistant(430 + 70 * (HeroNum-4), 80, false, true, Direction.D, battleField));
			Heros[HeroNum++].setIcon(DaQiao_Icon);
		}
			
		else if(e.getActionCommand().equals("LuBanQiHao")) {
			if(HeroNum==0)
				myHero=new Shooter(40 + 70 , 520 , true, false, Direction.D, battleField);
			else if(HeroNum<5)
				heros.add(new Shooter(40 + 70 * (HeroNum+1), 520, true, true, Direction.D, battleField));
			else
				heros.add(new Shooter(430 + 70 * (HeroNum-4), 80, false, true, Direction.D, battleField));
			Heros[HeroNum++].setIcon(LuBanQiHao_Icon);
		}
			
		if(e.getActionCommand().equals("Start")) {
			this.setVisible(false);
			battleField.Start();
		}
			
	}
	
	public static void main(String[] args) {
		new SelectHero();
	}

	
}
