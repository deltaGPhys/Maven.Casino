package io.zipcoder.casino.Blackjack;

import io.zipcoder.casino.CardGame;
import io.zipcoder.casino.Interfaces.GamblingGame;

import java.util.ArrayList;
import java.util.HashMap;

public class BlackjackGame extends CardGame implements GamblingGame {

    public static final HashMap<String, Integer> cardMap = null;
    private double minBet;
    private double maxBet;
    private BlackjackPlayer player;
    private BlackjackNPCPlayer dealer;
    private ArrayList<BlackjackHand> hands;
    private int numDecks;

    public BlackjackGame(double minBet, double maxBet, int numPlayers, BlackjackPlayer incomingPlayer) {
        this.minBet = minBet;
        this.maxBet = maxBet;
        this.player = incomingPlayer;
    }

    public void startPlay() {

    }

    public ArrayList<BlackjackHand> initialDeal() {
        return null;
    }

    public BlackjackNPCPlayer createDealer() {
        return null;
    }


    public void roundOfPlay() {

    }

    public void endChoice() {

    }

    public boolean checkDeck() {
        return false;
    }

    public void displayTable() {

    }

    // returns 0 if you lost, bet size if you pushed, 2x/2.5x bet size if you won
    public double calculateWinnings (BlackjackHand handToEvaluate) {
        return 0.0;
    }

    public double getWager() {
        return 0.0;
    }

    public void payOut(double amount) {

    }

    //    public ArrayList<Player> createNPCs () {
//        return null;
//    }

//    public ArrayList<Player> seatPlayers () {
//        return null;
//    }
//
//    public ArrayList<Player> modifyNPCRoster () {
//        return null;
//    }
}