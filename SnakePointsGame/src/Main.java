import Models.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SnakeBoard snakeBoard = new SnakeBoard(5,5);
        snakeBoard.StartGame(sc);
//        closing the resource
        sc.close();
    }
}