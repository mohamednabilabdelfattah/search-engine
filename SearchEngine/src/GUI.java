import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jsoup.nodes.Document;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class GUI extends HttpServlet {
    DatabaseConnection dbManager = new DatabaseConnection();
    Connection dbConnection = dbManager.connect();
    String akmlStart = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\" name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <link rel=\"icon\" type=\"image/x-icon\" href=\"favicon.ico\">\n" +
            "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
            "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
            "    <title>AKML Search</title>\n" +
            "    <style>\n" +
            "        #textQuery{\n" +
            "            width: 600px;\n" +
            "            height: 35px;\n" +
            "            border: 1px solid black;\n" +
            "            border-radius: 20px;\n" +
            "        }\n" +
            "\n" +
            "        #textQuery:hover{\n" +
            "            border: 2px;\n" +
            "        }\n" +
            "        \n" +
            "        #textQuery:focus{\n" +
            "            outline: none;\n" +
            "        }\n" +
            "\n" +
            "        #search {\n" +
            "            background-color:rgb(130, 17, 182);\n" +
            "        }\n" +
            "\n" +
            "        #search:hover {\n" +
            "            border: 1px groove;\n" +
            "            background-color:rgb(64, 6, 91);\n" +
            "        }\n" +
            "\n" +
            "        html{\n" +
            "            scroll-behavior: smooth;\n" +
            "            height: auto;\n" +
            "            min-height: 100vh;\n" +
            "            background-image: linear-gradient(to bottom, rgb(141, 13, 239), rgb(47, 53, 53));\n" +
            "        }\n" +
            "        body{\n" +
            "            background: none;\n" +
            "        }\n" +
            "        #akml{\n" +
            "            height: 175px;\n" +
            "        }\n" +
            "        .akmlParent {\n" +
            "            position: relative;\n" +
            "            top: 150px;\n" +
            "            height: auto;\n" +
            "            display: flex;\n" +
            "            align-items: center;\n" +
            "            justify-content: center;\n" +
            "        }\n" +
            "        .akmlChild {\n" +
            "            width: auto;\n" +
            "            height: auto;\n" +
            "            \n" +
            "        }\n" +
            "        .queryParent {\n" +
            "            position: relative;\n" +
            "            top: 130px;\n" +
            "            height: auto;\n" +
            "            display: flex;\n" +
            "            align-items: center;\n" +
            "            justify-content: center;\n" +
            "        }\n" +
            "        .queryChild {\n" +
            "            width: auto;\n" +
            "            height: auto;\n" +
            "        }\n" +
            "        .searchParent {\n" +
            "            position: relative;\n" +
            "            top: 140px;\n" +
            "            height: auto;\n" +
            "            display: flex;\n" +
            "            align-items: center;\n" +
            "            justify-content: center;\n" +
            "        }\n" +
            "        .searchChild {\n" +
            "            width: auto;\n" +
            "            height: auto;\n" +
            "        }\n" +
            "        .search-input.active .autocom-box{\n" +
            "            background-color: white;\n" +
            "            padding: 10px 8px;\n" +
            "            pointer-events: auto;\n" +
            "            z-index: 999;\n" +
            "            position: absolute;\n" +
            "            left: 31.5%;\n" +
            "            width: 570px;\n" +
            "            display:inline-flexbox;\n" +
            "            top: 35px;\n" +
            "        }\n" +
            "\n" +
            "        .autocom-box li{\n" +
            "            list-style: none;\n" +
            "            padding: 8px 12px;\n" +
            "            display: none;\n" +
            "            width: 100%;\n" +
            "            cursor: default;\n" +
            "            border-radius: 3px;\n" +
            "        }\n" +
            "\n" +
            "        .search-input.active .autocom-box li{\n" +
            "            display: block;\n" +
            "        }\n" +
            "        .autocom-box li:hover{\n" +
            "            background: #efefef;\n" +
            "        }\n" +
            "        .left-pan{\n" +
            "            position: absolute;\n" +
            "            top: 24%;\n" +
            "            left: 68%;\n" +
            "        }\n" +
            "        .vl {\n" +
            "            position: absolute;\n" +
            "            border-left: 1px solid rgb(112, 115, 116);\n" +
            "            height: 25px;\n" +
            "            top: 16%;\n" +
            "            left: 67.5%;\n" +
            "        }\n" +
            "        a { color:#99b4f4 !important; }\n" +
            "        a:hover, a:visited { color:#dc17ea !important; }\n" +
            "\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <!-- <a id=\"GoogleImages\" title=\"Google Images\" href=\"Google image.html\">Google Images</a> -->\n" +
            "    <!-- <a id=\"GoogleAdv\" title=\"Google Advanced Search\" href=\"Google Advanced Search.html\">Google Advanced Search</a> -->\n" +
            "    <form action=\"AKMLRequest\" method=\"post\" id = \"AKMLRequest\">\n" +
            "        <div class=\"akmlParent\">\n" +
            "            <div class=\"akmlChild\">\n" +
            "                <img src=\"AKML.png\" alt=\"AKML\" id=\"akml\" title=\"AKML\">\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div class=\"wrapper\">\n" +
            "            <div class=\"search-input\">\n" +
            "                <div class=\"queryParent\">\n" +
            "                    <div class=\"queryChild\">\n" +
            "                        <input title=\"Search\" id=\"textQuery\" type=\"text\" class=\"form-control\" name=\"Query\" autocomplete=\"off\" placeholder=\"Search...\">\n" +
            "                        <span class=\"left-pan\"><a id=\"mic\"><i class=\"fa fa-microphone\"></i></a></span>\n" +
            "                        <div class=\"vl\"></div>\n" +
            "                        <div class=\"autocom-box\" id=\"autocom-box\">\n" +
            "                            <!-- here list are inserted from javascript -->\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div class=\"searchParent\">\n" +
            "            <div class=\"searchChild\">\n" +
            "                <input title=\"Search\"  type=\"submit\" value=\"AKML Search\" class=\"btn btn-primary\" id=\"search\" name=\"btnK\">\n" +
            "            </div>\n" +
            "        </div>";

    String akmlEnd = "    </form>\n" +
            "</body>\n" +
            "</html>";
    String start = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "    <head>\n" +
            "        <meta charset=\"utf-8\" name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "        <link rel=\"icon\" type=\"image/x-icon\" href=\"favicon.ico\">\n" +
            "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
            "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
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
            "                top: 5px;\n" +
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
            "            a { color:#99b4f4 !important; }\n" +
            "            a:hover, a:visited { color:#dc17ea !important; }\n" +
            "            p{\n" +
            "                color: white;\n" +
            "            }\n" +
            "            @keyframes show{\n" +
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
            "\n" +
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
            "            }\n" +
            "            .search-input.active .autocom-box{\n" +
            "                z-index: 999;\n" +
            "                position: absolute;\n" +
            "                left: 7.62%;\n" +
            "                width: 570px;\n" +
            "                background-color: white;\n" +
            "                display:inline-flexbox;\n" +
            "                top: 8.7%;\n" +
            "                padding: 10px 8px;\n" +
            "                pointer-events: auto;\n" +
            "            }\n" +
            "\n" +
            "            .autocom-box li{\n" +
            "                padding: 8px 12px;\n" +
            "                display: block;\n" +
            "                width: 100%;\n" +
            "                cursor: default;\n" +
            "                border-radius: 3px;\n" +
            "            }\n" +
            "\n" +
            "            .search-input.active .autocom-box li{\n" +
            "                display: block;\n" +
            "            }\n" +
            "            .autocom-box li:hover{\n" +
            "                background: #efefef;\n" +
            "            }\n" +
            "            .left-pan{\n" +
            "            position: absolute;\n" +
            "            top: 40%;\n" +
            "            left: 97%;\n" +
            "            }\n" +
            "            .vl {\n" +
            "                position: absolute;\n" +
            "                border-left: 1px solid rgb(112, 115, 116);\n" +
            "                height: 25px;\n" +
            "                top: 37%;\n" +
            "                left: 96%;\n" +
            "            }\n" +
            "\n" +
            "        </style>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <div class=\"wrapper\">\n" +
            "            <div class=\"search-input\">\n" +
            "                <form action=\"AKMLRequest\" method=\"post\" id = \"AKMLRequest\">\n" +
            "                    <div class=\"allParent\">\n" +
            "                        <div class=\"allChild\">\n" +
            "                            <div class=\"queryParent\">\n" +
            "                                <div class=\"akmlParent\">\n" +
            "                                    <div class=\"akmlChild\">\n" +
            "                                        <img src=\"AKML.png\" alt=\"AKML\" id=\"akml\" title=\"AKML\">\n" +
            "                                    </div>\n" +
            "                                        <div class=\"queryChild\">\n" +
            "                                            <input title=\"Search\" id=\"textQuery\" type=\"text\" class=\"form-control\" name=\"Query\" autocomplete=\"off\" placeholder=\"Search...\">\n" +
            "                                            <span class=\"left-pan\"><a id=\"mic\"><i class=\"fa fa-microphone\"></i></a></span>\n" +
            "                                            <div class=\"vl\"></div>\n" +
            "                                        </div>\n" +
            "                                </div>\n" +
            "                            \n" +
            "                                <div class=\"searchParent\">\n" +
            "                                    <div class=\"searchChild\">\n" +
            "                                        <input title=\"Search\"  type=\"submit\" value=\"Search\" class=\"btn btn-primary\" id=\"search\" name=\"btnK\">        \n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </form> \n" +
            "                \n" +
            "                <div class=\"autocom-box\" id=\"autocom-box\">\n" +
            "                    <!-- here list are inserted from javascript -->\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <hr>\n" +
            "        <div style=\"position:absolute;top:5%;\">\n" +
            "            <div class=\"container bootstrap snippets bootdey\">\n" +
            "                <div class=\"row\">\n" +
            "                    <div class=\"col-lg-12\">\n" +
            "                        <div class=\"ibox float-e-margins\">\n" +
            "                            <div class=\"ibox-content\">\n" +
            "                                <div class=\"Pagination-Results\">";

    String end1 = "</div>\n" +
            "                                <div class=\"pagination\">\n" +
            "                                    <div class=\"prev\">Prev</div>\n" +
            "                                    <div class=\"page\">Page <span class=\"page-num\"></span></div>\n" +
            "                                    <div class=\"next\">Next</div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n";

    String end2 = "    </body>\n" +
            "</html>";

    String result2 = "";

    public GUI() throws SQLException, ClassNotFoundException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            String result = request.getParameter("Query");

            if (result.isEmpty()){
                throw new Exception("Empty Search, Please Type anything.......");
            }

            // suggestions
            try {
                DBManager.insertintoQueries(dbConnection, result);
            }
            catch (Exception e){
            }

            /////////////////////////////////////////////////////////////
//            Set<String> set = phraseSearching.phraseSearchingFunc(result);
            ArrayList<String> set = mainEngine.SearchEngine(result);
            //String result = "Done";
            String resultLink = "";
            splitPage splitObject=new splitPage();
            String resultStop = splitObject.readFile(result.toLowerCase());
            String [] words = resultStop.split(" ");
            //System.out.println(words);

            boolean onetimeError = false;

            if(set.isEmpty())
            {
                resultLink += "<p>Your search --- "+ result +" --- did not match any documents.</p>";
                onetimeError = true;
            }
            String title = "";
            String boldText = "";
            for (String link : set) {
                // Search for the word to make it bold
                ResultSet bodyAndTitle = DBManager.getOneBodyAndTitleFromCrawledv2(dbConnection, link);
                bodyAndTitle.next();
                String body = bodyAndTitle.getString("body");
                if (body.length() < 700)
                {
                    continue;
                }
                String[] printedResultsArr = body.substring(200, 700).split(" ");
                for (int i = 0; i < words.length; i++) {
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
                title = bodyAndTitle.getString("title");

                resultLink += "<div class=\"item\">\n";
                resultLink += "<div class=\"hr-line-dashed\"></div>\n" +
                        "                                <div class=\"search-result\">";

                resultLink += "<h3><a href=\"" + link + "\">" + title + "</a></h3>\n" +
                        "                                    <a href=\"" + link + "\" class=\"search-link\">" + link + "</a>\n" +
                        "                                    <p>\n" +
                        "                                        " + boldText + "\n" +
                        "                                    </p>";


                resultLink += "</div>\n";
                resultLink += "</div>\n";
            }
            if (boldText.isEmpty() && !onetimeError){
                resultLink += "<p>Your search --- "+ result +" --- did not match any documents.</p>";
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


            result2 = "";
            result2 += "<script>\n";
            result2 += "let suggestions = [";
            try {
                ResultSet resultSet = DBManager.getFromQueries(dbConnection);
                while (resultSet.next()) {
                    result2 +=  "\'" + resultSet.getString("query") + "\',";
                }
                result2 += "];";
                result2 += "\n</script>";
                result2 += "<script src=\"script.js\"></script>";
                result2 += "<script src=\"autoComplete.js\"></script>";
                result2 += "<script src=\"micSearch.js\"></script>";

            } catch (SQLException e) {
            }
            String page = start
                    +
                    resultLink
                    +
                    end1
                    +
                    result2
                    + end2;
            response.getWriter().println(page);


        }
        catch (Exception e)
        {
            response.setContentType("text/html");
            String page = start +"<p>"+ e.toString().substring(21)+"</p>" +end1 + result2 + end2;
            response.getWriter().println(page);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = "";
        result += "<script>\n";
        result += "let suggestions = [";
        try {
            ResultSet resultSet = DBManager.getFromQueries(dbConnection);
            while (resultSet.next()) {
                result +=  "'" + resultSet.getString("query") + "',";
            }
            System.out.println(result);
            result += "]; console.log(suggestions);";
            result += "\n</script>";
            result += "<script src=\"autoComplete.js\"></script>";
            result += "<script src=\"micSearch.js\"></script>";
            response.setContentType("text/html");//
            String page = akmlStart + result +akmlEnd;
            response.getWriter().println(page);
        } catch (SQLException e) {
        }
    }
}


