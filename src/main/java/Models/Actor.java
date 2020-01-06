package Models;

public class Actor
{
    private int id_actor = 0;
    private String name;
    private String surname;
    private String character;

    public Actor(int id_actor, String name, String surname, String character)
    {
        this.id_actor = id_actor;
        this.name = name;
        this.surname = surname;
        this.character = character;
    }

    public int getId_actor()
    {
        return id_actor;
    }

    public void setId_actor(int id_actor)
    {
        this.id_actor = id_actor;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getCharacter()
    {
        return character;
    }

    @Override
    public String toString()
    {
        return "Actor{" +
                "id_actor=" + id_actor +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", character='" + character + '\'' +
                '}';
    }
}
