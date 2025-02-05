package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class MovieDetailsPanel extends JPanel {
    private String title;
    private String tagline;
    private double score;
    private String posterPath;
    private String summary;
    private String genres;

    public MovieDetailsPanel(Movie movie) {
        this.title = movie.getTitle();
        this.tagline = movie.getTagline();
        this.score = movie.getScore();
        this.posterPath = movie.getPosterPath();
        this.summary = movie.getSummary();
        this.genres = movie.getGenres().toString();

        setLayout(new BorderLayout(10, 10)); // Add some spacing between components
        setPreferredSize(new Dimension(400, 550));
        setBackground(Color.WHITE);

        // Title Section
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(34, 45, 65)); // Darker shade for the title
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        if (tagline != null && !tagline.isEmpty()) {
            JLabel taglineLabel = new JLabel("<html><i>" + tagline + "</i></html>", SwingConstants.CENTER);
            taglineLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            taglineLabel.setForeground(new Color(100, 100, 100)); // Gray shade for tagline
            titlePanel.add(taglineLabel, BorderLayout.SOUTH);
        }

        add(titlePanel, BorderLayout.NORTH);

        // Poster Section
        try {
            if (posterPath != null && !posterPath.isEmpty()) {
                String posterURL = "https://image.tmdb.org/t/p/w500" + posterPath;

                // Create ImageIcon from URL
                ImageIcon originalImageIcon = new ImageIcon(new URL(posterURL));
                Image scaledImage = originalImageIcon.getImage().getScaledInstance(250, 375, Image.SCALE_SMOOTH); // Maintain aspect ratio
                ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

                JLabel posterLabel = new JLabel(scaledImageIcon);
                posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
                posterLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the image
                posterLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Movie selectedMovie = movie;
                        MovieDetailsFrame detailsFrame = new MovieDetailsFrame(selectedMovie);
                        detailsFrame.setVisible(true);
                        // Show detailed movie info on click
                    }
                });
                add(posterLabel, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Score and Genres Section
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 1, 5, 5)); // Rows: 3, Columns: 1 with spacing
        infoPanel.setBackground(Color.WHITE);
        String formattedScore = String.format("%.2f", score);
        JLabel scoreLabel = new JLabel("★ Score: " + formattedScore, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(0, 128, 0)); // Green color for score

        JLabel genresLabel = new JLabel("<html><b>Genres:</b> " + genres + "</html>", SwingConstants.CENTER);
        genresLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        genresLabel.setForeground(new Color(60, 60, 60));

        infoPanel.add(scoreLabel);
        infoPanel.add(genresLabel);
        add(infoPanel, BorderLayout.SOUTH);
    }



}
