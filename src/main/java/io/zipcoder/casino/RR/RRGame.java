package io.zipcoder.casino.RR;

import io.zipcoder.casino.DiceGame;
import io.zipcoder.casino.Menus.RRMenu;
import io.zipcoder.casino.Player;
import io.zipcoder.casino.Services.GameServices;
import io.zipcoder.casino.utilities.Console;
import io.zipcoder.casino.Interfaces.Game;
import io.zipcoder.casino.Utility.Music;

public class RRGame extends DiceGame implements Game {


    public static void main(String[] args) throws InterruptedException {
        Player rrPlayer = new Player("Grace", "Bunde", 23, 500);
        RRGame rrGame = new RRGame(rrPlayer);

        rrGame.startPlay();
    }

    private Console console = new Console(System.in, System.out);
    private String name = "Russian Dice Roulette";
    private Integer userDieNum = 0;
    private GameServices gameServices = new GameServices();
    private Player player;
    Music rouletteMusic = null;
    Music bang = null;
    Music elFin = null;
    private Integer computersRoll;

    public Integer getUserDieNum() {
        return userDieNum;
    }

    public void setUserDieNum(Integer userDieNum) {
        this.userDieNum = userDieNum;
    }

    public Integer getComputersRoll() {
        return computersRoll;
    }

    public void setComputersRoll(Integer computersRoll) {
        this.computersRoll = computersRoll;
    }

    public RRGame(Player player) {
        this.player = player;
    }

    public String getName() {
        return name;
    }


    @Override
    public void startPlay() {

        try {
            io.zipcoder.casino.Utility.Music.filePath = "src/music/(Roulette) Kirby Star Allies Music.wav";
            rouletteMusic = new io.zipcoder.casino.Utility.Music();
            rouletteMusic.play();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

        // store menu
        RRMenu rrMenu = new RRMenu(this);
        rrMenu.displayMenu();
        rouletteMusic.stop();
    }

    @Override
    public void endChoice() {
        //implements menu whether you want to quit or go again

        String endChoiceInput = console.getInput(("\n[DEALER]: You have finished this Russian Roulette Game.\n[DEALER]: Would you like to play again? (Y/N)\n"));

        if (endChoiceInput.toUpperCase().equals("N")) {
            rouletteMusic.stop();

            console.printWithDelays("\n[DEALER]: Have a good rest of your day.\n");
            console.sleep(1200);

            //also, return to the main menu
        } else if (endChoiceInput.toUpperCase().equals("Y")) {

            console.clearScreen();
            roundOfPlay();

        } else {
            console.println("(That's not a valid selection. Please choose again.)");
            endChoice();
        }

    }

    @Override
    public void roundOfPlay() {
        console.println(displayUserBalance());
        computerRoll();
        console.println(houseRollDisplay(computersRoll));
        userRollsDice();
        console.println(userRollDisplay(userDieNum));
        if (userDieNum.equals(computersRoll)) {

            rouletteMusic.stop();

            gameServices.wager(player.getBalance(), player);
            console.println(printLosingMessage());
            console.sleep(2000);
            console.clearScreen();

            console.sleep(1500);
            console.printWithDelays(printDealersLosingMessage(), 80);
            console.printWithDelays(printPistol(), 2);
            try {
                io.zipcoder.casino.Utility.Music.filePath = "src/music/(bang) sound effect.wav";
                bang = new io.zipcoder.casino.Utility.Music();
                bang.play();
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
            console.sleep(1000);

            bang.stop();

            console.sleep(1000);

            console.clearScreen();
            try {
                io.zipcoder.casino.Utility.Music.filePath = "src/music/(el fin) Super Mario Bros. - Game Over Sound Effect.wav";
                elFin = new io.zipcoder.casino.Utility.Music();
                elFin.play();
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
            console.printWithDelays(printElFin(), 3);
            console.sleep(3000);

            elFin.stop();

            System.exit(0);

        } else {
            gameServices.payOut(this.player.getBalance(), this.player);
            console.println(displayWinnerBalance());
            console.sleep(1000);
            endChoice();


        }

    }

    public Integer userRollsDice() {
        console.getInput("\n(Press Enter to roll the die): \n");
        userDieNum = roll();
        return userDieNum;
    }


    public Integer computerRoll() {
        computersRoll = roll();
        return computersRoll;
    }

    public String houseRollDisplay(Integer computersRoll) {
        return String.format("------------------------------------------------------\n\n** The House rolled %d **\n" + DiceGame.diceToASCII(computersRoll), computersRoll);
    }

    public String userRollDisplay(Integer userDieNum) {
        return (String.format("------------------------------------------------------\n** You rolled %d **\n\n" + DiceGame.diceToASCII(userDieNum), userDieNum));
    }

    public String displayUserBalance() {
        return String.format("Your Current Balance Is %.2f", player.getBalance());
    }


    public String displayWinnerBalance() {
        return String.format("\n\nYou Won!!! Your Balance Is Now $%.2f\n", player.getBalance());
    }

    public String printElFin() {
        String elFinMessage = "   ,ggggggg,             ,gggggggggggggg                  \n" +
                " ,dP\"\"\"\"\"\"Y8b ,dPYb,    dP\"\"\"\"\"\"88\"\"\"\"\"\"                  \n" +
                " d8'    a  Y8 IP'`Yb    Yb,_    88                        \n" +
                " 88     \"Y8P' I8  8I     `\"\"    88      gg                \n" +
                " `8baaaa      I8  8'         ggg88gggg  \"\"                \n" +
                ",d8P\"\"\"\"      I8 dP             88   8  gg    ,ggg,,ggg,  \n" +
                "d8\"           I8dP              88      88   ,8\" \"8P\" \"8, \n" +
                "Y8,           I8P         gg,   88      88   I8   8I   8I \n" +
                "`Yba,,_____, ,d8b,_        \"Yb,,8P    _,88,_,dP   8I   Yb,\n" +
                "  `\"Y8888888 8P'\"Y88         \"Y8P'    8P\"\"Y88P'   8I   `Y8\n\n\n\n";
        return elFinMessage;
    }

    public String printPistol() {
        String pistol = "                                  \n\n\n" +
                "   )                               /=>\n" +
                "  (  +____________________/\\/\\___ / /|\n" +
                "   .''._____________'._____      / /|/\\\n" +
                "  : () :              :\\ ----\\|    \\ )\n" +
                "   '..'______________.'0|----|      \\\n" +
                "                    0_0/____/        \\\n" +
                "                        |----    /----\\\n" +
                "                       || -\\\\ --|      \\\n" +
                "                       ||   || ||\\      \\\n" +
                "                        \\\\____// '|      \\\n" +
                "      Bang!                     .'/       |\n" +
                "                               .:/        |\n" +
                "                               :/_________|";
        return pistol;
    }

    public String printLosingMessage() {
        String losingMessage = "\nYou Lost!!!";
        return losingMessage;
    }

    public String printDealersLosingMessage () {
       String dealersLosingMesage = "[DEALER]: Don't you know how Russian Roulette works?\n\n";
       return dealersLosingMesage;
    }
}


