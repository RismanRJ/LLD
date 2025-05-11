package Models;

import java.util.Random;

public class Board {
     int boardSize;
     int currentPosition;
     Cell [] cells;
        Dice dice;
     public Board(int boardSize, int currentPosition, int noOfSides){
         this.boardSize= boardSize+1;
         this.cells = new Cell[this.boardSize];
         this.currentPosition = currentPosition;
         this.dice= new Dice(noOfSides);
         initializeBoard();
     }

     private void initializeBoard(){
         for(int i=1;i<boardSize;i++){
             cells[i] = new Cell(i,i,Cell_Type.NORMAL);
         }

         int[] SnakeRandomPosition = generateRandomPosition();
         int[] LadderRandomPosition = generateRandomPosition();

         cells[Math.min(SnakeRandomPosition[0],SnakeRandomPosition[1])] = new Cell(Math.min(SnakeRandomPosition[0],SnakeRandomPosition[1]),Math.min(SnakeRandomPosition[0],SnakeRandomPosition[1]), Cell_Type.SNAKE);
         cells[Math.max(SnakeRandomPosition[0],SnakeRandomPosition[1])] = new Cell(Math.min(SnakeRandomPosition[0],SnakeRandomPosition[1]),Math.max(SnakeRandomPosition[0],SnakeRandomPosition[1]), Cell_Type.SNAKE);
         cells[Math.min(LadderRandomPosition[0],LadderRandomPosition[1])] = new Cell(Math.min(LadderRandomPosition[0],LadderRandomPosition[1]), Math.max(LadderRandomPosition[0],LadderRandomPosition[1]), Cell_Type.LADDER);
         cells[Math.max(LadderRandomPosition[0],LadderRandomPosition[1])] = new Cell(Math.max(LadderRandomPosition[0],LadderRandomPosition[1]), Math.min(LadderRandomPosition[0],LadderRandomPosition[1]), Cell_Type.LADDER);
         cells[boardSize-1]= new Cell(boardSize-1,boardSize-1,Cell_Type.TARGET);
     }

    private int[] generateRandomPosition(){
         Random random= new Random();
         int start = random.nextInt(boardSize);
         int end = random.nextInt(boardSize);

         return new int[]{start,end};
    }
     public void printBoard(){
         System.out.println();
         for(int i=1;i<boardSize;i++){

             if(cells[i].type== Cell_Type.TARGET){
                 System.out.print("T ");
             }
             else if(cells[i].type == Cell_Type.SNAKE  && (cells[i].start==i || cells[i].end ==i)){
                 System.out.print("S ");
             }
             else if(cells[i].type == Cell_Type.LADDER  && (cells[i].start==i || cells[i].end ==i)){
                 System.out.print("L ");
             }
             else if(cells[i].type==Cell_Type.TARGET){
                 System.out.println("T ");
             }
             else if(i==currentPosition){
                 System.out.print("@ ");
             }
             else{
                 System.out.print(". ");
             }
             if((i)%10==0) System.out.println();
         }
         try{
         Thread.sleep(200);
         }
         catch (Exception e){
             System.out.println(e.getMessage());
         }

         System.out.println();
     }

     private void updateBoard(int positon){
         currentPosition =positon;
     }

     public boolean movePiece(){
        int nextPosition = dice.getNextPosition();
         System.out.println("Dice got "+ nextPosition);
        if(currentPosition+nextPosition>= boardSize) return  false;


        nextPosition+=currentPosition;

        if(cells[nextPosition].type == Cell_Type.TARGET){
            updateBoard(nextPosition);
            return true;
        }

        else if(cells[nextPosition].type == Cell_Type.SNAKE){
            if(nextPosition == cells[nextPosition].end){
                System.out.println("Hit by Snake.... Moving to the Snake's Tail ");
                nextPosition = cells[nextPosition].start;
            }
        }
        else if(cells[nextPosition].type ==Cell_Type.LADDER){
            if(nextPosition == cells[nextPosition].start){
                System.out.println("Reached the Ladder.... Moving to the Ladder's Head");
                nextPosition = cells[nextPosition].end;
            }
        }

         updateBoard(nextPosition);

        return  false;
     }
}
