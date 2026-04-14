

public class UserClass{
    String name;
    int ID;
    String[] collactionsList;
    String[] reviewList;

    public user(String name, int ID){
        this.name = name;
        this.ID = ID;
        this.collactionsList = new String[];
        this.reviewList = new String[];
    }

    public String GetName(){
        return this.name;
    }
    public String GetID(){
        return this.ID;
    }
    public String GetCollection(int id){
        //get the collection using the id will have to be drawn out further
        //put into tmp array
        String[] temparray;
        String tempvalue;
        int i  = 0;

        temparray = this.collactionsList;

        if(id < 0 || id >= temparray.length){
            return "Invalid collection id";
        }

        tempvalue = temparray[id];

        if(temparray[0] == null){
            System.out.println("No items in collection list");
            String text = "No collections";
            return text;
        }

        while(i < temparray.length){
            if(i == id){
                if(tempvalue == null){
                    String text = "No collection at that id";
                    return text;
                }
                return tempvalue;
            }
            i += 1;
        }

        return "all collections added";
    }

    public String AddCollection(String collection)
    {
        int length;
        int i = 0;

        while(i < this.collactionsList.length && this.collactionsList[i] != null){
            i += 1;
        }

        length = i;

        if(length >= this.collactionsList.length){
            String text = "collection list full";
            return text;
        }

        this.collactionsList[length] = collection;
        String text = "collection sucessfuly added";
        return text;
    }

    public String DeleteCollection(int id)//each collection gets a id
    {//get the collection using collection id
        //using temp to hold the collection
        String[] temparray;
        String tempvalue;
        int i = 0;

        temparray = this.collactionsList;

        if(id < 0 || id >= temparray.length){
            return "Invalid collection id";
        }

        tempvalue = temparray[id];

        if(tempvalue == null){
            String text = "collection not found";
            return text;
        }

        while(i < this.collactionsList.length){
            if(i == id){
                this.collactionsList[i] = null;
                String text = "collection deleted";
                return text;
            }
            i += 1;
        }

        String text = "collection not found";
        return text;
    }

    //if i dont brin gin the review then i cannot add or delete it
    public String GetReview(){
        return "review function not finished";
    }
}