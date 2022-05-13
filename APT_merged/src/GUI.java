// paragreph   ==> to do


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Set;

public class GUI extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            String start ="<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "    <head>\n" +
                    "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "        <link rel=\"icon\" type=\"image/x-icon\" href=\"favicon.ico\">\n" +
                    "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
                    "        <title>AKML Search</title>\n" +
                    "        <style>\n" +
                    "            b{\n" +
                    "            color: white;    \n"+
                  //  "                font-size: 24px;\n" +
                  //  "                font-weight: 100;\n" +
                    "           }\n"+
                    "            #textQuery{\n" +
                    "                width: 600px;\n" +
                    "                height: 35px;\n" +
                    "                border: 1px solid black;\n" +
                    "                border-radius: 20px;\n" +
                    "            }\n" +
                    "\n" +
                    "            #textQuery:hover{\n" +
                    "                border: 2px;\n" +
                    "            }\n" +
                    "            \n" +
                    "            #textQuery:focus{\n" +
                    "                outline: none;\n" +
                    "            }\n" +
                    "\n" +
                    "            #search {\n" +
                    "                background-color:rgb(130, 17, 182);\n" +
                    "                height: 30px;\n" +
                    "            }\n" +
                    "\n" +
                    "            #search:hover {\n" +
                    "                border: 1px groove;\n" +
                    "                background-color:rgb(64, 6, 91);\n" +
                    "            }\n" +
                    "\n" +
                    "            html{\n" +
                    "                scroll-behavior: smooth;\n" +
                    "                height: auto;\n" +
                    "                min-height: 100vh;\n" +
                    "                background-image: linear-gradient(to bottom, rgb(141, 13, 239), rgb(47, 53, 53));\n" +
                    "                background-attachment: fixed;\n" +
                    "            }\n" +
                    "            body{\n" +
                    "                background: none;\n" +
                    "            }\n" +
                    "            #akml{\n" +
                    "                height: 100px;\n" +
                    "            }\n" +
                    "            .akmlParent {\n" +
                    "                position: relative;\n" +
                    "                top: 5px;\n" +
                    "                height: auto;\n" +
                    "                display: flex;\n" +
                    "                align-items: center;\n" +
                    "                justify-content: center;\n" +
                    "            }\n" +
                    "            .akmlChild {\n" +
                    "                width: auto;\n" +
                    "                height: auto;\n" +
                    "                \n" +
                    "            }\n" +
                    "            .queryParent {\n" +
                    "                position: relative;\n" +
                    "                bottom: 10px;\n" +
                    "                right: 380px;\n" +
                    "                height: auto;\n" +
                    "                display: flex;\n" +
                    "                align-items: center;\n" +
                    "                justify-content: center;\n" +
                    "            }\n" +
                    "            .queryChild {\n" +
                    "                width: auto;\n" +
                    "                height: auto;\n" +
                    "            }\n" +
                    "            .searchParent {\n" +
                    "                position: relative;\n" +
                    "                top: 0px;\n" +
                    "                left: 10px;\n" +
                    "                height: auto;\n" +
                    "                display: flex;\n" +
                    "                align-items: center;\n" +
                    "                justify-content: center;\n" +
                    "                border-radius: 50px;\n" +
                    "            }\n" +
                    "            .searchChild {\n" +
                    "                width: auto;\n" +
                    "                height: auto;\n" +
                    "            }\n" +
                    "            .allParent {\n" +
                    "                position: absolute;\n" +
                    "                left: 25%;\n" +
                    "                height: auto;\n" +
                    "                display: flex;\n" +
                    "                align-items: center;\n" +
                    "                justify-content: center;\n" +
                    "            }\n" +
                    "            .allChild {\n" +
                    "                width: auto;\n" +
                    "                height: auto;\n" +
                    "            }\n" +
                    "            hr { \n" +
                    "                position: absolute;\n" +
                    "                display: block;\n" +
                    "                margin-top: 0.5em;\n" +
                    "                margin-bottom: 0.5em;\n" +
                    "                margin-left: auto;\n" +
                    "                margin-right: auto;\n" +
                    "                background-color: rgb(67, 5, 115);\n" +
                    "                color: rgb(67, 5, 115);\n" +
                    "                border-color: rgb(67, 5, 115);\n" +
                    "                bottom: 88%;\n" +
                    "                width: 100%;\n" +
                    "            } \n" +
                    "            .links{\n" +
                    "                position: absolute;\n" +
                    "                top: 15%;\n" +
                    "                width: 100%;\n" +
                    "            }\n" +
                    "            .search-result h3 {\n" +
                    "                margin-bottom: 0;\n" +
                    "                color: #060239;\n" +
                    "            }\n" +
                    "            .search-result .search-link {\n" +
                    "                color: #edf1f2;\n" +
                    "            }\n" +
                    "\n" +
                    "            .search-result p {\n" +
                    "                font-size: 12px;\n" +
                    "                margin-top: 5px;\n" +
                    "            }\n" +
                    "\n" +
                    "            .hr-line-dashed {\n" +
                    "                border-top: 1px dashed rgb(67, 5, 115);\n" +
                    "                height: 1px;\n" +
                    "                margin: 20px 0;\n" +
                    "            }\n" +
                    "\n" +
                    "            h2 {\n" +
                    "                font-size: 24px;\n" +
                    "                font-weight: 100;\n" +
                    "            }\n" +
                    "            .ibox-content {\n" +
                    "                position: absolute;\n" +
                    "                color: inherit;\n" +
                    "                top: 50px;\n" +
                    "                padding: 20px 40px 40px 15%;\n" +
                    "            }\n" +
                    "            a { color:#1f5bd2 !important; }\n" +
                    "            a:hover, a:visited { color:#dc17ea !important; }\n" +
                    "            p{\n" +
                    "                color: white;\n" +
                    "            }\n" +
                    "@keyframes show{\n" +
                    "                0%{\n" +
                    "                    opacity:0;\n" +
                    "                    transform: scale(0.9);\n" +
                    "                }\n" +
                    "                100%{\n" +
                    "                    opacity:1;\n" +
                    "                    transform: scale(1);\n" +
                    "                }\n" +
                    "            }\n" +
                    "            .pagination{\n" +
                    "                width: 100%;\n" +
                    "                float: left;\n" +
                    "                padding:15px;\n" +
                    "                text-align: center;\n" +
                    "            }\n" +
                    "            .pagination div{\n" +
                    "                display: inline-block;\n" +
                    "                margin:0 10px;\n" +
                    "            }\n" +
                    "            .pagination .page{\n" +
                    "                color:gray;\n" +
                    "            }\n" +
                    "            .pagination .prev,.pagination .next{\n" +
                    "            color:rgb(250, 247, 247);\n" +
                    "            font-size:15px;\n" +
                    "            padding:7px 15px;\n" +
                    "            cursor: pointer;\n" +
                    "            }\n" +
                    "            .pagination .prev:hover,.pagination .next:hover{\n" +
                    "            background-color: rgb(141, 13, 239);\n" +
                    "            }\n" +
                    "\n" +
                    "            .pagination .prev.disabled,\n" +
                    "            .pagination .next.disabled{\n" +
                    "                color:gray;\n" +
                    "                pointer-events: none;\n" +
                    "            }"+
                    "\n" +
                    "        </style>\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "        <div>\n" +
                    "            <form action=\"AKMLRequest\" method=\"post\" id = \"AKMLRequest\">\n" +
                    "                <div class=\"allParent\">\n" +
                    "                    <div class=\"allChild\">\n" +
                    "                        <div class=\"queryParent\">\n" +
                    "                            <div class=\"akmlParent\">\n" +
                    "                                <div class=\"akmlChild\">\n" +
                    "                                    <img src=\"AKML.png\" alt=\"AKML\" id=\"akml\" title=\"AKML\">\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"queryChild\">\n" +
                    "                                <input title=\"Search\" id=\"textQuery\" type=\"text\" class=\"form-control\" name=\"Query\">\n" +
                    "                            </div>\n" +
                    "                        \n" +
                    "                            <div class=\"searchParent\">\n" +
                    "                                <div class=\"searchChild\">\n" +
                    "                                    <input title=\"Search\"  type=\"submit\" value=\"Search\" class=\"btn btn-primary\" id=\"search\" name=\"btnK\">        \n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </form> \n" +
                    "        </div>\n" +
                    "        <hr>\n" +
                    "        <div style=\"position:absolute;top:5%;\">\n" +
                    "            <div class=\"container bootstrap snippets bootdey\">\n" +
                    "                <div class=\"row\">\n" +
                    "                    <div class=\"col-lg-12\">\n" +
                    "                        <div class=\"ibox float-e-margins\">\n" +
                    "                            <div class=\"ibox-content\">"+
                    "                               <div class=\"Pagination-Results\">";

            String end = "</div>\n" +
                    "                                    <div class=\"pagination\">\n" +
                    "                                    <div class=\"prev\">Prev</div>\n" +
                    "                                    <div class=\"page\">Page <span class=\"page-num\"></span></div>\n" +
                    "                                    <div class=\"next\">Next</div>\n" +
                    "                                </div>"+
                    "                           </div>\n"+
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "<script src=\"script.js\"></script>\n"+
                    "    </body>\n" +
                    "</html>";

            String result = request.getParameter("Query");
            Set<String> set = phraseSearching.phraseSearchingFunc(result);
            //String result = "Done";
            String resultLink = "";
            splitPage splitObject=new splitPage();
            String resultStop = splitObject.readFile(result.toLowerCase());
            String [] words = resultStop.split(" ");
            //System.out.println(words);

            if(set.isEmpty())
            {
                resultLink += "<h3>Your search - "+ result +" - did not match any documents.</h3>";
            }
//          Integer i = 0;
            String title = "";
            String boldText = "";

            for (String link:set) {

                // Download Doc
                Document doc = extract.downloadFile(link);
                // Search for the word to make it bold
                String body = doc.body().text();
                boldText = body.substring(100,700);
                String [] printedResultsArr = {};
                for (int i = 0; i < words.length; i++) {
                    //boldText.replaceAll(words[i].toLowerCase(), "</p><b>"+words[i].toLowerCase()+"</b><p>");
                    printedResultsArr = boldText.split(" ");
                    for (int j = 0; j < printedResultsArr.length; j++) {
                        if (printedResultsArr[j].equalsIgnoreCase(words[i])) {
                            printedResultsArr[j] = "<b>" + printedResultsArr[j] + "</b>";
                        }
                    }
                }
                boldText = "... ";
                for (int i = 0; i < printedResultsArr.length; i++) {
                    boldText += printedResultsArr[i] + " ";
                }
                boldText += " ...";

                // get title
                title = doc.title();

                resultLink += "<div class=\"item\">\n";
                resultLink += "<div class=\"hr-line-dashed\"></div>\n" +
                        "                                <div class=\"search-result\">";

                resultLink += "<h3><a href=\""+link+"\">"+ title +"</a></h3>\n" +
                        "                                    <a href=\""+link+"\" class=\"search-link\">"+link+"</a>\n"+
                        "                                    <p>\n" +
                        "                                        "+ boldText +"\n" +
                        "                                    </p>";


                resultLink += "</div>\n";
                resultLink += "</div>\n";
//                resultLink += "<h1>"+i.toString()+"</h1>";
//                resultLink += "<a href =\""+link+"\">"+link+"</a>";
//                i++;


            }

            response.setContentType("text/html");

//            String page = "<!doctype html> <html> <body bgcolor=\"#f0f0f0\" align=\"center\"> "
//                    +
//                    result
//                    +
//                    "<h1>Resut</h1>"
//                    +
//                    resultLink
//                    + "</body></html>";
            String page = start
                    +
                    resultLink
                    + end;
            response.getWriter().println(page);


        }
        catch (Exception e)
        {
            response.setContentType("text/html");
            String page = "<!doctype html> <html> <body bgcolor=\"#f0f0f0\" align=\"center\"> <h1>Error</h1>" +e.toString() +"</body></html>";
            response.getWriter().println(page);
        }
    }
}
