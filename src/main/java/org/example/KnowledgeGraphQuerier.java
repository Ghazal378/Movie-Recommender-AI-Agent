package org.example;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KnowledgeGraphQuerier {

    private final Driver driver;

    public KnowledgeGraphQuerier(Driver driver) {
        this.driver = driver;
    }

    // Query the KG for detailed information about a movie by title.
    public Movie getMovieByTitle(String title) {
        try (Session session = driver.session()) {
            String query = "MATCH (m:Movie {title: $title})-[:BELONGS_TO]->(g:Genre) " +
                    "RETURN m.id AS id, m.title AS title, m.score AS score, m.releaseDate AS releaseDate, " +
                    "m.runTime AS runTime, m.isAdult AS isAdult, m.imdbId AS imdbId, m.language AS language, " +
                    "m.summary AS summary, m.posterPath AS posterPath, m.tagLine AS tagLine, " +
                    "COLLECT(g.name) AS genres";

            Result result = session.run(query, Map.of("title", title));

            if (result.hasNext()) {
                Record record = result.next();
                return new Movie(
                        record.get("id").asString(),
                        record.get("title").asString(),
                        record.get("score").asFloat(),
                        record.get("releaseDate").asString(),
                        record.get("runTime").asInt(),
                        record.get("isAdult").asBoolean(),
                        record.get("imdbId").asString(),
                        record.get("language").asString(),
                        record.get("summary").asString(),
                        record.get("posterPath").asString(),
                        record.get("tagLine").asString(),
                        record.get("genres").asList(value -> value.asString())
                );
            }
        } catch (Exception e) {
            System.err.println("Error retrieving movie by title: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Return null if the movie is not found
    }

    public void close() {
        driver.close();
    }
}
