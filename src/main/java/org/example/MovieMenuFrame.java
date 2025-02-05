package org.example;

import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovieMenuFrame extends JFrame {
    public MovieMenuFrame(List<Movie> movieList){
        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JScrollPane scrollPane = new JScrollPane(moviePanel);
        scrollPane.setPreferredSize(new Dimension(1200, 800));

        // Add movie details panels to the scrollable panel
        for (Movie movie : movieList) {

            // Create a panel for each movie and add it to the movie panel
            MovieDetailsPanel movieDetailsPanel = new MovieDetailsPanel(movie);

            moviePanel.add(movieDetailsPanel);
        }
        // Add Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 10, 100, 40);
        backButton.addActionListener(e -> {

            String uri = "bolt://localhost:7687";
            String user = "neo4j";
            String password = "password";

            // Initialize Neo4j driver and clients.
            Driver driver = GraphDatabase.driver(uri, org.neo4j.driver.AuthTokens.basic(user, password));
            KnowledgeGraphQuerier kGQuerior = new KnowledgeGraphQuerier(driver);
            dispose();
            new StartFrame(kGQuerior);
        });
        moviePanel.add(backButton);

        // Display the scrollable panel with movie details in a new frame
        JFrame movieFrame = new JFrame("Recommended Movies");
        movieFrame.setSize(800, 600);
        movieFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieFrame.add(scrollPane);
        movieFrame.setVisible(true);
    }
}
