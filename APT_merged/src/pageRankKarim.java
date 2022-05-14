import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class pageRankKarim
{
    public static void splitheaders(String URL,String[] h,int index) throws IOException
    {
        Document doc= Jsoup.connect(URL).get();
        h[0]=" ";
        if(doc.select("h"+index).toString() != null)
        {
            h[0]=doc.select("h"+index).toString();
            h[0]=splitPage.readFile(h[0]);
        }
    }
    public static double [] calculateIDF(int [] numOfDocContainingTerm, int totalNumOfDoc,String[] q)
    {
        double [] IDF=new double [q.length];
        for(int i=0;i<q.length;i++)
        {
            if(numOfDocContainingTerm[i]!=0)
                IDF[i]=Math.log10((float)totalNumOfDoc/numOfDocContainingTerm[i]);
            else
                IDF[i] = 0;
        }
        return IDF;
    }
    public static void main (String [] args) throws IOException
    {
        int totalNumOfDoc=5000;
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter the query you want to search about : ");
        String query= sc.nextLine();
        sc.close();
        Set<String> arrayWords = new HashSet<>();
        Set<String> arrayLink = new  HashSet<>();
        QueryProcessor.QueryProcessorFunc(query,arrayWords,arrayLink);
        String[] q = arrayWords.toArray(String[]::new);
        double [] title_score=new double [arrayLink.size()];
        double [] h1_score=new double [arrayLink.size()];
        double [] h2_score=new double [arrayLink.size()];
        double [] description_score=new double [arrayLink.size()];
        double [] h3_score=new double [arrayLink.size()];
        double [] h4_score=new double [arrayLink.size()];
        double [] h5_score=new double [arrayLink.size()];
        double [] h6_score=new double [arrayLink.size()];
        double [] body_score=new double [arrayLink.size()];
        String[] titleArray=new String [1];
        String[] descriptionArray=new String [1];
        String[] bodyArray=new String [1];
        String[] h1=new String [1];
        String[] h2=new String [1];
        String[] h3=new String [1];
        String[] h4=new String [1];
        String[] h5=new String [1];
        String[] h6=new String [1];
        double [] TF=new double [q.length];
        double [] TF_title=new double [q.length];
        double [] TF_description=new double [q.length];
        double [] TF_body=new double [q.length];
        double [] TF_h1=new double [q.length];
        double [] TF_h2=new double [q.length];
        double [] TF_h3=new double [q.length];
        double [] TF_h4=new double [q.length];
        double [] TF_h5=new double [q.length];
        double [] TF_h6=new double [q.length];
        double [] IDF=new double [q.length];
        int [] numOfDocContainingTerm=new int [q.length];
        double [] TF_IDF_page=new double [arrayLink.size()];
        String [] LINKS=new String[arrayLink.size()];
        String [] arrayLinkasArray= arrayLink.toArray(String[]::new);
        for (int k=0;k<arrayLink.size();k++)
        {
            String link = arrayLinkasArray[k];
            Document currentPage = extract.downloadFile(link);
            String currnetPageContent = currentPage.text();
            currnetPageContent = currnetPageContent.toLowerCase();
            currnetPageContent = splitPage.readFile(currnetPageContent);
            String[] Text=currnetPageContent.split(" ");
            stemmer stem = new stemmer();
            stem.stemThis(Text);
            splitPage.split(link,titleArray,descriptionArray,bodyArray);
            titleArray=titleArray[0].split(" ");
            descriptionArray=descriptionArray[0].split(" ");
            bodyArray=bodyArray[0].split(" ");
            splitheaders(link,h1,1);
            splitheaders(link,h2,2);
            splitheaders(link,h3,3);
            splitheaders(link,h4,4);
            splitheaders(link,h5,5);
            splitheaders(link,h6,6);
            h1=h1[0].split(" ");
            h2=h2[0].split(" ");
            h3=h3[0].split(" ");
            h4=h4[0].split(" ");
            h5=h5[0].split(" ");
            h6=h6[0].split(" ");
            int docLength=Text.length;
            int [] numOccurrenceOfWord=new int [q.length];
            int [] numOccurrenceOfWord_title=new int [q.length];
            int [] numOccurrenceOfWord_description=new int [q.length];
            int [] numOccurrenceOfWord_body=new int [q.length];
            int [] numOccurrenceOfWord_h1=new int [q.length];
            int [] numOccurrenceOfWord_h2=new int [q.length];
            int [] numOccurrenceOfWord_h3=new int [q.length];
            int [] numOccurrenceOfWord_h4=new int [q.length];
            int [] numOccurrenceOfWord_h5=new int [q.length];
            int [] numOccurrenceOfWord_h6=new int [q.length];
            for(int i=0;i<q.length;i++)
            {
                for(int j=0; j<Text.length;j++)
                {
                    if(q[i].equals(Text[j]))
                        numOccurrenceOfWord[i]++;
                }
                for(int j=0; j<titleArray.length;j++)
                {
                    if(q[i].equals(titleArray[j]))
                        numOccurrenceOfWord_title[i]++;
                }
                for(int j=0; j<descriptionArray.length;j++)
                {
                    if(q[i].equals(descriptionArray[j]))
                        numOccurrenceOfWord_description[i]++;
                }
                for(int j=0; j<bodyArray.length;j++)
                {
                    if(q[i].equals(bodyArray[j]))
                        numOccurrenceOfWord_body[i]++;
                }
                for(int j=0; j<h1.length;j++)
                {
                    if(q[i].equals(h1[j]))
                        numOccurrenceOfWord_h1[i]++;
                }
                for(int j=0; j<h2.length;j++)
                {
                    if(q[i].equals(h2[j]))
                        numOccurrenceOfWord_h2[i]++;
                }
                for(int j=0; j<h3.length;j++)
                {
                    if(q[i].equals(h3[j]))
                        numOccurrenceOfWord_h3[i]++;
                }
                for(int j=0; j<h4.length;j++)
                {
                    if(q[i].equals(h4[j]))
                        numOccurrenceOfWord_h4[i]++;
                }
                for(int j=0; j<h5.length;j++)
                {
                    if(q[i].equals(h5[j]))
                        numOccurrenceOfWord_h5[i]++;
                }
                for(int j=0; j<h6.length;j++)
                {
                    if(q[i].equals(h6[j]))
                        numOccurrenceOfWord_h6[i]++;
                }
            }

            //[nabil] : we should store the words of document in the data base

            for(int i=0;i<q.length;i++)
            {
                TF[i]=numOccurrenceOfWord[i]/(float)docLength;
                TF_title[i]=numOccurrenceOfWord_title[i]/(float)titleArray.length;
                TF_description[i]=numOccurrenceOfWord_description[i]/(float)descriptionArray.length;
                TF_body[i]=numOccurrenceOfWord_body[i]/(float)bodyArray.length;
                TF_h1[i]=numOccurrenceOfWord_h1[i]/(float)h1.length;
                TF_h2[i]=numOccurrenceOfWord_h2[i]/(float)h2.length;
                TF_h3[i]=numOccurrenceOfWord_h3[i]/(float)h3.length;
                TF_h4[i]=numOccurrenceOfWord_h4[i]/(float)h4.length;
                TF_h5[i]=numOccurrenceOfWord_h5[i]/(float)h5.length;
                TF_h6[i]=numOccurrenceOfWord_h6[i]/(float)h6.length;
            }
            for(int i=0;i<q.length;i++)
            {
                if(currnetPageContent.contains(q[i]))
                    numOfDocContainingTerm[i]++;
            }
            IDF=calculateIDF(numOfDocContainingTerm,totalNumOfDoc,q);

            for(int i=0;i<q.length;i++)
            {
                TF_IDF_page[k]+=TF[i]*IDF[i];
                title_score[k]+=TF_title[i]*0.8;
                h1_score[k]+=TF_h1[i]*0.7;
                h2_score[k]+=TF_h2[i]*0.6;
                description_score[k]+=TF_description[i]*0.6;
                h3_score[k]+=TF_h3[i]*0.5;
                h4_score[k]+=TF_h4[i]*0.4;
                h5_score[k]+=TF_h5[i]*0.3;
                h6_score[k]+=TF_h6[i]*0.2;
                body_score[k]+=TF_body[i]*0.1;
            }
        }
        ArrayList<pair> arr=new ArrayList<>();
        for(int i = 0;i<arrayLink.size();i++)
        {
            pair temp=new pair(arrayLinkasArray[i],(TF_IDF_page[i]+title_score[i]+h1_score[i]+h2_score[i]+description_score[i]+h3_score[i]+h4_score[i]+h5_score[i]+h6_score[i]+body_score[i]));
            arr.add(temp);
        }
        GFG.quickSort(arr,0,arr.size()-1);
        GFG.printArr(arr);
    }
}