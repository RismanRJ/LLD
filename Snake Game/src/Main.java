import Models.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Size of the Board");
        int boardSize = sc.nextInt();
        System.out.println("Enter no of sides in the dice");
        int diceSides = sc.nextInt();
        System.out.println("Enter no of players in the game");
        int noOfPlayers = sc.nextInt();
        List<String>  players = new ArrayList<>();
        for(int i=0;i<noOfPlayers;i++){
            System.out.println("Enter Player name "+(i+1));
            players.add(sc.next());
        }
        Game game = new Game(boardSize,diceSides,players);

        game.startGame(sc);


        sc.close();
    }
}