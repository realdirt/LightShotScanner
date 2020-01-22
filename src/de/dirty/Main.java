package de.dirty;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private List<String> stringList = new ArrayList<>();
    private String current = "zaaaaa";
    private File file = new File("links.txt");

    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() throws Exception {
        file.createNewFile();
        generateLink();
    }

    public void generateLink() throws Exception {
        if (current.contains("zzzzzz")) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String s : stringList) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
            System.out.println("All link was saved in the file:" + file.getAbsolutePath());
            return;
        }
        String tmp = current;
        current = "";
        char[] chars = tmp.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                current += Character.toString((char) (((chars[i] - 'a' + 1) % 26) + 'a'));
            }
            else {
                if ('z' == chars[i - 1]) {
                    current += Character.toString((char) (((chars[i] - 'a' + 1) % 26) + 'a'));
                }
                else {
                    current += chars[i];
                }
            }
        }
        System.out.println(current);
        scanLink("https://prnt.sc/" + current);
    }

    public void scanLink(String link) throws Exception {
        StringBuilder html = new StringBuilder();
        System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        URL url = new URL(link);
        BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));

        String htmlLine;
        while ((htmlLine = input.readLine()) != null) {
            html.append(htmlLine);
        }
        input.close();

        String tmp = html.toString().replaceAll("<", "");
        String[] strings = tmp.split(">");
        for (String string : strings) {
            if (string.contains("id=\"screenshot-image\"")) {
                String[] tmpStrings = string.split("\"");
                for (String tmpString : tmpStrings) {
                    if (tmpString.contains("https://")) {
                        stringList.add(tmpString);
                        System.out.println(tmpString);
                        break;
                    }
                    if (tmpString.contains("//st")) {
                        System.out.println("Found removed screenshot");
                        break;
                    }
                }
                break;
            }
        }
        generateLink();
    }
}
