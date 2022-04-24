public class entry {
    public String word;
    public String link;
    public Double TfHeader;
    public Double TfBody;
    public Double TfDescription;

    public entry(String l, String w, Double tfheader, Double tfbody, Double tfdescription) {
        this.link = l;
        this.TfHeader = tfheader;
        this.TfDescription = tfdescription;
        this.TfBody = tfbody;
        this.word = w;
    }
}
