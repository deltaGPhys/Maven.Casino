package io.zipcoder.casino.Menus;

import io.zipcoder.casino.GameObject;
import io.zipcoder.casino.Player;
import io.zipcoder.casino.Services.GameRepo;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class GameMenuTest {


    @Test
    public void mapSizeTest() {
        Player player = new Player("June", "Cleaver", 35, 0.00);

        GameMenu gameMenu = new GameMenu(player);
        GameRepo gameRepo = gameMenu.getGameRepo();
        HashMap<Integer,GameObject> map = gameMenu.getGameMap();

        Assert.assertEquals(GameRepo.NUM_ADULT_GAMES + GameRepo.NUM_KID_GAMES + 1, map.size());

    }

    @Test
    public void mapSizeTest2() {
        Player player = new Player("Beaver", "Cleaver", 13, 0.00);

        GameMenu gameMenu = new GameMenu(player);
        GameRepo gameRepo = gameMenu.getGameRepo();
        HashMap<Integer,GameObject> map = gameMenu.getGameMap();

        Assert.assertEquals(GameRepo.NUM_KID_GAMES + 1, map.size());
    }

    @Test
    public void printTestAdult() {
        Player player = new Player("June", "Cleaver", 35, 0.00);

        GameMenu gameMenu = new GameMenu(player);
        GameRepo gameRepo = gameMenu.getGameRepo();
//        gameMenu.displayMenu();
    }

    @Test
    public void printTestKid() {
        Player player = new Player("Beaver", "Cleaver", 13, 0.00);

        GameMenu gameMenu = new GameMenu(player);
        GameRepo gameRepo = gameMenu.getGameRepo();
//        gameMenu.displayMenu();
    }

    @Test
    public void printTestAdulttemp() {
        Player player = new Player("June", "Cleaver", 35, 0.00);

        GameMenu gameMenu = new GameMenu(player);
        GameRepo gameRepo = gameMenu.getGameRepo();
//        gameMenu.displayMenu();
//        gameMenu.handleChoice(2);
    }
}