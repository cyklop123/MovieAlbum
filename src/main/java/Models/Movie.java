package Models;

import java.util.ArrayList;
import java.util.List;

public class Movie
{
    String title;
    String description;
    List<Actor> cast;
    int rating;

    private Movie(){};

    public Movie(String title, String description, List<Actor> cast, int rating)
    {
        this.title = title;
        this.description = description;
        this.cast = cast;
        this.rating = rating;
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

    public static class Builder{
        String title = "";
        String description = "";
        List<Actor> cast = new ArrayList<>();
        int rating = 0;

        public Builder setTitle(String title)
        {
            this.title = title;
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

        public Builder addActor(Actor actor)
        {
            cast.add(actor);
            return this;
        }

        public Builder addActor(String name, String surname, String character)
        {
            cast.add(new Actor(name, surname, character));
            return this;
        }

        public Movie build()
        {
            return new Movie(title, description, cast, rating);
        }
    }

    @Override
    public String toString()
    {
        return "Movie{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cast=" + cast.toString() +
                ", rating=" + rating +
                '}';
    }
}
