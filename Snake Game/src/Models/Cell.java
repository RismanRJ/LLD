package Models;

public class Cell {
    int start;
    int end;
    Cell_Type type;

    public Cell(int start, int end, Cell_Type type){
        this.start=start;
        this.end=end;
        this.type=type;
    }
}
