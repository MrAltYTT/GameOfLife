package gol;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Please dont fail omg");
        GameOfLife gol = new GameOfLife();

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            private boolean running = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (running) {
                    gol.stopTimer();
                    pauseButton.setText("Resume");
                } else {
                    gol.startTimer();
                    pauseButton.setText("Pause");
                }
                running = !running;
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(pauseButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(gol, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
