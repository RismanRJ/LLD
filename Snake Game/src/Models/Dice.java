package Models;

import java.util.Random;

public class Dice {
    int numberOfSides;

    public Dice(int sides){
        this.numberOfSides = sides;
    }

    public int getNextPosition(){
        Random random = new Random();
        return  random.nextInt(numberOfSides)+1;
    }
}
