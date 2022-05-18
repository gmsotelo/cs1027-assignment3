import java.io.IOException;

/**
 * This class finds the best path for a given map
 * @author gsotelo
 */
public class FindPath {
    private Map pyramidMap;

    /**
     * Constructor to create a map from the text file to find a path for
     * @param fileName name of the text file to be used as the map
     * @throws InvalidMapCharacterException if an invalid character is encountered while building the map
     */
    public FindPath(String fileName) throws InvalidMapCharacterException {
        try {
            pyramidMap = new Map(fileName);
        } catch (IOException e) {
            System.out.println("Inputted filename is invalid");
            System.exit(0);
        }
    }

    /**
     * Creates a path leading to all treasures in the map and stores it into a stack
     * @return the stack containing chambers
     */
    public DLStack path() {
        DLStack<Chamber> stack = new DLStack<Chamber>();
        int N = pyramidMap.getNumTreasures();
        int treasuresFound = 0;

        stack.push(pyramidMap.getEntrance());
        stack.peek().markPushed();

        // Continue finding path while stack is not empty and treasures have not been found
        while (!stack.isEmpty() && treasuresFound != N) {
            Chamber c = stack.peek();

            // Set current chamber as the best adjacent chamber and push it into the stack
            c = bestChamber(c);

            if (c != null) {
                stack.push(c);
                stack.peek().markPushed();
                if (stack.peek().isTreasure()) treasuresFound++;
            }

            // Pop current chamber if no best adjacent chamber exists
                if (c == null) {
                stack.peek().markPopped();
                stack.pop();
            }
        }

        return stack;
    }

    /**
     * Gets the map used by the program
     * @return the map of class Map used by the program
     */
    public Map getMap() {
        return pyramidMap;
    }

    /**
     * Checks whether the current chamber is dim
     * @param currentChamber the current chamber
     * @return true if chamber should be considered dim, false otherwise
     */
    public boolean isDim(Chamber currentChamber) {
        boolean dim = false;
        if (currentChamber != null && !currentChamber.isSealed() && !currentChamber.isLighted()) {
            for (int i = 0; i <= 5; i++) {
                if (currentChamber.getNeighbour(i) != null)
                    if (currentChamber.getNeighbour(i).isLighted()) {
                        dim = true;
                        break;
                    }
            }
        }

        return dim;
    }

    /**
     * Finds the best chamber to travel to from the current chamber
     * @param currentChamber the current chamber
     * @return the best neighboring chamber to travel to
     */
    public Chamber bestChamber(Chamber currentChamber) {
        Chamber bestChamber = currentChamber;
        boolean bestFound = false;

        // Check if any neighboring chamber contains a treasure
        for (int i = 0; i <= 5; i++) {
            if (currentChamber.getNeighbour(i) != null && !currentChamber.getNeighbour(i).isSealed()
                    && !currentChamber.getNeighbour(i).isMarked() && !bestFound) {
                if (currentChamber.getNeighbour(i).isTreasure()) {
                    bestChamber = currentChamber.getNeighbour(i);
                    bestFound = true;
                }
            }
        }

        // Check if any neighboring chamber is lighted
        for (int i = 0; i <= 5; i++) {
            if (currentChamber.getNeighbour(i) != null && !currentChamber.getNeighbour(i).isSealed()
                    && !currentChamber.getNeighbour(i).isMarked() && !bestFound) {
                if (currentChamber.getNeighbour(i).isLighted()) {
                    bestChamber = currentChamber.getNeighbour(i);
                    bestFound = true;
                }
            }
        }

        // Check if any neighboring chamber is dim
        for (int i = 0; i <= 5; i++) {
            if (currentChamber.getNeighbour(i) != null && !currentChamber.getNeighbour(i).isSealed()
                    && !currentChamber.getNeighbour(i).isMarked() && !bestFound) {
                if (isDim(currentChamber.getNeighbour(i))) {
                    bestChamber = currentChamber.getNeighbour(i);
                    bestFound = true;
                }
            }
        }
        if (!bestFound) bestChamber = null;

        return bestChamber;
    }
}