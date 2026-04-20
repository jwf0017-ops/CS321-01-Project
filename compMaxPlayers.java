import java.util.Comparator;

public class compMaxPlayers implements Comparator<Game> {
    public compMaxPlayers() {

    }

    public int compare(Game game1, Game game2)
    {
        int result = 0;
        int game1Players = game1.getMaxPlayers();
        int game2Players = game2.getMaxPlayers();
        if (game1Players < game2Players)
        {
            result = -1;
        }
        else if (game1Players > game2Players)
        {
            result = 1;
        }

        return result;
    }
}