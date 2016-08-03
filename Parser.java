package httpparser;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Parser extends Thread {

    OkHttpClient client = new OkHttpClient();
    String url;
    private static final Logger logger = LogManager.getLogger(Parser.class);

    Parser(String url) {
        this.url = url;
    }

    public void run() {
        try {
            parse(url);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

    }

    private String gethtml(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private Document getdoc(String url) throws IOException {
        String result = gethtml(url);
        Document doc = Jsoup.parse(result);
        return doc;
    }

    public void parse(String url) throws IOException {

        Document doc = getdoc(url);
        Data data = new Data();
        DB base = new DB();
        Elements link;

        for (int i = 0; i <= 10; i++) {
            link = doc.select("#theProxyList").select("tbody").select("tr:eq(" + i + ")").select("td:eq(1)");
            data.setIP(link.text());
            link = doc.select("#theProxyList").select("tbody").select("tr:eq(" + i + ")").select("td:eq(2)");
            data.setPort(Integer.parseInt(link.text()));
            link = doc.select("#theProxyList").select("tbody").select("tr:eq(" + i + ")").select("td:eq(5)");
            data.setType(link.text());
            base.insertDB(data.getIP(), data.getPort(), data.getType());
        }
    } 
}
