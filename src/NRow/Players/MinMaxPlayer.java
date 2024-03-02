package NRow.Players;

import NRow.Board;
import NRow.Game;
import NRow.Heuristics.Heuristic;
import NRow.Heuristics.SimpleHeuristic;
import NRow.Node;

import java.util.Arrays;

public class MinMaxPlayer extends PlayerController {
    private int depth;

    public MinMaxPlayer(int playerId, int gameN, int depth, Heuristic heuristic) {
        super(playerId, gameN, heuristic);
        this.depth = depth;
        //You can add functionality which runs when the player is first created (before the game starts)
    }

    /**
     * Makes a move for the player based on the current game board using the Minimax algorithm.
     * @param board The current game board.
     * @return The column integer that represents the player's chosen move.
     */
    @Override
    public int makeMove(Board board) {
        //System.out.println(board.toString());

        // Build the game tree using the Node class
        Node rootNode = new Node(board, playerId, -1); // -1 indicates no move has been made yet
        buildGameTree(rootNode, depth, true);
        int maxValue = Integer.MIN_VALUE;
        int maxMove = -1;
        for(Node child : rootNode.getChildren()) { //for each of the possible moves

            int value = minimax(child, depth -1, false);
                if(value > maxValue) {
                    if (board.isValid(child.getMove())){
                        maxValue = value;
                        maxMove = child.getMove();
                    }
                }
        }
        // Perform the Minimax search and choose the best move
        return maxMove;
    }

    /**
     * Builds the game tree by recursively expanding possible moves for the Minimax algorithm.
     *
     * @param node   The current node in the game tree.
     * @param depth  The remaining depth for tree expansion.
     * @param isMax  Indicates whether the current player is maximizing or minimizing.
     */
    private void buildGameTree(Node node, int depth, boolean isMax) {
        int winner = Game.winning(node.getBoard().getBoardState(), gameN);
        if (depth == 0 || winner != 0) {
            // Terminal node reached or game over
            return;
        }

        for (int i = 0; i < node.getBoard().width; i++) {
            if (node.getBoard().isValid(i)) {
                Board newBoard = node.getBoard().getNewBoard(i, node.getPlayer());
                Node childNode = new Node(newBoard, (node.getPlayer() == 1) ? 2 : 1, i);
                node.addChild(childNode);

                buildGameTree(childNode, depth - 1, !isMax);
            }
        }
    }

    /**
     * Performs the Minimax algorithm to evaluate the value of a game state.
     *
     * @param node   The current node in the game tree.
     * @param depth  The remaining depth for tree expansion.
     * @param isMax  Indicates whether the current player is maximizing or minimizing.
     * @return The evaluated value of the game state.
     */
    private int minimax(Node node, int depth, boolean isMax) {
        int winner = Game.winning(node.getBoard().getBoardState(), gameN);
        if (depth == 0 || winner != 0 || node.getChildren().isEmpty()) {
            // Return a suitable terminal score based on the game result
            return heuristic.evaluateBoard(playerId, node.getBoard());
        }

        if (isMax) {
            int maxEval = Integer.MIN_VALUE;
            for (Node child : node.getChildren()) {
                int eval = minimax(child, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Node child : node.getChildren()) {
                int eval = minimax(child, depth - 1, true);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }
}

