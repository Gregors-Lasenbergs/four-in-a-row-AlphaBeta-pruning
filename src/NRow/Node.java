package NRow;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Board board; // The current board state
    private int player;  // The player whose turn it is (1 or 2)
    private int move;    // The move that led to this state (column index)
    private List<Node> children; // Children nodes

    /**
     * Constructor for creating a Node object.
     *
     * @param board  The current board state.
     * @param player The player whose turn it is (1 or 2).
     * @param move   The move that led to this state (column index).
     */
    public Node(Board board, int player, int move) {
        this.board = board;
        this.player = player;
        this.move = move;
        this.children = new ArrayList<>();
    }

    /**
     * Get the current board state.
     *
     * @return The current board state.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get the player whose turn it is (1 or 2).
     *
     * @return The player whose turn it is.
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Get the move that led to this state (column index).
     *
     * @return The move that led to this state.
     */
    public int getMove() {
        return move;
    }

    /**
     * Add a child node to the current node.
     *
     * @param child The child node to add.
     */
    public void addChild(Node child) {
        children.add(child);
    }

    /**
     * Get the list of child nodes.
     *
     * @return The list of child nodes.
     */
    public List<Node> getChildren() {
        return children;
    }
}



