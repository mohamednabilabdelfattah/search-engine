public class entry {
    public String word;
    public String link;
    public Double TfTitle;
    public Double TfBody;
    public Double TfDescription;
    public Double TFh1;
    public Double TFh2;
    public Double TFh3;
    public Double TFh4;
    public Double TFh5;
    public Double TFh6;

    public entry(String l, String w, Double tftitle, Double tfbody, Double tfdescription,Double h1,Double h2
            ,Double h3,Double h4,Double h5,Double h6) {
        this.link = l;
        this.TfTitle = tftitle;
        this.TfDescription = tfdescription;
        this.TfBody = tfbody;
        this.word = w;
        this.TFh1=h1;
        this.TFh2=h2;
        this.TFh3=h3;
        this.TFh4=h4;
        this.TFh5=h5;
        this.TFh6=h6;
    }
}
