package application.mcts;

import application.game.Board;
import application.game.COLOUR;
import application.game.Verifier;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static application.mcts.GenerateTrainingData.forNeuralNet;
import static application.mcts.GenerateTrainingData.write;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GenerateTrainingDataTest {
    private Board board;

    @Before
    public void setup() {
        board = new Board(4, new Verifier(), 0.0, 0.0, 0.0);
    }

    @Test
    public void writeSingleBoardTest() {
        File file = new File("intBoards/testWrite.txt");
        file.delete();
        try {
            write("testWrite.txt", board.asIntArray(), null);
            String expected = "[0,0,0,0,0,1,-1,0,0,-1,1,0,0,0,0,0]";
            assertEquals(expected, readFile("testWrite.txt").get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImmutableList<String> readFile(String filename) throws IOException {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        File file = new File("intBoards/" + filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            builder.add(line);
        }
        return builder.build();
    }

    @Test
    public void convertForNNTest() {
        ImmutableList<Integer> whiteNNBoard = forNeuralNet(board, COLOUR.WHITE);
        ImmutableList<Integer> whiteExpected = ImmutableList.of(
                0, 0, 0, 0,
                0, 1, -1, 0,
                0, -1, 1, 0,
                0, 0, 0, 0);
        assertEquals(whiteExpected, whiteNNBoard);
        ImmutableList<Integer> blackExpected = ImmutableList.of(
                0, 0, 0, 0,
                0, -1, 1, 0,
                0, 1, -1, 0,
                0, 0, 0, 0);
        ImmutableList<Integer> blackNNBoard = forNeuralNet(board, COLOUR.BLACK);
        assertEquals(blackExpected, blackNNBoard);
    }
}



