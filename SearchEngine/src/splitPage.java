import java.io.*;

public class splitPage {
    @SuppressWarnings("resource")

    public static void splitheaders(String hText,String[] h) throws IOException
    {
        h[0]=hText;
        splitPage s=new splitPage();
        h[0]=s.readFile(h[0]);
    }
    public String readFile(String object) throws IOException
    {
        object=" "+object+" ";
        object=object.replaceAll("[^a-zA-Z0-9]", " ");
        @SuppressWarnings("resource")
        BufferedReader reader=new BufferedReader(new FileReader("stopwords.txt"));
        String line=reader.readLine();
        while (line != null)
        {
            if(object.contains(line))
            {
                object=object.replaceAll(" "+line+" ", " ");
            }
            line=reader.readLine();
        }
        object=object.replaceAll("\\s+", " ");
        object=object.trim();
        return object;
    }
    public void split (String title, String body, String description,String[] titleArray, String[] descriptionArray,String[] bodyArray/*,String title,String body,String description*/) throws IOException
    {
//		____________split_title______________

        titleArray[0]= title;
        titleArray[0]= readFile(titleArray[0]);

//		____________split_description____________

        descriptionArray[0]= description;
        descriptionArray[0]= readFile(descriptionArray[0]);


//		____________split_body_____________

        bodyArray[0]= body;
        bodyArray[0]= readFile(bodyArray[0]);
    }
}
