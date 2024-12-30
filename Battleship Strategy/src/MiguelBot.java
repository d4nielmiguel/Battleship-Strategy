import battleship.BattleShip3;
import battleship.BattleShipBot;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

/**
 * A Sample random shooter - Takes no precaution on double shooting and has no strategy once
 * a ship is hit - This is not a good solution to the problem!
 * @author mark.yendt@mohawkcollege.ca (November 2024)
 *
 * I Daniel Miguel, 000869785 certify that this is my original work. I have not
 * used any uncited exernal resources and I have not shared my work with anyone else.
 * I did not use ChatGPT or any other AI based tool.
 */
public class MiguelBot implements BattleShipBot {
    private int gameSize;
    private BattleShip3 battleShip;
    private Random random;

    /**
     * Constructor keeps a copy of the BattleShip instance
     * Create instances of any Data Structures and initialize any variables here
     * @param b previously created battleship instance - should be a new game
     */

    @Override
    public void initialize(BattleShip3 b) {
        battleShip = b;
        gameSize = b.BOARD_SIZE;

        // Need to use a Seed if you want the same results to occur from run to run
        // This is needed if you are trying to improve the performance of your code

        random = new Random(0xAAAAAAAA);   // Needed for random shooter - not required for more systematic approaches
    }

    /**
     * Create a random shot and calls the battleship shoot method
     * Put all logic here (or in other methods called from here)
     * The BattleShip API will call your code until all ships are sunk
     */

    @Override
    public void fireShot() {
        HashSet<Point> shots = new HashSet<>();
        int startX = 1;  // starting column.
        int startY = 0;  // starting row.
        int x = startX;
        int y = startY;

        // Loop over rows
        for (y = startY; y < gameSize; y++) {
            // Determine the starting x based on the current row (y).
            x = (y % 2 == 0) ? 0 : 1;

            // Loop over columns.
            while (x < gameSize) {
                Point shot = new Point(x, y);

                // Check if the shot has already been fired.
                if (!shots.contains(shot)) {
                    boolean hit = battleShip.shoot(shot);
                    shots.add(shot);  // Mark the shot as fired.

                    // If the shot was a hit, shoot surrounding points.
                    if (hit) {
                        // Directions for left, right, up, and down (in terms of x and y offsets).
                        int[] directionsX = {-1, 1, 0, 0}; // Left, Right, Up, Down
                        int[] directionsY = {0, 0, -1, 1}; // Left, Right, Up, Down

                        // Loop through each direction (left, right, up, down).
                        for (int i = 0; i < 4; i++) {
                            int newX = shot.x + directionsX[i];
                            int newY = shot.y + directionsY[i];

                            // Check if the new shot is within bounds.
                            if (newX >= 0 && newX < gameSize && newY >= 0 && newY < gameSize) {
                                Point newShot = new Point(newX, newY);
                                if (!shots.contains(newShot)) {
                                    boolean newHit = battleShip.shoot(newShot);
                                    shots.add(newShot);  // Mark the surrounding shot as fired.
                                }
                            }
                        }
                    }
                }
                x += 2;  // Increment x to move to the next column.
            }
        }
    }

    /**
    * Authorship of the solution - must return names of all students that contributed to
    * the solution
    * @return names of the authors of the solution
    */

    @Override
    public String getAuthors() {
        return "Daniel Miguel";
    }
}
