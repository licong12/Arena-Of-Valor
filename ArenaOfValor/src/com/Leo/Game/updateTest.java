package com.Leo.Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class updateTest {

	@Test
	void test() {
		BattleField battleField=new BattleField();
		for(int i=0;i<9;i++) {
			battleField.heros.get(i).move();
			battleField.heros.get(i).fire();
		}
		battleField.MyHero.move();
		battleField.MyHero.MasterFire();
		battleField.repaint();
	}

}
