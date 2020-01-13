import Database.MovieDatabase;
import Models.Actor;
import Models.Movie;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SelectsTest
{
    @Test
    public void selectMoviesTest() throws Exception
    {
        //is
        List<Movie> isList = new ArrayList<>();

        Actor a1 = new Actor(1,"test","test","test");
        Actor a2 = new Actor(2,"test2","test2","test2");
        Movie m1 = new Movie.Builder().
                setId_movie(1).
                setTitle("test").
                setDescription("test").
                setRating(10).
                build();
        m1.addActor(a1);
        m1.addActor(a2);
        isList.add(m1);

        Actor a3 = new Actor(3,"qwerty","qwerty","qwerty");
        Actor a4 = new Actor(4,"qwerty","qwerty","qwerty");
        Movie m2 = new Movie.Builder().
                setId_movie(2).
                setTitle("qwerty").
                setDescription("qwerty").
                setRating(0).
                build();
        m2.addActor(a3);
        m2.addActor(a4);
        isList.add(m2);

        //then
        MovieDatabase db = MovieDatabase.getInstance();
        db.testDB();
        List<Movie> thenList = db.selectMovies();

        //expected
        Assert.assertEquals(isList.toString(),thenList.toString());
        db=null;
    }

    @Test
    public void selectTest()
    {
        //is
        Actor a1 = new Actor(1,"test","test","test");
        Actor a2 = new Actor(2,"test2","test2","test2");
        Movie isMovie = new Movie.Builder().
                setId_movie(1).
                setTitle("test").
                setDescription("test").
                setRating(10).
                build();
        isMovie.addActor(a1);
        isMovie.addActor(a2);

        //then
        MovieDatabase db = MovieDatabase.getInstance();
        db.testDB();
        Movie thenMovie = db.selectMovie(1);

        //expected
        Assert.assertEquals(isMovie.toString(), thenMovie.toString());
        db = null;
    }

}
