package application.mcts;

import application.game.COLOUR;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class GenerateNNData {

    static ImmutableList<Integer> canonicalBoard(TreeNode node) {
        ImmutableList<Integer> intBoard = node.getCurrentBoard().asIntArray();
        if (node.getRootColour().equals(COLOUR.BLACK)) {
            return changeBoardPerspective(intBoard, node.getCurrentBoard().getBoardSize());
        }
        return intBoard;
    }


    static String write(ImmutableList<Integer> intBoard, ImmutableList<Double> policyBoard, Double result) {
        StringBuilder builder = new StringBuilder();
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
        return builder.toString();
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

    public static String save(TreeNode terminalNode) {
        TreeNode node = terminalNode;
        StringBuilder builder = new StringBuilder();

        Optional<COLOUR> winner = node.getCurrentBoard().getWinner(false);
        double result = 0.0;
        double oppResult = 0.0;

        if (winner.isPresent()) {
            if (winner.get().equals(COLOUR.WHITE)) {
                result = 1.0;
                oppResult = -1.0;
            } else if (winner.get().equals(COLOUR.BLACK)) {
                result = -1.0;
                oppResult = 1.0;
            }
        }
        node = node.getParent();
        while (node != null && node.getParent() != null) {
            ImmutableList<Integer> intBoard = canonicalBoard(node);
            if (!node.getRootColour().equals(node.getColour())) {
                intBoard = changeBoardPerspective(intBoard, node.getCurrentBoard().getBoardSize());
            }
            if (node.getColour().equals(COLOUR.WHITE)) {
                builder.append(write(intBoard, node.getTrainingPolicy(result), result));
                builder.append(",");
            } else if (node.getColour().equals(COLOUR.BLACK)) {
                builder.append(write(intBoard, node.getTrainingPolicy(result), oppResult));
                builder.append(",");
            }


            node = node.getParent();
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
