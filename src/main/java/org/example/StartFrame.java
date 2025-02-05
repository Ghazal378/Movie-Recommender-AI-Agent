package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame {

    private JTextField genreTextField;
    private JTextField actorTextField;
    private JTextField directorTextField;

    public StartFrame(KnowledgeGraphQuerier querier) {
        this.setTitle("Movie Recommender");
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());

        // Background panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the image
                Image backgroundImage = new ImageIcon("src/main/resources/background.jpg").getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set layout for the background panel
        backgroundPanel.setLayout(null); // Use null layout for absolute positioning

        // Genre TextField
        JLabel genreLabel = new JLabel("Preferred Genre:");
        genreLabel.setForeground(Color.WHITE); // Label text color
        genreLabel.setBounds(50, 50, 150, 30); // Positioning label
        backgroundPanel.add(genreLabel);

        genreTextField = new JTextField();
        genreTextField.setBounds(200, 50, 200, 30); // Positioning text field
        backgroundPanel.add(genreTextField);

        // Actor TextField
        JLabel actorLabel = new JLabel("Preferred Actor/Actress:");
        actorLabel.setForeground(Color.WHITE); // Label text color
        actorLabel.setBounds(50, 100, 150, 30); // Positioning label
        backgroundPanel.add(actorLabel);

        actorTextField = new JTextField();
        actorTextField.setBounds(200, 100, 200, 30); // Positioning text field
        backgroundPanel.add(actorTextField);

        //Director TextField
        JLabel directorLabel = new JLabel("Preferred Director:");
        directorLabel.setForeground(Color.WHITE); // Label text color
        directorLabel.setBounds(50, 150, 150, 30); // Positioning label
        backgroundPanel.add(directorLabel);

        directorTextField= new JTextField();
        directorTextField.setBounds(200, 150, 200, 30); // Positioning text field
        backgroundPanel.add(directorTextField);

        // Recommend Button
        JButton recommendButton = new JButton("Recommend");
        recommendButton.setBounds(50, 200, 150, 40); // Positioning button
        backgroundPanel.add(recommendButton);

        // Reset Button
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(250, 200, 150, 40); // Positioning button
        backgroundPanel.add(resetButton);

        // Add ActionListener to Recommend button
        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String genre = genreTextField.getText();
                String actor = actorTextField.getText();
                String director = directorTextField.getText();

                if (genre.isEmpty() && actor.isEmpty() && director.isEmpty()) {
                    JOptionPane.showMessageDialog(StartFrame.this, "Please Enter At Least One Argument", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Call your recommendation logic here
                    try {
                        // Fetch recommended movies based on genre and actor and director input
                        MovieRecommendationApp.callAPI(genre, actor,director,querier);
                        dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Add ActionListener to Reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text fields
                genreTextField.setText("");
                actorTextField.setText("");
            }
        });

        // Add the panel to the frame
        setContentPane(backgroundPanel);
        this.setVisible(true);
    }

}
