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
                    "                            <div class=\"ibox-content\">";

            String end = "</div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        \n" +
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
//            Integer i = 0;
            String title = "";
            for (String link:set) {

                // Download Doc
                Document doc = extract.downloadFile(link);
                // Search for the word to make it bold
                String body = doc.body().text();

                String senPre = "";
                String senCurr = "";
                String senNext = "";
                //String firstThree = "";

                BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
                iterator.setText(body);
                int startBody = iterator.first();
                boolean foundWord = false;
                for (int endBody = iterator.next();
                     endBody != BreakIterator.DONE;
                     startBody = endBody, endBody = iterator.next()) {
                    //System.out.println(body.substring(startBody,endBody));
//                    if (senPre.isEmpty())
//                    {
//                        firstThree += senNext;
//                    }
                    senPre = senCurr;
                    senCurr = senNext;
                    senNext = body.substring(startBody,endBody);
                    if (!senCurr.isEmpty()) {
                        for (int i = 0; i < words.length; i++) {

                            if (senCurr.contains(words[i])){
                                foundWord = true;
                                break;
                            }
                        }
                        if (foundWord == true)
                        {
                            break;
                        }
                    }

                }
                String boldText = "";
//                senCurr += senNext;
                if (foundWord == true)
                {
                    boldText = senPre + senCurr + senNext;
//                    boldText += senPre;

//                    boldText += senCurr;
                }
                else
                {
                    boldText = doc.select("meta[name=description]").get(0).attr("content").toLowerCase();
                }
                boldText = boldText.toLowerCase();
                for (int i = 0; i < words.length; i++) {
                    boldText.replaceAll(words[i].toLowerCase(), "<strong>"+words[i].toLowerCase()+"</strong>");
                }

                // get title
                title = doc.title();

                resultLink += "<div class=\"hr-line-dashed\"></div>\n" +
                        "                                <div class=\"search-result\">";

                resultLink += "<h3><a href=\""+link+"\">"+ title +"</a></h3>\n" +
                        "                                    <a href=\""+link+"\" class=\"search-link\">"+link+"</a>\n" +
                        "                                    <p>\n" +
                        "                                        "+ boldText +"\n" +
                        "                                    </p>";

                resultLink += "</div>";
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
