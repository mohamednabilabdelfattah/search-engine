
import org.tartarus.snowball.ext.PorterStemmer;
public class stemmer {


    public void stemThis(String[] toBeStemmed) {
        PorterStemmer stemmer = new PorterStemmer();
        for (int i = 0; i < toBeStemmed.length; i++) {
            stemmer.setCurrent(toBeStemmed[i]);
            stemmer.stem();
            toBeStemmed[i]= stemmer.getCurrent();
        }
    }

}

