package application.gui;


import application.ImmutablePosition;
import application.game.Board;
import application.game.COLOUR;
import com.google.common.base.Optional;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
public class GUI {

    private BoardGridPanel boardGridPanel;
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel currentPlayerLabel;
    private JLabel currentScoreLabel;
    private Board board;
    private COLOUR colour = COLOUR.WHITE;

    public GUI(Board board) {
        this.board = board;
        frame = new JFrame("Othello");
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;

        c.weightx = 1.0;
        c.gridwidth = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        currentPlayerLabel = new JLabel("White's Turn", JLabel.CENTER);
        currentPlayerLabel.setBorder(new EmptyBorder(10, 0, 10, 0));//top,left,bottom,right
        currentPlayerLabel.setFont(new Font(null, Font.PLAIN, 20));
        mainPanel.add(currentPlayerLabel, c);

        currentScoreLabel = new JLabel("Score: 0-0", JLabel.CENTER);
        currentScoreLabel.setBorder(new EmptyBorder(10, 0, 10, 0));//top,left,bottom,right
        currentScoreLabel.setFont(new Font(null, Font.PLAIN, 20));
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridwidth = 1;
        mainPanel.add(currentScoreLabel, c);


        boardGridPanel = new BoardGridPanel(board);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.gridheight = 4;
        mainPanel.add(boardGridPanel, c);

        setup();
    }

    private void setup() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
        mainPanel.requestFocusInWindow();
        mainPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'h') {
                    help();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void help() {
        boardGridPanel.highlight(board.getValidMoves(colour));
    }

    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

    public void updateBoard(Board board, COLOUR colour) {
        mainPanel.requestFocusInWindow();
        this.board = board;
        this.colour = colour;
        if (colour.equals(COLOUR.WHITE)) {
            currentPlayerLabel.setText("White's Turn");
        } else if (colour.equals(COLOUR.BLACK)) {
            currentPlayerLabel.setText("Black's Turn");
        }
        currentScoreLabel.setText("Score: " + board.getNumberOfWhiteCounters() + "-" + (board.getCountersPlayed() - board.getNumberOfWhiteCounters()));
        boardGridPanel.updateBoard(board);
    }

    public void setWinnerText(Optional<COLOUR> colour) {
        if (colour.isPresent()) {
            if (colour.get().equals(COLOUR.WHITE)) {
                currentPlayerLabel.setText("White Wins");
            } else if (colour.get().equals(COLOUR.BLACK)) {
                currentPlayerLabel.setText("Black Wins");
            }
        } else {
            currentPlayerLabel.setText("Draw");
        }
    }

    public Optional<ImmutablePosition> getClickedPos() {
        return boardGridPanel.getClickedPos();
    }

    public void getFocus() {
        mainPanel.requestFocusInWindow();
    }
}
