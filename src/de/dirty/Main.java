package de.dirty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        StringBuilder html = new StringBuilder();
        System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        URL url = new URL("https://prnt.sc/n25rtz");
        BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));

//        URLConnection urlConnection = url.openConnection();
//        urlConnection.set

        String htmlLine;
        while ((htmlLine = input.readLine()) != null) {
            html.append(htmlLine);
        }
        input.close();

        String rawImg = "";

        String tmp = html.toString().replaceAll("<", "");
        String[] strings = tmp.split(">");
        for (String string : strings) {
            if (string.contains("id=\"screenshot-image\"")) {
                String[] tmpStrings = string.split("\"");
                for (String tmpString : tmpStrings) {
                    if(tmpString.contains("https://")){
                        rawImg = tmpString;
                        break;
                    }
                }
                break;
            }
        }

        System.out.println(rawImg);

    }
}
