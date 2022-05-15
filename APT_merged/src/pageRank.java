import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

class pair{
    public double rank;
    public String link;
    public pair(String L, double R)
    {
        rank = R;
        link = L;
    }
    public pair(){}
}
class GFG{

    // A utility function to swap two elements
    static void swap(ArrayList<pair> arr, int i, int j)
    {
        pair temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    /* This function takes last element as pivot, places
    the pivot element at its correct position in sorted
    array, and places all smaller (smaller than pivot)
    to left of pivot and all greater elements to right
    of pivot */
    static int partition(ArrayList<pair> arr, int low, int high)
    {

        // pivot
        pair pivot = arr.get(high);

        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);

        for(int j = low; j <= high - 1; j++)
        {

            // If current element is smaller
            // than the pivot

            //this edited to make the sort be done by the rank
            if (arr.get(j).rank > pivot.rank)
            {

                // Increment index of
                // bigger element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    /* The main function that implements QuickSort
            arr[] --> Array to be sorted,
            low --> Starting index,
            high --> Ending index
    */
    static void quickSort(ArrayList<pair> arr, int low, int high)
    {
        if (low < high)
        {

            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(arr, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static void printArr(ArrayList<pair> arr)
    {
        for (pair p:arr) {
            System.out.println("the link is "+p.link+"\n");
            System.out.println("and it's rank ="+p.rank+"\n\n");
        }
    }

    // Driver Code
}


public class pageRank {
    // Java implementation of QuickSort


    public static ArrayList<String> rankByPopluarity(Connection dbConnection, ArrayList<pair>links) throws SQLException {
        ArrayList<pair> URLS = new ArrayList<>();
        pair currentURL =null;
        //make the array of pairs from the arry of string and from the data base
        //and merge the two arrays
        double rankOfIdf;
        double rankOfPop;


        for (int i=0;i< links.size();i++ ) {
            currentURL = links.get(i);
            rankOfIdf =currentURL.rank;
            rankOfPop =  DBManager.getTheRankOfURL(dbConnection, currentURL.link);
            currentURL.rank = rankOfIdf*rankOfPop/(rankOfIdf+rankOfPop);

            URLS.add(currentURL);
        }
        GFG.quickSort(URLS,0,links.size()-1);

        //another approach from [Karim] : we may rearrange by give it weights to mix the two algorithms
        ArrayList<String> result=new ArrayList<>();
        for(int i = 0;i< URLS.size();i++)
        {
            result.add(URLS.get(i).link);
        }
        return result;
    }

    public static void main(String[] args)
    {
        ArrayList<pair> arr = new ArrayList<>();
        pair temp[] ;
        temp = new pair[6];
        temp[0] = new pair();
        temp[0].link="www.cnn.com";
        temp[0].rank= 5;
        arr.add(temp[0]);
        temp[1] = new pair();
        temp[1].link="www.bbc.com";
        temp[1].rank= 2;
        arr.add(temp[1]);
        temp[2] = new pair();
        temp[2].link="www.aljazera.com";
        temp[2].rank= 4;
        arr.add(temp[2]);

        temp[3] = new pair();
        temp[3].link="www.foxnews.com";
        temp[3].rank= 9;
        arr.add(temp[3]);

        temp[4] = new pair();
        temp[4].link="www.60minutes.com";
        temp[4].rank= 4;
        arr.add(temp[4]);

        temp[5] = new pair();
        temp[5].link="www.bolbol.com";
        temp[5].rank= 1;
        arr.add(temp[5]);
        GFG.quickSort(arr,0,arr.size()-1);
        GFG.printArr(arr);
    }
}
