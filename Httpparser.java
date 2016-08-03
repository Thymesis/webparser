package httpparser;

import java.io.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Httpparser {

    public static void main(String[] args) throws IOException {
        
        for (int i = 1; i < 9; i++) {
            new Parser("http://foxtools.ru/Proxy?page=" + i).start();
        }
    }
}
