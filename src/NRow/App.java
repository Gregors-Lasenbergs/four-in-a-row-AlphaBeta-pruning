package NRow;

import NRow.Heuristics.SimpleHeuristic;
import NRow.Players.AlphaBetaPlayer;
import NRow.Players.HumanPlayer;
import NRow.Players.MinMaxPlayer;
import NRow.Players.PlayerController;

public class App {
    public static void main(String[] args) throws Exception {
        int gameN = 4;
        int boardWidth = 7;
        int boardHeight = 6;

        PlayerController[] players = getPlayers(gameN);

        Game game = new Game(gameN, boardWidth, boardHeight, players);
        game.startGame();
    }

    /**
     * Determine the players for the game
     * @param n
     * @return an array of size 2 with two Playercontrollers
     */
    private static PlayerController[] getPlayers(int n) {
        SimpleHeuristic heuristic1 = new SimpleHeuristic(n);
        SimpleHeuristic heuristic2 = new SimpleHeuristic(n);

        int depth = 6;

        PlayerController human = new HumanPlayer(1, n, heuristic1);
        PlayerController human2 = new HumanPlayer(2, n, heuristic2);

        PlayerController minMax = new MinMaxPlayer(1, n, depth,  heuristic1);
        PlayerController minMax2 = new MinMaxPlayer(2, n, depth,  heuristic2);


        PlayerController alphaBeta = new AlphaBetaPlayer(1, n, depth, heuristic1);
        PlayerController alphaBeta2 = new AlphaBetaPlayer(2, n, depth, heuristic2);



        //TODO: Implement other PlayerControllers (MinMax, AlphaBeta)

        PlayerController[] players = { human, alphaBeta2 };

        return players;
    }
}
