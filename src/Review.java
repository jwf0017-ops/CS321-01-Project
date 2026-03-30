


public class Review implements Comparable<Review> {

    //
    //Variables
    //
    private int userID;//ID of user the review belongs to
    private int gameID;//ID of game the review belongs to
    private int ID;//number assigned to each instance of the review class
    private int rating;//A rating out of 5 stars
    private String desc;//Written text of the review

    //
    //Comparable Structure
    //
    @Override
    public int compareTo(Review otherReview) {
        return rating-otherReview.getRating();
    }

    //
    //Constructors
    //
    public Review() //Default constructor. Not expected to be used
    {
        userID=0;
        gameID=0;
        ID=0;
        rating=5;

    }

    public Review(int u, int g, int i, int r, String d)//Constructor to initialize everything on creation
    {
        userID=u;
        gameID=g;
        ID=i;
        rating=r;
        desc=d;
    }

    //
    //Accessors
    //
    public int getUserID()
    {
        return userID;
    }
    public int getGameID()
    {
        return gameID;
    }
    public int getID()
    {
        return ID;
    }
    public int getRating()
    {
        return rating;
    }
    public String getDesc()
    {
        return desc;
    }

    //
    //Mutators
    //
    public void setUserID(int u)
    {
        userID=u;
    }
    public void setGameID(int g)
    {
        gameID=g;
    }
    public void setID(int i)
    {
        ID=i;
    }
    public void setRating(int r)
    {
        rating=r;
    }
    public void setDesc(String d)
    {
        desc=d;
    }

    //
    //Unique Functions
    //
    public void editReview(int r, String d)//For user convenience, the rating and description can both be edited after creation. The IDs should not be modified.
    {
        setRating(r);
        setDesc(d);
    }







}
