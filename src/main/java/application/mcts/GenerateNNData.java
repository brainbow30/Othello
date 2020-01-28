package application.mcts;

import application.game.COLOUR;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateNNData {
    private BufferedWriter outputWriter;
    private String filename;
    private StringBuilder builder;

    public GenerateNNData(String filename) {
        this.filename = filename;
        open();
    }

    public void open() {
        try {
            outputWriter = new BufferedWriter(new FileWriter("intBoards/" + filename, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ImmutableList<Integer> canonicalBoard(TreeNode node) {
        ImmutableList<Integer> intBoard = node.getCurrentBoard().asIntArray();
        if (node.getColour().equals(COLOUR.BLACK)) {
            return changeBoardPerspective(intBoard, node.getCurrentBoard().getBoardSize());
        }
        return intBoard;
    }


    void write(ImmutableList<Integer> intBoard, ImmutableList<Double> policyBoard, Integer result) {
        builder.append("[[");
        for (int pos = 0; pos < intBoard.size(); pos++) {
            builder.append(intBoard.get(pos));
            if (pos + 1 != intBoard.size()) {
                builder.append(",");
            }
        }

        builder.append("],[");
        for (int pos = 0; pos < policyBoard.size(); pos++) {
            builder.append(String.format("%.8f", policyBoard.get(pos)));
            if (pos + 1 != policyBoard.size()) {
                builder.append(",");
            }
        }

        builder.append("]," + result);
        builder.append("]");


    }

    public void close() {
        try {
            outputWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ImmutableList<Integer> changeBoardPerspective(ImmutableList<Integer> intBoard, Integer boardSize) {
        ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        for (Integer pos : intBoard) {
            if (pos == 1) {
                builder.add(-1);
            } else if (pos == -1) {
                builder.add(1);
            } else {
                builder.add(0);
            }
        }
        ImmutableList<Integer> unrotatedBoard = ImmutableList.copyOf(builder.build());
        return rotateBoard(unrotatedBoard, boardSize);
    }

    static ImmutableList rotateBoard(ImmutableList intArray, Integer boardSize) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int y = boardSize - 1; y >= 0; y--) {
            for (int x = boardSize - 1; x >= 0; x--) {
                Object value = intArray.get(x + y * boardSize);
                builder.add(value);
            }
        }
        return builder.build();
    }

    public void save(TreeNode terminalNode) {
        builder = new StringBuilder();
        builder.append("[");
        Optional<COLOUR> winner = terminalNode.getCurrentBoard().getWinner(false);
        int result = 0;
        int oppResult = 0;

        if (winner.isPresent()) {
            if (winner.get().equals(COLOUR.WHITE)) {
                result = 1;
                oppResult = -1;
            } else if (winner.get().equals(COLOUR.BLACK)) {
                result = -1;
                oppResult = 1;
            }
        }
        Integer boardSize = terminalNode.getCurrentBoard().getBoardSize();
        while (terminalNode.getParent() != null) {
            ImmutableList<Integer> intBoard = canonicalBoard(terminalNode);
            if (terminalNode.getColour().equals(COLOUR.WHITE)) {
                write(intBoard, terminalNode.getTrainingPolicy(result), result);
                builder.append(",");
            } else if (terminalNode.getColour().equals(COLOUR.BLACK)) {
                write(intBoard, rotateBoard(terminalNode.getTrainingPolicy(oppResult), boardSize), oppResult);
                builder.append(",");
            }


            terminalNode = terminalNode.getParent();
        }
        ImmutableList<Integer> intBoard = canonicalBoard(terminalNode);
        if (terminalNode.getColour().equals(COLOUR.WHITE)) {
            write(intBoard, terminalNode.getTrainingPolicy(result), result);
        } else if (terminalNode.getColour().equals(COLOUR.BLACK)) {
            write(intBoard, rotateBoard(terminalNode.getTrainingPolicy(oppResult), boardSize), oppResult);
        }
        builder.append("]");
        try {
            outputWriter.write(builder.toString());
            outputWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
