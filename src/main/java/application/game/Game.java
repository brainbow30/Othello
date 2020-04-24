package application.game;

import application.game.verifiers.OthelloVerifier;
import application.gui.GUI;
import application.players.ComputerPlayer;
import application.players.HumanPlayer;
import application.players.Player;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public
class Game {
    private final Player player1;
    private final Player player2;
    private Board board;
    private Player currentTurnsPlayer;
    private application.gui.GUI GUI;


    @Autowired
    public Game(Board board, @Value("${player1.human}") Boolean humanPlayer1, @Value("${player2.human}") Boolean humanPlayer2,
                @Value("${computer1.moveFunction}") Integer computer1MoveFunction, @Value("${computer2.moveFunction}") Integer computer2MoveFunction,
                @Value("${mcts.waitTime1}") Integer mctsWaitTime1, @Value("${mcts.waitTime2}") Integer mctsWaitTime2,
                @Value("${hostname}") String hostname,
                @Value("${useGUI}") Boolean useGUI, @Value("${mcts.cpuct1}") Double cpuct1, @Value("${mcts.cpuct2}") Double cpuct2, @Value("${mcts.tempThreshold1}") Integer tempThreshold1, @Value("${mcts.tempThreshold1}") Integer tempThreshold2) {
        this.board = board;
        GUI = new GUI(board);
        if (humanPlayer1) {
            if (useGUI) {
                player1 = new HumanPlayer(COLOUR.WHITE, GUI);
            } else {
                player1 = new HumanPlayer(COLOUR.WHITE);
            }
        } else {
            player1 = new ComputerPlayer(COLOUR.WHITE, computer1MoveFunction, mctsWaitTime1, hostname, cpuct1, tempThreshold1);
        }
        if (humanPlayer2) {
            if (useGUI) {
                player2 = new HumanPlayer(COLOUR.BLACK, GUI);
            } else {
                player2 = new HumanPlayer(COLOUR.BLACK);
            }
        } else {

            player2 = new ComputerPlayer(COLOUR.BLACK, computer2MoveFunction, mctsWaitTime2, hostname, cpuct2, tempThreshold2);
        }
        currentTurnsPlayer = player2;
    }

    public Game(Properties properties) {
        this.board = new Board(Integer.valueOf(properties.getProperty("board.size")), new OthelloVerifier(), Double.valueOf(properties.getProperty("evaluationValue.discNum")), Double.valueOf(properties.getProperty("evaluationValue.stableNum")), Double.valueOf(properties.getProperty("evaluationValue.mobility")));
        GUI = new GUI(board);
        if (Boolean.valueOf(properties.getProperty("player1.human"))) {
            if (Boolean.valueOf(properties.getProperty("useGUI"))) {
                player1 = new HumanPlayer(COLOUR.WHITE, GUI);
            } else {
                player1 = new HumanPlayer(COLOUR.WHITE);
            }
        } else {
            player1 = new ComputerPlayer(COLOUR.WHITE, Integer.valueOf(properties.getProperty("computer1.moveFunction")), Integer.valueOf(properties.getProperty("mcts.waitTime1")), properties.getProperty("hostname"), Double.valueOf(properties.getProperty("mcts.cpuct1")), Integer.valueOf(properties.getProperty("mcts.tempThreshold1")));
        }
        if (Boolean.valueOf(properties.getProperty("player2.human"))) {
            if (Boolean.valueOf(properties.getProperty("useGUI"))) {
                player2 = new HumanPlayer(COLOUR.BLACK, GUI);
            } else {
                player2 = new HumanPlayer(COLOUR.BLACK);
            }
        } else {

            player2 = new ComputerPlayer(COLOUR.BLACK, Integer.valueOf(properties.getProperty("computer2.moveFunction")), Integer.valueOf(properties.getProperty("mcts.waitTime2")), properties.getProperty("hostname"), Double.valueOf(properties.getProperty("mcts.cpuct2")), Integer.valueOf(properties.getProperty("mcts.tempThreshold2")));
        }
        currentTurnsPlayer = player1;
    }

    public Optional<Player> play(Boolean useGUI) {
        int numberOfConsecutivePasses = 0;
        while (Math.pow(board.getBoardSize(), 2) > board.getCountersPlayed() && numberOfConsecutivePasses < 2) {
            if (useGUI) {
                GUI.updateBoard(board.clone(), currentTurnsPlayer.getCounterColour());
                GUI.show();
            } else {
                System.out.println(board);
            }

            if (board.numberOfValidMoves(currentTurnsPlayer.getCounterColour()) > 0) {
                board = currentTurnsPlayer.playTurn(board);
                numberOfConsecutivePasses = 0;
            } else {
                System.out.println("No valid moves, turn passes");
                numberOfConsecutivePasses += 1;
            }
            if (currentTurnsPlayer.equals(player1)) {
                currentTurnsPlayer = player2;
            } else {
                currentTurnsPlayer = player1;
            }
        }
        return endGame(board, useGUI);
    }


    private Optional<Player> endGame(Board board, Boolean useGUI) {
        if (useGUI) {
            GUI.updateBoard(board.clone(), currentTurnsPlayer.getCounterColour());
            GUI.show();
        } else {
            System.out.println(board);
        }
        Optional<COLOUR> winner = board.getWinner(true);
        GUI.setWinnerText(winner);
        if (winner.isPresent()) {
            if (winner.get().equals(COLOUR.WHITE)) {
                System.out.println("White wins");
                if (player1.getCounterColour().equals(COLOUR.WHITE)) {
                    return Optional.of(player1);
                } else {
                    return Optional.of(player2);
                }
            } else if (winner.get().equals(COLOUR.BLACK)) {
                System.out.println("Black wins");
                if (player1.getCounterColour().equals(COLOUR.BLACK)) {
                    return Optional.of(player1);
                } else {
                    return Optional.of(player2);
                }
            }
        } else {
            System.out.println("Draw");
            return Optional.absent();
        }
        return Optional.absent();
    }


    public void reset() {
        board.reset();
        player1.reset();
        player2.reset();
        currentTurnsPlayer = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
