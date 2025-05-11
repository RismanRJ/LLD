package Models;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;


public class SnakeBoard {
   private final char[][] board;
   private int currentRow;
   private int currentCol;

   private Queue<int[]> Snakes = new LinkedList<>();
   private Queue<int[]> Foods = new LinkedList<>();
    
    public SnakeBoard(int row, int col){
        this.board = new char[row][col];
        intializeSnake();
        AssignFoodPositionAtRandom(Foods);
        this.currentCol=0;
        this.currentRow=0;
    }
    
    private void intializeSnake(){
        board[0][0]='.';
        Snakes.add(new int[]{0,0});
    }
    
    private void AssignFoodPositionAtRandom(Queue<int[]> T){
        int[] randomPosition = getNextPosition();
        Foods.add(randomPosition);
        board[randomPosition[0]][randomPosition[1]]= 'X';
        
    }
    
    private int[] getNextPosition(){
        Random random = new Random();
        int x = random.nextInt(board.length);
        int y = random.nextInt(board[0].length);
        
        return new int[] {x,y};
    }

    public void StartGame(Scanner sc){
        boolean isGameOn=  true;
        while(isGameOn){
            printBoard();
            System.out.println("Enter a Key\t 1)L\t 2)R \t 3)T \t 4)D \n");
            char move = sc.next().charAt(0);
            switch (move){
                case 'L':
                    //leftDirection
                    isGameOn= moveSnake(currentRow,--currentCol);
                    break;
                case 'R':
                    //RightDirection
                    isGameOn= moveSnake(currentRow,++currentCol);
                    break;
                case 'T':
                    //topDirection
                    isGameOn = moveSnake(--currentRow,currentCol);
                    break;
                case 'D':
                    //DownDirection
                    isGameOn = moveSnake(++currentRow,currentCol);
                    break;

                default:
                    System.out.println("Invalid Key Pressed");
                    System.exit(0);
                    isGameOn =false;
            }
        }

        System.out.println("Total Points Taken : "+ Snakes.size());
    }


    private boolean moveSnake(int row, int col){
        if(isValidMove(row,col)){
            currentRow=row;
            currentCol =col;
            if(board[row][col]!='X' && board[row][col]!='.'){
                int[] currentTailPosition = Snakes.poll();
                board[currentTailPosition[0]][currentTailPosition[1]]='\0';
                // updating snake Tail to Head Position
            }
            else if(board[row][col]=='X'){
                Foods.poll();
                AssignFoodPositionAtRandom(Foods);
            }
            else{
                System.out.println("Snake Bit itself .... Lose The Game");
                return false;
            }
            board[row][col]='.';
            printBoard();
            Snakes.add(new int[]{row,col});
            return true;
        }
        System.out.println("Snake Hit the Wall ... Lose the Game");
        return false;
    }

    private boolean isValidMove(int row, int col){
        return row>=0 && row<board.length && col>=0 && col<board[0].length;
    }


    private void printBoard(){
        try{
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");
        for(int i=0;i< board.length;i++){
            for(int j=0;j<board[0].length;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("-----------------------");
    }
}
