import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove()) {
            step();
        } else {
            showError( "Your stuck" );
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }
    
    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public String jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
            
        }
        return " you have moved " + nrStepsTaken + " steps " ;
    }

    
    
    
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdgePrintingCoordinates( ){
        while( ! borderAhead() ){
            System.out.println(" x: " + getX() + " Y: " + getY());
            move();
        }
        System.out.println(" x: " + getX() + " Y: " + getY());
        System.out.println(" Boink ");
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if( onEgg() ){
            return false;
        }else{
            return true;
       }
    }  
    
    public void turn180() {
        turnRight();
        turnRight();
    }
    
    public void climbOverFence() {
        turnLeft();
        move();
        turnRight();
        move();
        move();
        turnRight();
        move();
        turnLeft();
    }
    
    public boolean grainAhead() {
        boolean grain = false;
        move();
        if (onGrain()) {
            grain = true;
        } 
        turn180();
        move();
        turn180();
        return grain;
    }
    
    public void gotoEgg() {
        while (!onEgg()) {
            if (getX() >= getWorld().getWidth() - 1) {
                System.out.println("Hier ligt geen ei!");
                break;
            }
            move();
        }

        if (onEgg()) {
            System.out.println("Ei gevonden!");
        }
    }
    
    public void walkToWorldEdge(){
        while( ! borderAhead() ){
            move();
        }
    }
    
    public void goBackToStartOfRowAndFaceBack () {
        turn180();
        walkToWorldEdge();
        turn180();
    }
    
    public void walkToWorldEdgeClimbingOverFence () {
        while (!borderAhead()) {
            if (fenceAhead()) {
                climbOverFence();                
            } else {
                if (onNest()) {
                    break;
                }
                move();
                if (onNest()) {
                    break;
                }
            }
        }
    }
    
    public void pickUpGrainsAndPrintCoordinates() {
        while (!borderAhead()) {
            if (onGrain()) {
                System.out.println(" x: " + getX() + " Y: " + getY());
            }
            move();
            if (onGrain()) {
                System.out.println(" x: " + getX() + " Y: " + getY());
            }
        }
    }
    
    public void stepOneCellBackwards () {
        turn180();
        move();
        turn180();
    }
    
    public void noDoubleEggs () {
        while (!borderAhead()) {
            if (onNest()) {
                if (canLayEgg()) {
                    layEgg();
                }
            }
            move();
            if (onNest()) {
                if (canLayEgg()) {
                    layEgg();
                }
            }
        }
    }
    
    public void walkAroundFencedArea () {
        while (!onEgg()) {
            turnRight();
            if (fenceAhead()) {
                turnLeft();
                if (fenceAhead()) {
                    turnLeft();
                }
                if (fenceAhead()) {
                    turnLeft();
                }
                move();
            } else {
                move();
            }
        }
    }
    
    public void eggTrailToNest() {
        while (!onNest()) {
            if (eggAhead()) {
                move();
                hatchEgg();
                if (!eggAhead()) {
                    turnRight();
                }
            } else {
                turn180();
            } 
            if (nestAhead()) {
                move();
                break;
            }
        }
    }
    
    public void simpleMaze() {
        while (!onNest()) {
            if (!fenceAhead()) {
                move();
            } else {
                turnRight();
                if (!fenceAhead()) {
                    move();
                } else {
                    turn180();
                    move();
                }
            } 
        }
    }
    
    public void simpleMaze2() {
        while (!onNest()) {
            turnLeft();
            if (!fenceAhead()) {
                move();
            } else {
                turnRight();
                if (!fenceAhead()) {
                    move();
                } else {
                    turnRight();
                    if (!fenceAhead()) {
                        move();
                    } else {
                        turnRight();
                    }
                }
            }
        }  
    }
    
    public void faceNorth() {
        while (getDirection() !=NORTH) {
            turnRight();
        }
    }
    
    public void faceEast() {
        while (getDirection() !=EAST) {
            turnRight();
        }
    }
    
    public void faceSouth() {
        while (getDirection() !=SOUTH) {
            turnRight();
        }
    }
    
    public void faceWest() {
        while (getDirection() !=WEST) {
            turnRight();
        }
    }
    
    public void goToLocation (int coordX , int coordY) {
        if (validCoordinates(coordX, coordY)) {
            while (!locationReached(coordX, coordY)) {
                if (getX() < coordX) {
                    faceEast();
                    move();
                } else if (getX() > coordX) {
                    faceWest();
                    move();
                }
                if (getY() < coordY) {
                    faceSouth();
                    move();
                } else if (getY() > coordY) {
                    faceNorth();
                    move();
                }
                faceEast();
            }
        }
    }
    
    public boolean locationReached(int x, int y) {
        if (getX() == x && getY() == y) {
            return true;
        }
        return false;
    }
    
    public boolean validCoordinates(int x, int y) {
        if (x > getWorld().getWidth()-1 || y > getWorld().getWidth()-1) {
            showError ("Invalid Coordinates " );
            return false;
        } else {
            return true;
        }
    }
    
    public int countEggsInRow() {
        int totalEggs = 0;
        while (!borderAhead()) {
            if (onEgg()) {
                totalEggs++;
            }
            move();
        }
        if (onEgg()) {
                move();
                totalEggs++;
        }
        goBackToStartOfRowAndFaceBack ();
        return(totalEggs);
    }
    
    public void layTrailOfEggs(int coordX) {
        if (coordX > getX()) {
        } else if (coordX < getX()) {
            turn180();
        } else {
            layEgg();
            return;
        }
        layEgg();
        while (getX() != coordX) {
            if (borderAhead()) {
                System.out.println("You are stuck!");
                return;
            }
            move();
            layEgg();
        }
    }
    
    public int countEggsInWorld(){
        boolean end = false;
        int totaleggs = 0;
        int startx = getX();
        int starty = getY();
        goToLocation(0, 0);
        while (!end) {
            totaleggs = totaleggs + countEggsInRow();
            turnRight();
            if (borderAhead()) {
                end = true;
                break;
            } else {
                move();
                turnLeft();
            }
        }
        goToLocation(startx, starty);
        return totaleggs;
    }
    
    public int findRowWithMostEggs() {
        boolean end = false;
        int maxEggs = -1;
        int rowWithMax = 0;
        int currentRow = 0;
        int startx = getX();
        int starty = getY();
        goToLocation(0, 0);
        while (!end) {
            int eggsInRow = countEggsInRow();
            if (eggsInRow > maxEggs) {
                maxEggs = eggsInRow;
                rowWithMax = currentRow;
            }
            turnRight();
            if (borderAhead()) {
                end = true;
            } else {
                move();
                currentRow++;
                turnLeft();
            }
        }
        goToLocation(startx, starty);
        System.out.println("Rij met meeste eieren: " + rowWithMax);
        return rowWithMax;
    }
    
    public void monumentOfEggs() {
        int startX = getX();
        int startY = getY();
        int row = 0;
        boolean klaar = false;
        while (!klaar) {
            goToLocation(startX, startY + row);
            faceEast();
            int column = 0;
            while (validCoordinates(startX + column, startY + row)) {
                if (column <= row) {
                    if (canLayEgg()) {
                        layEgg();
                    }
                }
                if (!borderAhead()) {
                    move();
                    column++;
                } else {
                    break;
                }
            }   
            row++;
            if (!validCoordinates(startX, startY + row)) {
                klaar = true;
            }
        }
    }
    
public void monumentOfEggs2() {
    int eggsInRow = 1;
    int startX = getX();
    int startY = getY();
    int worldWidth = getWorld().getWidth();
    int worldHeight = getWorld().getHeight();
    int row = 0;

    while (startY + row < worldHeight && startX + eggsInRow - 1 < worldWidth) {
        goToLocation(startX, startY + row);
        faceEast();
        int layed = 0;
        while (layed < eggsInRow && !borderAhead()) {
            if (canLayEgg()) {
                layEgg();
            }
            layed++;
            if (layed < eggsInRow && !borderAhead()) {
                move();
            }
        }
        eggsInRow *= 2;
        row++;
    }
}
}

