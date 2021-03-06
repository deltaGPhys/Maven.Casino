package io.zipcoder.casino;

import java.util.Arrays;
import java.util.Random;

public abstract class DiceGame extends GameObject {


    //Rolls any number of dice with any number of sides
    public static int roll (int numberOfDiceBeingRolled, int numOfSides)
    {
        Integer dieRoll1 = 0;
        for (int i = 1; i <= numberOfDiceBeingRolled; i++) {
            dieRoll1 += (int) (Math.random() * numOfSides + 1);
        }
        return dieRoll1;
    }

    //Rolls just one die with 6 sides
    public static int roll () {

        return roll(1,6);
    }

    public static int roll (int numberOfDiceBeingRolled){
        return roll(numberOfDiceBeingRolled, 6);
    }

    public static String diceToASCII(int... dice) {
        int numDice = dice.length;
        String[] emptyArray = new String[numDice+1];
        Arrays.setAll(emptyArray,(index) -> "");

        String output = String.join("   ________  ", emptyArray);
        output += "\n";
        output += String.join("  /       /| ", emptyArray);
        output += "\n";
        output += String.join(" /       / | ", emptyArray);
        output += "\n";
        output += String.join("\u250C-------\u2510  | ", emptyArray);
        output += "\n";
        for (int num : dice) {
            if (num >= 4) {
                output += "| \u2022   \u2022 |  / ";
            } else if (num > 1) {
                output += "| \u2022     |  / ";
            } else {
                output += "|       |  / ";
            }
        }
        output += "\n";
        for (int num : dice) {
            if (num % 2 == 1) {
                output += "|   \u2022   | /  ";
            } else if (num == 6) {
                output += "| \u2022   \u2022 | /  ";
            } else {
                output += "|       | /  ";
            }
        }
        output += "\n";
        for (int num : dice) {
            if (num >= 4) {
                output += "| \u2022   \u2022 |/   ";
            } else if (num > 1) {
                output += "|     \u2022 |/   ";
            } else {
                output += "|       |/   ";
            }
        }
        output += "\n";
        output += String.join("\u2514-------\u2518    ", emptyArray);
        output += "\n";
        return output;
    }


}
