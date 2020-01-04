package Models;

public class Actor
{
    String name;
    String surname;
    String character;

    public Actor(String name, String surname, String character)
    {
        this.name = name;
        this.surname = surname;
        this.character = character;
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
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", character='" + character + '\'' +
                '}';
    }
}
