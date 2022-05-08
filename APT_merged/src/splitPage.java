import java.io.*;
import java.util.Scanner;

import jakarta.servlet.ServletContext;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class splitPage {
    @SuppressWarnings("resource")
    //[ToDO] : add the headers
    public String readFile(String object) throws IOException
    {
        object=" "+object+" ";
        object=object.replaceAll("[^a-zA-Z0-9]", " ");
        @SuppressWarnings("resource")
        //BufferedReader reader=new BufferedReader(new FileReader("\"E:\\Programs\\Tomcat\\apache-tomcat-10.0.20\\webapps\\stopwords.txt\""));
        //BufferedReader reader=new BufferedReader(new FileReader("http://localhost:8080/stopwords.txt"));


        //BufferedReader infile = new BufferedReader(new FileReader(config.getServletContext().getRealPath("path_to_your_file")));
        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //InputStream input = classLoader.getResourceAsStream("foo.properties");

        //BufferedReader reader=new BufferedReader(new FileReader(String.valueOf(this.getClass().getClassLoader().getResourceAsStream("/stopwords.txt"))));


        String stopwords = "a\n" +
                "about\n" +
                "above\n" +
                "across\n" +
                "after\n" +
                "again\n" +
                "against\n" +
                "all\n" +
                "almost\n" +
                "alone\n" +
                "along\n" +
                "already\n" +
                "also\n" +
                "although\n" +
                "always\n" +
                "among\n" +
                "an\n" +
                "and\n" +
                "another\n" +
                "any\n" +
                "anybody\n" +
                "anyone\n" +
                "anything\n" +
                "anywhere\n" +
                "are\n" +
                "area\n" +
                "areas\n" +
                "around\n" +
                "as\n" +
                "ask\n" +
                "asked\n" +
                "asking\n" +
                "asks\n" +
                "at\n" +
                "away\n" +
                "b\n" +
                "back\n" +
                "backed\n" +
                "backing\n" +
                "backs\n" +
                "be\n" +
                "became\n" +
                "because\n" +
                "become\n" +
                "becomes\n" +
                "been\n" +
                "before\n" +
                "began\n" +
                "behind\n" +
                "being\n" +
                "beings\n" +
                "best\n" +
                "better\n" +
                "between\n" +
                "big\n" +
                "both\n" +
                "but\n" +
                "by\n" +
                "c\n" +
                "came\n" +
                "can\n" +
                "cannot\n" +
                "case\n" +
                "cases\n" +
                "certain\n" +
                "certainly\n" +
                "clear\n" +
                "clearly\n" +
                "come\n" +
                "could\n" +
                "d\n" +
                "did\n" +
                "differ\n" +
                "different\n" +
                "differently\n" +
                "do\n" +
                "does\n" +
                "done\n" +
                "down\n" +
                "down\n" +
                "downed\n" +
                "downing\n" +
                "downs\n" +
                "during\n" +
                "e\n" +
                "each\n" +
                "early\n" +
                "either\n" +
                "end\n" +
                "ended\n" +
                "ending\n" +
                "ends\n" +
                "enough\n" +
                "even\n" +
                "evenly\n" +
                "ever\n" +
                "every\n" +
                "everybody\n" +
                "everyone\n" +
                "everything\n" +
                "everywhere\n" +
                "f\n" +
                "face\n" +
                "faces\n" +
                "fact\n" +
                "facts\n" +
                "far\n" +
                "felt\n" +
                "few\n" +
                "find\n" +
                "finds\n" +
                "first\n" +
                "for\n" +
                "four\n" +
                "from\n" +
                "full\n" +
                "fully\n" +
                "further\n" +
                "furthered\n" +
                "furthering\n" +
                "furthers\n" +
                "g\n" +
                "gave\n" +
                "general\n" +
                "generally\n" +
                "get\n" +
                "gets\n" +
                "give\n" +
                "given\n" +
                "gives\n" +
                "go\n" +
                "going\n" +
                "good\n" +
                "goods\n" +
                "got\n" +
                "great\n" +
                "greater\n" +
                "greatest\n" +
                "group\n" +
                "grouped\n" +
                "grouping\n" +
                "groups\n" +
                "h\n" +
                "had\n" +
                "has\n" +
                "have\n" +
                "having\n" +
                "he\n" +
                "her\n" +
                "here\n" +
                "herself\n" +
                "high\n" +
                "high\n" +
                "high\n" +
                "higher\n" +
                "highest\n" +
                "him\n" +
                "himself\n" +
                "his\n" +
                "how\n" +
                "however\n" +
                "i\n" +
                "if\n" +
                "important\n" +
                "in\n" +
                "interest\n" +
                "interested\n" +
                "interesting\n" +
                "interests\n" +
                "into\n" +
                "is\n" +
                "it\n" +
                "its\n" +
                "itself\n" +
                "j\n" +
                "just\n" +
                "k\n" +
                "keep\n" +
                "keeps\n" +
                "kind\n" +
                "knew\n" +
                "know\n" +
                "known\n" +
                "knows\n" +
                "l\n" +
                "large\n" +
                "largely\n" +
                "last\n" +
                "later\n" +
                "latest\n" +
                "least\n" +
                "less\n" +
                "let\n" +
                "lets\n" +
                "like\n" +
                "likely\n" +
                "long\n" +
                "longer\n" +
                "longest\n" +
                "m\n" +
                "made\n" +
                "make\n" +
                "making\n" +
                "man\n" +
                "many\n" +
                "may\n" +
                "me\n" +
                "member\n" +
                "members\n" +
                "men\n" +
                "might\n" +
                "more\n" +
                "most\n" +
                "mostly\n" +
                "mr\n" +
                "mrs\n" +
                "much\n" +
                "must\n" +
                "my\n" +
                "myself\n" +
                "n\n" +
                "necessary\n" +
                "need\n" +
                "needed\n" +
                "needing\n" +
                "needs\n" +
                "never\n" +
                "new\n" +
                "new\n" +
                "newer\n" +
                "newest\n" +
                "next\n" +
                "no\n" +
                "nobody\n" +
                "non\n" +
                "noone\n" +
                "not\n" +
                "nothing\n" +
                "now\n" +
                "nowhere\n" +
                "number\n" +
                "numbers\n" +
                "o\n" +
                "of\n" +
                "off\n" +
                "often\n" +
                "old\n" +
                "older\n" +
                "oldest\n" +
                "on\n" +
                "once\n" +
                "one\n" +
                "only\n" +
                "open\n" +
                "opened\n" +
                "opening\n" +
                "opens\n" +
                "or\n" +
                "order\n" +
                "ordered\n" +
                "ordering\n" +
                "orders\n" +
                "other\n" +
                "others\n" +
                "our\n" +
                "out\n" +
                "over\n" +
                "p\n" +
                "part\n" +
                "parted\n" +
                "parting\n" +
                "parts\n" +
                "per\n" +
                "perhaps\n" +
                "place\n" +
                "places\n" +
                "point\n" +
                "pointed\n" +
                "pointing\n" +
                "points\n" +
                "possible\n" +
                "present\n" +
                "presented\n" +
                "presenting\n" +
                "presents\n" +
                "problem\n" +
                "problems\n" +
                "put\n" +
                "puts\n" +
                "q\n" +
                "quite\n" +
                "r\n" +
                "rather\n" +
                "really\n" +
                "right\n" +
                "right\n" +
                "room\n" +
                "rooms\n" +
                "s\n" +
                "said\n" +
                "same\n" +
                "saw\n" +
                "say\n" +
                "says\n" +
                "second\n" +
                "seconds\n" +
                "see\n" +
                "seem\n" +
                "seemed\n" +
                "seeming\n" +
                "seems\n" +
                "sees\n" +
                "several\n" +
                "shall\n" +
                "she\n" +
                "should\n" +
                "show\n" +
                "showed\n" +
                "showing\n" +
                "shows\n" +
                "side\n" +
                "sides\n" +
                "since\n" +
                "small\n" +
                "smaller\n" +
                "smallest\n" +
                "so\n" +
                "some\n" +
                "somebody\n" +
                "someone\n" +
                "something\n" +
                "somewhere\n" +
                "state\n" +
                "states\n" +
                "still\n" +
                "still\n" +
                "such\n" +
                "sure\n" +
                "t\n" +
                "take\n" +
                "taken\n" +
                "than\n" +
                "that\n" +
                "the\n" +
                "their\n" +
                "them\n" +
                "then\n" +
                "there\n" +
                "therefore\n" +
                "these\n" +
                "they\n" +
                "thing\n" +
                "things\n" +
                "think\n" +
                "thinks\n" +
                "this\n" +
                "those\n" +
                "though\n" +
                "thought\n" +
                "thoughts\n" +
                "three\n" +
                "through\n" +
                "thus\n" +
                "to\n" +
                "today\n" +
                "together\n" +
                "too\n" +
                "took\n" +
                "toward\n" +
                "turn\n" +
                "turned\n" +
                "turning\n" +
                "turns\n" +
                "two\n" +
                "u\n" +
                "under\n" +
                "until\n" +
                "up\n" +
                "upon\n" +
                "us\n" +
                "use\n" +
                "used\n" +
                "uses\n" +
                "v\n" +
                "very\n" +
                "w\n" +
                "want\n" +
                "wanted\n" +
                "wanting\n" +
                "wants\n" +
                "was\n" +
                "way\n" +
                "ways\n" +
                "we\n" +
                "well\n" +
                "wells\n" +
                "went\n" +
                "were\n" +
                "what\n" +
                "when\n" +
                "where\n" +
                "whether\n" +
                "which\n" +
                "while\n" +
                "who\n" +
                "whole\n" +
                "whose\n" +
                "why\n" +
                "will\n" +
                "with\n" +
                "within\n" +
                "without\n" +
                "work\n" +
                "worked\n" +
                "working\n" +
                "works\n" +
                "would\n" +
                "x\n" +
                "y\n" +
                "year\n" +
                "years\n" +
                "yet\n" +
                "you\n" +
                "young\n" +
                "younger\n" +
                "youngest\n" +
                "your\n" +
                "yours\n" +
                "z\n";

        Scanner scanner = new Scanner(stopwords);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // process the line
            if(object.contains(line))
            {
                object=object.replaceAll(" "+line+" ", " ");
            }
        }
        scanner.close();

//        String line=reader.readLine();
//        while (line != null)
//        {
//            if(object.contains(line))
//            {
//                object=object.replaceAll(" "+line+" ", " ");
//            }
//            line=reader.readLine();
//        }
        object=object.replaceAll("\\s+", " ");
        object=object.trim();
        return object;
    }
    public void split (String URL,String[] titleArray, String[] descriptionArray,String[] bodyArray/*,String title,String body,String description*/) throws IOException
    {
        Document doc=Jsoup.connect(URL).get();
//		____________split_title______________


        if (doc.title() != null)
        {
            titleArray[0]=doc.title().toLowerCase();
            titleArray[0]=readFile(titleArray[0]);
        }

//		____________split_description____________

        if(doc.select("meta[name=description]").size() != 0)
        {
            descriptionArray[0]=doc.select("meta[name=description]").get(0).attr("content").toLowerCase();
            descriptionArray[0]=readFile(descriptionArray[0]);
        }

//		____________split_body_____________

        if(doc.body().text() != null)
        {
            bodyArray[0]=doc.body().text().toLowerCase();
            bodyArray[0]=readFile(bodyArray[0]);
        }

    }
}
