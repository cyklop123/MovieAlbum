package Database;

import Models.Actor;
import Models.Movie;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase
{
    static MovieDatabase instance = null;
    public static MovieDatabase getInstance()
    {
        if(instance == null)
            instance = new MovieDatabase();
        return instance;
    }

    private Connection connection;
    private Statement statement;
    private MovieDatabase()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:movieDatabase.db");
            statement = connection.createStatement();
        } catch (SQLException e)
        {
            System.err.println("Błąd połączenia z bazą danych");
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            System.err.println("Nie znaleziono silnika bazy danych");
            e.printStackTrace();
        }
        this.initTables();
    }

    @Override
    protected void finalize() throws Throwable
    {
        try
        {
            connection.close();
        } catch (SQLException e)
        {
            System.err.println("Błąd rozłączania z bazą danych");
            e.printStackTrace();
        }
        super.finalize();
    }

    private void initTables()
    {
        String createMovies = "CREATE TABLE IF NOT EXISTS movies(id_movie INTEGER PRIMARY KEY AUTOINCREMENT, title varchar(41), description varchar(255), rating int)";
        String createActors = "CREATE TABLE IF NOT EXISTS actors(id_actor INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(41), surname varchar(255), character varchar(255), id_movie int)";
        try{
            statement.execute(createMovies);
            statement.execute(createActors);
        } catch (SQLException e)
        {
            System.err.println("Błąd tworzenia tabel");
            e.printStackTrace();
        }
    }

    public List<Movie> selectMovies(){
        List<Movie> movies = new ArrayList<>();

        try{
            ResultSet movieResSet = statement.executeQuery("SELECT * FROM movies");

            //pobranie danych z tabeli movie
            while(movieResSet.next())
            {
                Movie movie = new Movie.Builder()
                        .setId_movie(movieResSet.getInt("id_movie"))
                        .setTitle(movieResSet.getString("title"))
                        .setDescription(movieResSet.getString("description"))
                        .setRating(movieResSet.getInt("rating"))
                        .build();

                movies.add(movie);
            }

            //pobranie i dolepienie danych z actors
            for(Movie movie: movies)
            {
                ResultSet actorsResSet = statement.executeQuery("SELECT * FROM actors WHERE id_movie=" +movie.getId_movie());
                while (actorsResSet.next())
                {
                    movie.addActor(
                            actorsResSet.getInt("id_actor"),
                            actorsResSet.getString("name"),
                            actorsResSet.getString("surname"),
                            actorsResSet.getString("character")
                        );
                }
            }

        } catch (SQLException e)
        {
            System.err.println("Błąd wykonania zapytania");
            e.printStackTrace();
        }
        finally
        {
            return movies;
        }
    }

    public Movie insertMovie(Movie movie)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO movies ('title', 'description', 'rating') VALUES (?,?,?)");
            preparedStatement.setString(1,movie.getTitle());
            preparedStatement.setString(2,movie.getDescription());
            preparedStatement.setInt(3,movie.getRating());
            preparedStatement.execute();

            ResultSet idResult = statement.executeQuery("SELECT MAX(id_movie) AS id FROM movies");
            int id_movie = idResult.getInt("id");

            movie.setId_movie(id_movie);

            for(Actor actor: movie.getCast())
            {
                preparedStatement = connection.prepareStatement("INSERT INTO actors VALUES (NULL,?,?,?,?)");
                preparedStatement.setString(1, actor.getName());
                preparedStatement.setString(2, actor.getSurname());
                preparedStatement.setString(3, actor.getCharacter());
                preparedStatement.setInt(4, id_movie);
                preparedStatement.execute();

                idResult = statement.executeQuery("SELECT MAX(id_actor) AS id FROM actors");
                int id_actor = idResult.getInt("id");
                actor.setId_actor(id_actor);
            }

        } catch (SQLException e)
        {
            System.err.println("Błąd dodawania filmu do bazy");
            e.printStackTrace();
        }
        finally
        {
            return movie;
        }
    }

    public void deleteMovie(Movie movie)
    {
        for (Actor actor: movie.getCast())
        {
            try
            {
                statement.execute("DELETE FROM actors WHERE id_actor=" + actor.getId_actor());

            } catch (SQLException e)
            {
                System.err.println("Błąd usuwania aktora");
                e.printStackTrace();
            }

        }
        try{
            statement.execute("DELETE FROM movies WHERE id_movie=" + movie.getId_movie());
        } catch (SQLException e)
        {
            System.err.println("Błąd usuwania filmu");
            e.printStackTrace();
        }
    }

    public Movie selectMovie(int id_movie)
    {
        Movie movie = null;
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movies WHERE id_movie="+id_movie);

            movie = new Movie.Builder()
                    .setId_movie(resultSet.getInt("id_movie"))
                    .setTitle(resultSet.getString("title"))
                    .setDescription(resultSet.getString("description"))
                    .setRating(resultSet.getInt("rating"))
                    .build();

            resultSet = statement.executeQuery("SELECT * FROM actors WHERE id_movie="+id_movie);

            while (resultSet.next())
            {
                movie.addActor(
                        resultSet.getInt("id_actor"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("character")
                );
            }

        } catch (SQLException e)
        {
            System.err.println("Błąd pobierania filmu");
            e.printStackTrace();
        }
        finally
        {
            return movie;
        }
    }

    public Movie updateMovie(Movie movie)
    {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE movies SET title=?, description=?, rating=? WHERE id_movie=?");
            preparedStatement.setString(1,movie.getTitle());
            preparedStatement.setString(2,movie.getDescription());
            preparedStatement.setInt(3,movie.getRating());
            preparedStatement.setInt(4, movie.getId_movie());
            preparedStatement.execute();

            ResultSet actorsResSet = statement.executeQuery("SELECT id_actor FROM actors WHERE id_movie="+movie.getId_movie());
            List<Integer> actorIds = new ArrayList<>();
            while (actorsResSet.next())
                actorIds.add(actorsResSet.getInt("id_actor"));

            System.out.println(actorIds.toString());

            for(Actor actor: movie.getCast())
            {
                if(actor.getId_actor() == 0)
                {
                    preparedStatement = connection.prepareStatement("INSERT INTO actors VALUES (NULL,?,?,?,?)");
                    preparedStatement.setString(1, actor.getName());
                    preparedStatement.setString(2, actor.getSurname());
                    preparedStatement.setString(3, actor.getCharacter());
                    preparedStatement.setInt(4, movie.getId_movie());
                    preparedStatement.execute();

                    ResultSet idActorRes = statement.executeQuery("SELECT max(id_actor) AS id FROM actors");
                    actor.setId_actor(idActorRes.getInt("id"));

                }
                else
                {
                    if(actorIds.contains(actor.getId_actor()))
                        actorIds.remove((Object) actor.getId_actor());
                }
            }

            System.out.println(actorIds.toString());

            for(int i: actorIds)
            {
                statement.execute("DELETE FROM actors WHERE id_actor="+i);
            }
        } catch (SQLException e)
        {
            System.err.println("Błąd aktualizowania filmu");
            e.printStackTrace();
        }
        finally
        {
            return movie;
        }
    }

    public void testDB()
    {
        try
        {
            connection.close();
            connection = DriverManager.getConnection("jdbc:sqlite::resource:testDB.db");
            statement = connection.createStatement();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
