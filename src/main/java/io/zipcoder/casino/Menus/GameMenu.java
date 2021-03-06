package io.zipcoder.casino.Menus;

import io.zipcoder.casino.GameObject;
import io.zipcoder.casino.Interfaces.Menu;
import io.zipcoder.casino.Utility.Music;
import io.zipcoder.casino.Player;
import io.zipcoder.casino.Services.GameRepo;
import io.zipcoder.casino.utilities.Console;

import java.util.HashMap;

public class GameMenu implements Menu {

    //maps the choice number to the name of the game
    private HashMap<Integer, GameObject> gameMap;
    private GameRepo gameRepo;
    private Console console;
    private String name = "Game Menu";

    Music mainMusic = null;

    public GameMenu(Player player) {
        this.gameRepo = new GameRepo(player);
        this.gameMap = gameRepo.getGamesMap();
        this.console = new Console(System.in, System.out);
    }

    public HashMap<Integer, GameObject> getGameMap() {
        return gameMap;
    }

    public GameRepo getGameRepo() {
        return gameRepo;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void displayMenu() {

        try {
            Music.filePath = "src/music/(Menu) All of Me Instrumental.wav";
            mainMusic = new Music();
            mainMusic.play();
        } catch (Exception ex) {
            System.out.println("Error playing sound.");
            ex.printStackTrace();
        }
        console.clearScreen();
        console.println("\n" +
                "               .........\n" +
                "              :~, *   * ~,\n" +
                "              : ~, *   * ~.\n" +
                "              :  ~........~\n" +
                "              : *:         :      ~'~,\n" +
                "              :  :         :    ~' *  ~,\n" +
                "              ~* :    *    : ,~' *    * ~,\n" +
                "               ~,:         :.~,*    *  ,~ :\n" +
                "                ~:.........::  ~, *  ,~   :\n" +
                "                            : *  ~,,~  *  :\n" +
                "                            :* * * :  *   :\n" +
                "                             ~, *  : *  ,~\n" +
                "                               ~,  :  ,~\n" +
                "                                 ~,:,~\n");

        for (int gameNum : gameMap.keySet()) {
            console.println(String.format("%d: %s", gameNum, ((GameObject) gameMap.get(gameNum)).getName()));
        }

        handleChoice(console.menuChoice(gameMap.size()));

        displayMenu();
    }

    @Override
    public void handleChoice(int choice) {

        mainMusic.stop();

        gameMap.get(choice).startPlay();
    }

}
