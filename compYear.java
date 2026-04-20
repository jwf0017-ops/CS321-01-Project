import java.util.Comparator;

public class compYear implements Comparator<Game> {
    public compYear() {

    }

    public int compare(Game game1, Game game2)
    {
        int result = 0;
        int game1Year = game1.getYearPublished();
        int game2Year = game2.getYearPublished();
        if (game1Year < game2Year)
        {
            result = -1;
        }
        else if (game1Year > game2Year)
        {
            result = 1;
        }

        return result;
    }
}
