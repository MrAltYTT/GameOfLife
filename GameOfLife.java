package gol;
/*package gol;
import javax.swing.*;
import java.awt.*;
public class GameOfLife {
    private int rows;
    private int columns;
    private boolean[][] grid;
    private boolean[][] nextGen;

    public GameOfLife(int rs, int cs) {
        this.rows = rs;
        this.columns = cs;
        this.grid = new boolean[rows][columns];
        this.nextGen = new boolean[rows][columns];
    }

    
    public void showGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(grid[i][j] ? "⬛ " : "⬜ ");
            }
            System.out.println();
        }
        System.out.println();
    }
        

    private int countLiveNeighbors(int row, int column) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int neighborR = row + i;
                int neighborC = column + j;

                if (neighborR >= 0 && neighborR < rows && neighborC >= 0 && neighborC < columns) {
                    if (grid[neighborR][neighborC]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void nextGen() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);
                if (grid[i][j]) {
                    nextGen[i][j] = liveNeighbors == 2 || liveNeighbors == 3;
                } else {
                    nextGen[i][j] = liveNeighbors == 3;
                }
            }
        }
        boolean[][] temp = grid;
        grid = nextGen;
        nextGen = temp;
    }

    
    public void randomize() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = Math.random() < 0.5;
            }
        }
    }
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameOfLife extends JPanel implements ActionListener {
    private int rows = 50;
    private int cols = 50;
    private boolean[][] cells = new boolean[rows][cols];
    private Timer timer;
    private int cellSize = 10;

    public GameOfLife() {
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));

        // Initialize the timer to update the game state
        timer = new Timer(100, this);
        timer.start();

        // Random initialization of cells
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = Math.random() < 0.2; // 20% chance cell is alive
            }
        }

        // Add mouse listener to toggle cells manually
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / cellSize;
                int row = e.getY() / cellSize;
                if (col >= 0 && col < cols && row >= 0 && row < rows) {
                    cells[row][col] = !cells[row][col];
                    repaint();
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw each cell
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (cells[row][col]) {
                    g.setColor(Color.BLACK); // Alive cell
                } else {
                    g.setColor(Color.WHITE); // Dead cell
                }
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);

                // Draw grid lines
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }

    // Update the state of the cells based on Game of Life rules
    public void updateCells() {
        boolean[][] nextGen = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int neighbors = countNeighbors(row, col);

                if (cells[row][col]) {
                    // Any live cell with two or three live neighbours survives.
                    nextGen[row][col] = (neighbors == 2 || neighbors == 3);
                } else {
                    // Any dead cell with three live neighbours becomes a live cell.
                    nextGen[row][col] = (neighbors == 3);
                }
            }
        }

        cells = nextGen;
    }

    // Count the number of alive neighbors for a given cell
    public int countNeighbors(int row, int col) {
        int count = 0;

        // Check all surrounding cells
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Skip the cell itself
                if (i == 0 && j == 0) continue;

                int r = (row + i + rows) % rows; // Wrap around edges
                int c = (col + j + cols) % cols;

                if (cells[r][c]) count++;
            }
        }
        return count;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateCells();
        repaint();
    }

    // Add these methods to control the timer
    public void startTimer() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void stopTimer() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }
}
