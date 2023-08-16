import java.util.*;

public class BattleshipGame {
    // Initialize ArrayList to store battleship objects
    private ArrayList<Battleship> battleships = new ArrayList<Battleship>();
    // Helper class for getting user input and placing battleships
    private GameHelper helper = new GameHelper();
    // Counter for tracking the number of guesses
    private int numGuesses = 0;

    public void setUpGame() {
        // Make the three ships and name them
        Battleship one = new Battleship();
        one.setName("Justin");
        Battleship two = new Battleship();
        two.setName("Osiel");
        Battleship three = new Battleship();
        three.setName("Ivan");
        // Insert the ships into the ArrayList
        battleships.add(one);
        battleships.add(two);
        battleships.add(three);

        // Print game instructions for the user
        System.out.println("Your goal is to sink three Battleships!");
        System.out.println("Justin, Osiel, and Ivan");
        System.out.println("Try to sink all the ships with the fewest number of guesses.");

        // Use a for loop to iterate over ships
        for (Battleship battleship : battleships) {
            // Get and set a location for each ship with helper
            ArrayList<String> newLocation = helper.placeStartup(3);
            battleship.setLocationsCells(newLocation);
        }
    }

    public void playGame() {
        // Use while loop to keep playing until all battleships are sunk
        while (!battleships.isEmpty()) {
            // Get user guess while the battleships are still on the board
            String userGuess = helper.getUserInput("Enter a guess");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    private void checkUserGuess(String userGuess) {
        // Increment number of guesses the user has made
        numGuesses++;
        // Default result is a miss
        String result = "miss";

        // For loop to iterate over battleships to see if there is a hit
        for (Battleship battleship : battleships) {
            // Check the guess against each battleship
            result = battleship.checkYourself(userGuess);
            // Exit loop if a battleship is hit
            if (result.equals("Hit")) {
                break;
            // Remove battleship if it's sunk and exit loop
            } else if (result.equals("Sunk")) {
                battleships.remove(battleship);
                break;
            }
        }
        // Print result for user to see
        System.out.println(result);
    }

    // Give user game results
    private void finishGame() {
        System.out.println("All battleships are sunk! Congratulations!");
        System.out.println("It took you " + numGuesses + " guesses to sink all the battleships.");
    }

    // Actually start the game
    public static void main(String[] args) {
        // Create a BattleshipGame object
        BattleshipGame game = new BattleshipGame();
        // Set up the game with 3 battleships
        game.setUpGame();
        // Start playing
        game.playGame();
    }
}

class Battleship {
    // Store ship names and locations
    private String name;
    private ArrayList<String> locationCells;

    public void setLocationsCells(ArrayList<String> location) {
        locationCells = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String checkYourself(String userInput) {
        // Again default result is a miss
        String result = "Miss";
        // Find index of user's guess in the locationCells list
        int index = locationCells.indexOf(userInput);

        // If the guess is found in the list, remove the guessed location from the list
        if (index >= 0) {
            locationCells.remove(index);

            // If there are no more remaining ship locations, the ship is sunk
            if (locationCells.isEmpty()) {
                result = "Sunk";
                // Print a message indicating the ship is sunk
                System.out.println("Ouch! You sunk " + name + "!");
            } else {
                // The guess is a hit, but the ship is not sunk yet
                result = "Hit";
            }
        }
        // Return the result of the guess (Miss, Hit, or Sunk)
        return result;
    }
}


class GameHelper {
    private static final String ALPHABET = "abcdefg";
    private static final int GRID_LENGTH = 7;
    private static final int GRID_SIZE = 49;
    private static final int MAX_ATTEMPTS = 200;

    static final int HORIZONTAL_INCREMENT = 1;          // A better way to represent these two
    static final int VERTICAL_INCREMENT = GRID_LENGTH;  // things is an enum (see Appendix B)

    private final int[] grid = new int[GRID_SIZE];
    private final Random random = new Random();

    private int startupCount = 0;

    public String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toLowerCase();
    } //end getUserInput

    public ArrayList<String> placeStartup(int startupSize) {
        // holds index to grid (0 - 48)
        int[] startupCoords = new int[startupSize];         // current candidate co-ordinates
        int attempts = 0;                                   // current attempts counter
        boolean success = false;                            // flag = found a good location?

        startupCount++;                                     // nth Startup to place
        int increment = getIncrement();                     // alternate vert & horiz alignment

        while (!success & attempts++ < MAX_ATTEMPTS) {      // main search loop
            int location = random.nextInt(GRID_SIZE);         // get random starting point

            for (int i = 0; i < startupCoords.length; i++) {  // create array of proposed coords
                startupCoords[i] = location;                    // put current location in array
                location += increment;                          // calculate the next location
            }

            // Test to make sure ships are being placed.
            // Comment out when actually trying to play the game so you cannot "cheat"
            // System.out.println("Trying: " + Arrays.toString(startupCoords));

            if (startupFits(startupCoords, increment)) {      // startup fits on the grid?
                success = coordsAvailable(startupCoords);       // ...and locations aren't taken?
            }                                                 // end loop
        }                                                   // end while

        savePositionToGrid(startupCoords);                  // coords passed checks, save
        ArrayList<String> alphaCells = convertCoordsToAlphaFormat(startupCoords);
        // Test to make sure ships are placed.
        // Comment out when actually trying to play the game so you cannot "cheat"
        // System.out.println("Placed at: "+ alphaCells);
        return alphaCells;
    } //end placeStartup

    boolean startupFits(int[] startupCoords, int increment) {
        int finalLocation = startupCoords[startupCoords.length - 1];
        if (increment == HORIZONTAL_INCREMENT) {
            // check end is on same row as start
            return calcRowFromIndex(startupCoords[0]) == calcRowFromIndex(finalLocation);
        } else {
            return finalLocation < GRID_SIZE;                 // check end isn't off the bottom
        }
    } //end startupFits

    boolean coordsAvailable(int[] startupCoords) {
        for (int coord : startupCoords) {                   // check all potential positions
            if (grid[coord] != 0) {                           // this position already taken
                // Test to make sure ships are being placed.
                // Comment out when actually trying to play the game so you cannot "cheat"
                // System.out.println("position: " + coord + " already taken.");
                return false;                                   // NO success
            }
        }
        return true;                                        // there were no clashes, yay!
    } //end coordsAvailable

    void savePositionToGrid(int[] startupCoords) {
        for (int index : startupCoords) {
            grid[index] = 1;                                  // mark grid position as 'used'
        }
    } //end savePositionToGrid

    private ArrayList<String> convertCoordsToAlphaFormat(int[] startupCoords) {
        ArrayList<String> alphaCells = new ArrayList<String>();
        for (int index : startupCoords) {                   // for each grid coordinate
            String alphaCoords = getAlphaCoordsFromIndex(index); // turn it into an "a0" style
            alphaCells.add(alphaCoords);                      // add to a list
        }
        return alphaCells;                                  // return the "a0"-style coords
    } // end convertCoordsToAlphaFormat

    String getAlphaCoordsFromIndex(int index) {
        int row = calcRowFromIndex(index);                  // get row value
        int column = index % GRID_LENGTH;                   // get numeric column value

        String letter = ALPHABET.substring(column, column + 1); // convert to letter
        return letter + row;
    } // end getAlphaCoordsFromIndex

    private int calcRowFromIndex(int index) {
        return index / GRID_LENGTH;
    } // end calcRowFromIndex

    private int getIncrement() {
        if (startupCount % 2 == 0) {                        // if EVEN Startup
            return HORIZONTAL_INCREMENT;                      // place horizontally
        } else {                                            // else ODD
            return VERTICAL_INCREMENT;                        // place vertically
        }
    } //end getIncrement
} //end class
