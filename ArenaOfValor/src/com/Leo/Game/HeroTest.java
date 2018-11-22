package com.Leo.Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class HeroTest {

	@Test
	public void testShooter() throws Exception{
		Shooter hero=new Shooter(60, 60, true, true);
		System.out.println(hero.getX());
		System.out.println(hero.getY());
		hero.move();
		System.out.println(hero.getX());
		System.out.println(hero.getY());
	}
	@Test
	public void testMaster() throws Exception{
		Master hero=new Master(20, 20, true, true);
		System.out.println(hero.getX());
		System.out.println(hero.getY());
		hero.move();
		System.out.println(hero.getX());
		System.out.println(hero.getY());
	}
}
