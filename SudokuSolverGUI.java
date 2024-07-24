import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolverGUI extends JFrame implements ActionListener {
    private static final int SIZE = 9;
    private JTextField[][] textFields;
    private JButton solveButton;

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(SIZE, SIZE));

        // Initialize textFields array
        textFields = new JTextField[SIZE][SIZE];

        // Create text fields and add them to the main panel
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                textFields[i][j] = new JTextField(1);
                mainPanel.add(textFields[i][j]);
            }
        }

        // Create solve button
        solveButton = new JButton("Solve");
        solveButton.addActionListener(this);

        // Add main panel and solve button to the frame
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(solveButton, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == solveButton) {
            int[][] board = new int[SIZE][SIZE];

            // Get Sudoku puzzle from text fields
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    String text = textFields[i][j].getText();
                    if (!text.isEmpty()) {
                        board[i][j] = Integer.parseInt(text);
                    } else {
                        board[i][j] = 0;
                    }
                }
            }

            // Solve Sudoku puzzle
            if (solveSudoku(board)) {
                // Update text fields with solved puzzle
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        textFields[i][j].setText(Integer.toString(board[i][j]));
                    }
                }
                JOptionPane.showMessageDialog(this, "Sudoku puzzle solved!");
            } else {
                JOptionPane.showMessageDialog(this, "No solution exists for the given Sudoku puzzle.");
            }
        }
    }

    private boolean solveSudoku(int[][] board) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        // Find an empty cell
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        // If there are no empty cells, puzzle is solved
        if (isEmpty) {
            return true;
        }

        // Try placing digits from 1 to 9
        for (int num = 1; num <= SIZE; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board)) {
                    return true;
                } else {
                    board[row][col] = 0; // Backtrack
                }
            }
        }
        return false;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        // Check if the number is already present in the row or column
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // Check if the number is already present in the 3x3 grid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SudokuSolverGUI().setVisible(true);
            }
        });
    }
}
