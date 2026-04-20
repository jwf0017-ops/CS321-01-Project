import java.util.Comparator;

public class compMinAge implements Comparator<Game> {
    public compMinAge() {

    }

    public int compare(Game game1, Game game2)
    {
        int result = 0;
        int game1Age = game1.getMinAge();
        int game2Age = game2.getMinAge();
        if (game1Age < game2Age)
        {
            result = -1;
        }
        else if (game1Age > game2Age)
        {
            result = 1;
        }

        return result;
    }
}