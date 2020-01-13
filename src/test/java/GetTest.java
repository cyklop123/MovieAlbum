import Models.Actor;
import org.junit.Assert;
import org.junit.Test;


public class GetTest
{
    @Test
    public void setterandgetterTest()
    {
        //is
        Actor actor = new Actor(0, "test", "test1", "test2");
        //then
        String name = actor.getName();
        String surname = actor.getSurname();
        String character = actor.getCharacter();
        int id_actor = actor.getId_actor();
        //expected
        Assert.assertEquals("test",name);
        Assert.assertEquals("test1",surname);
        Assert.assertEquals("test2",character);
        Assert.assertEquals(0,id_actor);
    }
}
