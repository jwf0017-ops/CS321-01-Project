import java.util.Comparator;

public class compID implements Comparator<Game> {
    public compID() {

    }

    public int compare(Game game1, Game game2)
    {
        int result = 0;
        int game1ID = game1.getID();
        int game2ID = game2.getID();
        if (game1ID < game2ID)
        {
            result = -1;
        }
        else if (game1ID > game2ID)
        {
            result = 1;
        }

        return result;
    }
}
