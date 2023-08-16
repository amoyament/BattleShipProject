# Battleship Java Program

This is a Battleship game coded with Java! The program allows you to play Battleship in the console. You will be trying to sink three battleships: Justin, Osiel, and Ivan. Your goal is to sink all the battleships with the fewest number of guesses.

## How the Code Functions

The code consists of three main classes:

1. **BattleshipGame**: This class initializes the game and manages the overall game flow. It sets up the battleships, displays game instructions, takes user guesses, checks the guesses, and finishes the game when all battleships are sunk.

2. **Battleship**: This class represents the actual ships with a name and a list of location cells. It has a method `checkYourself` that takes a user's guess and determines if it's a hit, miss, or a sunk ship.

3. **GameHelper**: This class handles user input, battleship placement, and various game calculations. It generates random startup positions for the battleships, converts coordinates, and checks if a startup position is valid.

## How to Run the Code

1. **Compile**: Open a terminal and navigate to the directory containing the `BattleshipGame.java`, `Battleship.java`, and `GameHelper.java` files. Compile the Java files using the following command:

   ```shell
   javac BattleshipGame.java
   ```

2. **Run**: After compilation, run the Battleship game by executing the following command:

   ```shell
   java BattleshipGame
   ```

3. **Play**: Follow the on-screen instructions to input your guesses and play the game. The program will tell you if your guesses are hits, misses, or sunken ships.

4. **Quit**: To quit the game, simply close the terminal or press `Ctrl+C`.

## Note

In the GameHelper class lines 165, 176, and 195 are commented out for gameplay. If you would like to watch the placement process of the ships, you can uncomment these lines. When actually playing the game, these should be commented out. Otherwise the user will see where all ships are placed.

Enjoy playing Battleship! If you have any questions or need further assistance, just let me know at amoyamen44@gmail.com.
