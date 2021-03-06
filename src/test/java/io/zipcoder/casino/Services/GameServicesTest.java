package io.zipcoder.casino.Services;

import io.zipcoder.casino.Blackjack.BlackjackGame;
import io.zipcoder.casino.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameServicesTest {

    private Player player;
    private BlackjackGame blackjackGame;
    private GameServices gameServices;
    @Before
    public void setUp() throws Exception {
        player = new Player ("Jeff", "Steinbeck", 35, 200.00);
        gameServices = new GameServices();
    }

    @Test
    public void getWagerTest() {
        Assert.assertFalse(gameServices.wager(300.00, player));
    }

    @Test
    public void getWagerTest2() {
        Assert.assertTrue(gameServices.wager(100.00, player));
        Assert.assertEquals(100.00, player.getBalance(), .01);
    }

    @Test
    public void getWagerTest3() {
        Assert.assertTrue(gameServices.wager(199.00, player));
        Assert.assertEquals(1.00, player.getBalance(), .01);
    }

    @Test
    public void withdrawTest() {
        Assert.assertEquals(200.00, gameServices.withdraw(player), .01);
        Assert.assertEquals(0.00, player.getBalance(), .01);
    }

    @Test
    public void withdrawTest2() {
        player.setBalance(0.0);
        Assert.assertEquals(0.00, gameServices.withdraw(player), .01);
        Assert.assertEquals(0.00, player.getBalance(), .01);
    }

    @Test
    public void depositTest() {
        player.setBalance(0.0);
        Assert.assertEquals(0.00, gameServices.withdraw(player), .01);
        gameServices.deposit(100.0, player);
        Assert.assertEquals(100.00, player.getBalance(), .01);

    }

    @Test
    public void payOut() {
        gameServices.payOut(100.00, player);
        Assert.assertEquals(300.00, player.getBalance(), .01);
    }

    @Test
    public void payOut2() {
        gameServices.payOut(0.00, player);
        Assert.assertEquals(200.00, player.getBalance(), .01);
    }
}