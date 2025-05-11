package Models;

public class SudukoBoard {
    private int[][] sudukoBoard;
    private final int INNERROWDIMENSION= 3;
    private final int INNERCOLDIMENSTION =3;

    public SudukoBoard(int[][] board){
        this.sudukoBoard = board;
    }


    public boolean Solve(int row,int col){
        if(row ==sudukoBoard.length){
            col++;

            if(col== sudukoBoard.length) return true;
            else row=0; // start from next column and first row
        }

        //skip if the cell is already visited
        if(sudukoBoard[row][col]!=0) return Solve(row+1,col);

        for(int i=1;i<=sudukoBoard.length;i++){
            if(isValid(row,col,i)){
                sudukoBoard[row][col] = i;
                if(Solve(row+1,col)) return true; // if it reaches the end of the board
                //back track , if the number fails
                sudukoBoard[row][col] =0;
            }

        }

        return false;
    }


    private boolean isValid(int row, int col, int num){
        //checking column
        for(int i=0;i<sudukoBoard.length;i++){
            if(sudukoBoard[i][col]==num) return false;
        }


        //checking row
        for(int i=0;i< sudukoBoard.length;i++){
            if(sudukoBoard[row][i]==num) return false;
        }


        //checking inner 3 X 3 grid

        int currentRowGrid = (row/INNERROWDIMENSION)*INNERROWDIMENSION;
        int currentColGrid = (col/INNERCOLDIMENSTION)*INNERCOLDIMENSTION;

        for(int i=currentRowGrid;i<currentRowGrid+INNERROWDIMENSION;i++){
            for(int j=currentColGrid;j<currentColGrid+INNERCOLDIMENSTION;j++){
                if(sudukoBoard[i][j]==num) return false;
            }
        }



        return true;
    }

    public void printSudukoBoard(){
        System.out.println("-------------------------------------------");
        for(int i=0;i<sudukoBoard.length;i++){
            if(i%INNERROWDIMENSION ==0) System.out.println();
            for (int j=0;j<sudukoBoard[0].length;j++){
                if(j%INNERCOLDIMENSTION==0) System.out.print(" ");
                System.out.print(sudukoBoard[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------");
    }
}
