import java.util.ArrayList;

public class User{

    public User(String name, int ID, ArrayList<Integer> collectoins, ArrayList<Integer> reviws){
        this.name = name;
        this.ID = ID;
        this.collactionsList = collectoins;
        this.reviewList = reviws;
    }

    public String GetName(){
        return this.name;
    }
    public int GetID(){
        return this.ID;
    }
    public int GetCollection(int tarID){

        for(int x=0; x<collactionsList.size(); x++)
        {
            if(collactionsList.get(x)==tarID)
            {
                return x;
            }
        }

        return 0;
    }

    public void AddCollection(int tarID)
    {
        collactionsList.add(tarID);
    }

    public void DeleteCollection(int tarID)//each collection gets a id
    {
        for(int x=0; x<collactionsList.size(); x++)
        {
            if(collactionsList.get(x)==tarID)
            {
                collactionsList.remove(x);
                return;
            }
        }
    }

    //if i dont brin gin the review then i cannot add or delete it
    public int GetReview(int tarID){

        for(int x=0; x<reviewList.size(); x++)
        {
            if(reviewList.get(x)==tarID)
            {
                return x;
            }
        }

        return 0;
    }

    public void addReview(int tarID)
    {
        reviewList.add(tarID);
    }

    public void deleteReview(int tarID)
    {
        for(int x=0; x<reviewList.size(); x++)
        {
            if(reviewList.get(x)==tarID)
            {
                reviewList.remove(x);
                return;
            }
        }
    }
    public ArrayList<Integer> getReviewList(){return reviewList;}
    public ArrayList<Integer> getCollactionsList(){return collactionsList;}

    private String name;
    private int ID;
    private ArrayList<Integer> collactionsList;
    private ArrayList<Integer> reviewList;
}