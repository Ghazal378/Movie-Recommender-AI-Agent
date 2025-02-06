# Movie-Recommender-AI-Agent

Knowledge Graph Construction:
The Neo4j database is populated with movie data from a CSV file.
Data includes attributes like title, genres, scores, release dates, and summaries.

User Input and API Call:
Users provide movie-related input (e.g., title or genre) via the application.
The system calls an api to fetch relevant movies.
Movie Details Retrieval:

For matching movies, the app retrieves detailed attributes from knowledge graph like:
Title, Tagline, Genres, Summary, Runtime, Poster URL, and IMDb ID.
Displays results in an interactive GUI.
