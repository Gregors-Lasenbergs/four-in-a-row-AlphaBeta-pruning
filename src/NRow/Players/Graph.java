package NRow.Players;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Heuristics.SimpleHeuristic;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    public static void main(String[] args) throws Exception {
        Heuristic heuristic1 = new SimpleHeuristic(7);
        Node root = new Node(new Board(7, 6), 1, null, heuristic1, 7);
        List<Node> rootChildren = root.getChildren();
        System.out.println(rootChildren.get(2).getChildren().get(2).getChildren().get(2).getChildren().get(4).getChildren().get(3).getChildren().get(5).getChildren().get(6).getState().toString());

    }
    private static class Node{
        private final Board parent;
        private final Board state;
        private final int playerId;
        private final List<Node> children = new ArrayList<>();
        private final int value;

        public Node(Board state, int playerId, Board parent, Heuristic heuristic, int depth) {
            this.state = state;
            this.playerId = playerId;
            this.parent = parent;
            // Generates children in the constructor
            for(int i = 0; i < state.width; i++) {  // for each of the possible moves
                if(state.isValid(i) && depth !=0) { // if the move is valid, and depth is not 0
                    this.children.add((playerId==1) // Get a new board resulting from that move
                            ?                       // If current node is player one, next is player two
                            new Node(state.getNewBoard(i, playerId), 2, state, heuristic, depth-1)
                            :                       // Otherwise player one
                            new Node(state.getNewBoard(i, playerId), 1, state, heuristic, depth-1));
                }
            }
            this.value = heuristic.evaluateBoard(playerId, state);
        }

        public Board getParent() {
            return parent;
        }
        public List<Node> getChildren() {
            return children;
        }
        public int getValue() {
            return value;
        }
        public int getPlayerId() {
            return playerId;
        }
        public Board getState() {
            return state;
        }
    }
}
