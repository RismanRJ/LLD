package Models;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    List<Player> players;
    Board board;

    private static boolean isGameOn=false;

    public Game(int boardSize, int noOfSides,List<String> players){
        this.board= new Board(boardSize,1, noOfSides);
        this.players= new ArrayList<Player>();
        AssignPlayer(players);  // initializePlayers
    }

    private void AssignPlayer(List<String> playersList){
        for(String player : playersList){
            players.add(new Player(player));
        }
    }

    public void startGame(Scanner sc){
        while(!isGameOn){
            //start the game

            for(Player player : players){
                System.out.println(player.userName + "'s turn");
                System.out.println("Type Any Key and Press Enter For the next Move");
                sc.next();
                boolean status =board.movePiece();
                System.out.println("Board Status Updating..");
                board.printBoard();
                if(status){
                    printWinner(player.userName);
                    isGameOn = true;
                }
            }
        }
    }


    public void printWinner(String userName){
        System.out.println(userName+" won the game");
    }





 }
