import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class GUI extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            String result = request.getParameter("Query");
            Set<String> set = phraseSearching.phraseSearchingFunc(result);
            //String result = "Done";
            String resultLink = "";

            Integer i = 0;
            for (String link:set) {
                resultLink += "<h1>"+i.toString()+"</h1>";
                resultLink += "<a href =\""+link+"\">"+link+"</a>";
                i++;
            }

            response.setContentType("text/html");

            String page = "<!doctype html> <html> <body bgcolor=\"#f0f0f0\" align=\"center\"> "
                    +
                    result
                    +
                    "<h1>Resut</h1>"
                    +
                    resultLink
                    + "</body></html>";
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
