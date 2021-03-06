package io.zipcoder.casino;

import org.junit.Assert;
import org.junit.Test;

//import static org.junit.Assert.*;
import io.zipcoder.casino.utilities.Console;

public class DiceGameTest {

    private Console console = new Console(System.in, System.out);

    @Test
    public void testRoll2() {
        for (int i = 0; i < 1000; i++) {
            int expected = DiceGame.roll(2, 10);
            Assert.assertTrue((expected >= 2 && expected <= 20));
        }
    }

    @Test
    public void testRoll3() {
        for (int i = 0; i < 1000; i++) {
            int expected = DiceGame.roll(3, 8);
            Assert.assertTrue((expected >= 3 && expected <= 24));
        }
    }

    @Test
    public void testRoll() {
        for (int i = 0; i < 1000; i++) {
            int expected = DiceGame.roll(1, 6);
            Assert.assertTrue((expected >= 1 && expected <= 6));
        }
    }

    @Test
    public void testRoll1() {
        for (int i = 0; i < 1000; i++) {
            int expected = DiceGame.roll(3);
            Assert.assertTrue((expected >= 3 && expected <= 18));
        }
    }

    @Test
    public void testRoll4() {
        for (int i = 0; i < 1000; i++) {
            int expected = DiceGame.roll();
            Assert.assertTrue((expected >= 1 && expected <= 6));
        }
    }

    @Test
    public void ASCIITest() {
        console.println(DiceGame.diceToASCII(1,2,3,4,5,6));
    }
}

