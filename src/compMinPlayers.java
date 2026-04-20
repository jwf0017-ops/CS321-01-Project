import java.util.Comparator;

public class compMinPlayers implements Comparator<Game> {
    public compMinPlayers() {

    }

    public int compare(Game game1, Game game2)
    {
        int result = 0;
        int game1Players = game1.getMinPlayers();
        int game2Players = game2.getMinPlayers();
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