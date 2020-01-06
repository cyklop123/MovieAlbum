package Models;

import java.util.ArrayList;
import java.util.List;

public class Movie
{
    private int id_movie;
    private String title;
    private String description;
    private List<Actor> cast;
    private int rating;

    private Movie(){};

    public Movie(int id_movie, String title, String description, List<Actor> cast, int rating)
    {
        this.id_movie=id_movie;
        this.title = title;
        this.description = description;
        this.cast = cast;
        this.rating = rating;
    }

    public int getId_movie()
    {
        return id_movie;
    }

    public void setId_movie(int id_movie)
    {
        this.id_movie = id_movie;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public List<Actor> getCast()
    {
        return cast;
    }

    public int getRating()
    {
        return rating;
    }

    public void setCast(List<Actor> cast)
    {
        this.cast = cast;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public void addActor(Actor actor)
    {
        cast.add(actor);
    }

    public void addActor(int id_actor, String name, String surname, String character)
    {
        cast.add(new Actor(id_actor, name, surname, character));
    }


    public static class Builder{
        private int id_movie = 0;
        private String title = "";
        private String description = "";
        private List<Actor> cast = new ArrayList<>();
        private int rating = 0;

        public Builder setTitle(String title)
        {
            this.title = title;
            return this;
        }

        public Builder setId_movie(int id_movie){
            this.id_movie = id_movie;
            return this;
        }

        public Builder setDescription(String description)
        {
            this.description = description;
            return this;
        }

        public Builder setRating(int rating)
        {
            this.rating = rating;
            return this;
        }

        public Builder setCast(List<Actor> cast)
        {
            this.cast.addAll(cast);
            return this;
        }

        public Movie build()
        {
            return new Movie(id_movie, title, description, cast, rating);
        }
    }

    @Override
    public String toString()
    {
        return "Movie{" +
                "id_movie=" + id_movie +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cast=" + cast.toString() +
                ", rating=" + rating +
                '}';
    }
}
