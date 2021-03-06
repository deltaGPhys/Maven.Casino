package io.zipcoder.casino.GoFish;

import io.zipcoder.casino.Card;
import io.zipcoder.casino.CardGame;
import io.zipcoder.casino.CardSet;
import io.zipcoder.casino.Interfaces.Game;
import io.zipcoder.casino.Menus.GoFishMenu;
import io.zipcoder.casino.Player;
import io.zipcoder.casino.Utility.Music;
import io.zipcoder.casino.utilities.Console;

import java.util.ArrayList;

public class GoFishGame extends CardGame implements Game {


    private Console console = new Console(System.in, System.out);

    private String name = "Go Fish";
    private GoFishPlayer player;
    private GoFishNPC opponent;
    private CardSet shoe;
    private CardSet playersCards;
    private CardSet opponentsCards;
    private CardSet playerSuites;
    private CardSet opponentSuites;
    private Music fishMusic = null;
    private String actingPlayer;
    private ArrayList<Card> stolenCards;
    private String message = "";
    private Card fishedCard;



    public GoFishGame(Player player) {
        this.player = new GoFishPlayer(player);
        this.opponent = new GoFishNPC(new Player("Baity", "McSwitch", 55, 0));
        this.playersCards = new CardSet(0);
        this.opponentsCards = new CardSet(0);
        this.playerSuites = new CardSet(0);
        this.opponentSuites = new CardSet(0);
        this.shoe = new CardSet(1);
    }

    public static void main(String[] args) { // for testing
        Player player = new Player("Lem", "Jukes", 23, 300.00);
        GoFishGame goFishGame = new GoFishGame(player);
        goFishGame.startPlay();
    }

    public void setupGame(){
        startPlay();
    }

    //populates player deals hands
    public void startPlay() {
        resetGame();
        try {
            Music.filePath = "src/music/(Go Fish) Underwater Theme GuitarMarimba Cover - Super Mario Bros. 1.wav";
            fishMusic = new Music();
            fishMusic.play();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        new GoFishMenu().displayMenu();
        goTitleScreen();
        initialDeal();
        turn(this.player, this.opponent, this.playersCards, this.opponentsCards, this.playerSuites, this.opponentSuites);
    }

    public void initialDeal() {
        shoe.shuffle();
        for (int i = 0; i < 7; i++) {
            this.playersCards.addCard(this.shoe.removeFirstCard());
            this.opponentsCards.addCard(this.shoe.removeFirstCard());
        }
    }

    public CardSet integrateStolenCards(ArrayList<Card> stolenCards, CardSet hand) {
        System.out.println("SUCCESSFULLY TOOK " + stolenCards.size() + " " + stolenCards.get(0).getRank() + "'S");
        hand.addCards(stolenCards);
        return hand;
    }

    public Card drawCard(CardSet hand) {
        console.println("OoOoOoO  GO FISH!  OoOoOoO");
        Card fishedCard = shoe.removeFirstCard();
        hand.addCard(fishedCard);
        hand.sort();
        return fishedCard;
    }

    public void emptyHandDraw(CardSet hand) {
        if (hand.size() <= 0) {
            hand.addCard(shoe.removeFirstCard());
        }
    }

    public boolean scanForSuites(CardSet hand, CardSet currentPlayerSuites, String rankToCheck) {
        ArrayList<Card> suiteChecker = hand.removeRank(rankToCheck);
        if (suiteChecker.size() == 4) {
            console.println("NEW SUITE: " + suiteChecker.get(0));
            currentPlayerSuites.addCard(suiteChecker.get(0));
            return true;
        } else {
            hand.addCards(suiteChecker);
        }
        hand.sort();
        return false;
    }

    public void turn(GoFishPlayer playerUp, GoFishPlayer nextPlayer, CardSet playerUpCards, CardSet nextPlayerCards, CardSet playerUpSuites, CardSet nextPlayerSuites) {

        GoFishPlayer winStatus = checkForWin(playerUp, nextPlayer, playerUpSuites, nextPlayerSuites);
        announceWinner(winStatus);
        String cardChoice = "";
        fishedCard = null;
        if (winStatus == null) {
            emptyHandDraw(playerUpCards);
            console.clearScreen();
            displayStatus();
            cardChoice = playerUp.chooseCard(playerUpCards);
            actingPlayer = playerUp.getPlayer().getFirstName();
        }
        if (!cardChoice.equals("N") && winStatus == null) {
            stolenCards = nextPlayerCards.removeRank(cardChoice);

            if (stolenCards.size() > 0) { // successfully took from opponent
                integrateStolenCards(stolenCards, playerUpCards);
                // scan and get another turn
                scanForSuites(playerUpCards, playerUpSuites, cardChoice);
                message = createMessage(playerUp, stolenCards, cardChoice, fishedCard);
                turn(playerUp, nextPlayer, playerUpCards, nextPlayerCards, playerUpSuites, nextPlayerSuites);
            } else { // didn't guess correctly
                fishedCard = drawCard(playerUpCards);
                message = createMessage(playerUp, stolenCards, cardChoice, fishedCard);
                if (fishedCard.getRank().equals(cardChoice)) { // drew a helpful card
                    //scan and get another turn
                    scanForSuites(playerUpCards, playerUpSuites, cardChoice);
                    turn(playerUp, nextPlayer, playerUpCards, nextPlayerCards, playerUpSuites, nextPlayerSuites);
                } else { // did not draw
                    if (scanForSuites(playerUpCards, playerUpSuites, fishedCard.getRank())) { // ...got a suite from it, though
                        // go again
                        turn(playerUp, nextPlayer, playerUpCards, nextPlayerCards, playerUpSuites, nextPlayerSuites);
                    } else { // everything failed
                        // opponent's turn
                        turn(nextPlayer, playerUp, nextPlayerCards, playerUpCards, nextPlayerSuites, playerUpSuites);
                    }
                }
            }
        } else {
            fishMusic.stop();
        }
    }
    public GoFishPlayer checkForWin(GoFishPlayer playerUp, GoFishPlayer nextPlayer, CardSet playerUpSuites, CardSet nextPlayerSuites) {

        if (playerUpSuites.size() >= 7) {
            return playerUp;
        } else if (nextPlayerSuites.size() >= 7) {
            console.printWithDelays(nextPlayer.getPlayer().getFirstName() + " IS THE WINNER!!!!!!!!! \n");
            return nextPlayer;
        } else {
            return null;
        }
    }

    public void announceWinner(GoFishPlayer winner) {
        if (null != winner) {
            console.printWithDelays(winner.getPlayer().getFirstName() + " IS THE WINNER!!!!!!!!! \n");
            endChoice();
        }
    }
    @Override
    public void endChoice() {
        //implements menu whether you want to quit or go again

        String endChoiceInput = console.getInput(("\n[DEALER]: You have finished this Go Fish Game.\n[DEALER]: Would you like to play again? (Y/N)\n"));

        if (endChoiceInput.toUpperCase().equals("N")) {
            console.printWithDelays("\n[DEALER]: Enjoy the rest of your stay!!\n");
            console.sleep(1500);

            fishMusic.stop();

            //also, return to the main menu
        } else if (endChoiceInput.toUpperCase().equals("Y")) {
            console.clearScreen();
            startPlay();

        } else {
            console.println("(That's not a valid selection. Please choose again.)");
            endChoice();
        }
    }

    public void resetGame() {
        this.playersCards = new CardSet(0);
        this.opponentsCards = new CardSet(0);
        this.playerSuites = new CardSet(0);
        this.opponentSuites = new CardSet(0);
        this.shoe = new CardSet(1);
    }

    public String getName() {
        return name;
    }

    public GoFishPlayer getPlayer() {
        return player;
    }

    public GoFishNPC getOpponent() {
        return opponent;
    }

    public String createMessage(GoFishPlayer playerUp, ArrayList stolenCards, String cardChoice, Card fishedCard) {
        String msg = "";
        if (fishedCard != null) {
            if (playerUp instanceof GoFishNPC) {
                msg = "*******************************************************************\n" + playerUp.getPlayer().getFirstName() + " Asked for " + cardChoice + ".  Drew a card.";
            } else {
                msg = "*******************************************************************\n" + playerUp.getPlayer().getFirstName() + " Asked for " + cardChoice + ".  Drew " + fishedCard.toString();
            }
        } else {
            msg = "*******************************************************************\n" + playerUp.getPlayer().getFirstName() + " Asked for " + cardChoice + ".  Received " + stolenCards.size();
        }

        return msg;
    }

    public CardSet getPlayersCards() {
        return playersCards;
    }

    public CardSet getOpponentsCards() {
        return opponentsCards;
    }

    public CardSet getPlayerSuites() {
        return playerSuites;
    }

    public CardSet getOpponentSuites() {
        return opponentSuites;
    }

    public void displayStatus() {
        goTitleScreen();
        playersCards.sort();
        playerSuites.sort();
        console.println(displayOpponentHands());
        console.println(displayOpponentSuites());
        console.println(message);
        console.println(displayPlayerSuites());
        console.println(displayPlayerHands());
    }

    public String displayPreviousTurn(){
        if (actingPlayer != null){
        return "*******************************************************************\n"+ actingPlayer + " Asked for " +  ""  + ".  Received " + stolenCards.size();
    }
        return "GO FISH!";
    }

    public String displayPlayerSuites() {
        return "************************* PLAYER'S SUITES *************************\n" + playerSuites.toASCIISuite() + "\n";
    }

    public String displayPlayerHands() {
        return "************************** PLAYER'S HAND **************************\n" + playersCards.toASCII() + "\n";
    }

    public String displayOpponentHands() {
        return "************************* OPPONENT'S HAND *************************\n" + opponentsCards.toASCIIBlank() + "\n";
    }

    public String displayOpponentSuites() {
        return "************************ OPPONENT'S SUITES ************************\n" + opponentSuites.toASCIISuite() + "\n";
    }

    public String goTitleScreen() {
        console.println("\n   >===>                         >=>                      \n" +
                " >>    >=>                     >>     >>          >=>      \n" +
                ">=>            >=>           >=>> >>       >===>  >=>      \n" +
                ">=>          >=>  >=>          >=>   >=>  >=>     >=>>=>   \n" +
                ">=>   >===> >=>    >=>         >=>   >=>   >==>   >=>  >=> \n" +
                " >=>    >>   >=>  >=>          >=>   >=>     >=>  >>   >=> \n" +
                "  >====>       >=>             >=>   >=>  >=>>=>  >=>  >=> \n" +
                "                                                          \n");
        return "GO FISH!";
    }
}