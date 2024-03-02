package NRow.Players;

import NRow.Board;
import NRow.Game;
import NRow.Heuristics.Heuristic;
import NRow.Node;

public class AlphaBetaPlayer extends PlayerController {
    private int depth;

    /**
     * Create human player, enabling human computer interaction through the console
     *
     * @param playerId  can take values 1 or 2 (0 = empty)
     * @param gameN     N in a row required to win
     * @param heuristic the heuristic the player should use
     */
    public AlphaBetaPlayer(int playerId, int gameN, int depth ,Heuristic heuristic) {
        super(playerId, gameN, heuristic);
        this.depth = depth;
    }

    /**
     * Makes a move for the player based on the current game board using the Alpha-Beta Pruning algorithm.
     *
     * @param board The current game board.
     * @return The column integer that represents the player's chosen move.
     */
    @Override
    public int makeMove(Board board) {
        //System.out.println(board.toString());

        // Build the game tree using the Node class
        Node rootNode = new Node(board, playerId, -1); // -1 indicates no move has been made yet
        buildGameTree(rootNode, depth, true);
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        int maxMove = 0;
        for(Node child : rootNode.getChildren()) { //for each of the possible moves

            int value = alphaBeta(child, alpha, beta , depth,false);
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
     * Builds the game tree by recursively expanding possible moves for the Alpha-Beta Pruning algorithm.
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
     * Performs the Alpha-Beta Pruning algorithm to evaluate the value of a game state.
     *
     * @param node   The current node in the game tree.
     * @param alpha  The alpha value for pruning.
     * @param beta   The beta value for pruning.
     * @param depth  The remaining depth for tree expansion.
     * @param isMax  Indicates whether the current player is maximizing or minimizing.
     * @return The evaluated value of the game state.
     */
    private int alphaBeta(Node node,int alpha, int beta, int depth, boolean isMax) {
        int winner = Game.winning(node.getBoard().getBoardState(), gameN);
        if (depth == 0 || winner != 0 || node.getChildren().isEmpty()) {
            // Return a suitable terminal score based on the game result
            return heuristic.evaluateBoard(playerId, node.getBoard());
        }

        if (isMax) {
            int maxEval = Integer.MIN_VALUE;
            for (Node child : node.getChildren()) {
                if (node.getBoard().isValid(child.getMove())) {
                    int eval = alphaBeta(child, alpha, beta, depth - 1, false);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);

                    // Alpha-Beta Pruning: If beta is less than or equal to alpha, break the loop
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
             for (Node child : node.getChildren()) {
                if (node.getBoard().isValid(child.getMove())) {
                    int eval = alphaBeta(child, alpha, beta, depth - 1, true);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);

                    // Alpha-Beta Pruning: If beta is less than or equal to alpha, break the loop
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }
}
