package de.dirty;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private List<String> stringList = new ArrayList<>();
    private Random random = new Random();
    private Arguments arguments;
    private String current = "";
    private File file, folder;

    public static void main(String[] args) throws Exception {
        new Main(args);
    }

    public Main(String[] args) throws Exception {
        arguments = new Arguments();
        for (int i = 0; i < args.length; i++) {
            switch (args[i].toLowerCase()) {
                case "-help":
                case "-?":
                    System.out.println("-?, -help | shows all arguments");
                    System.out.println("-rnd, -random | Random generated url");
                    System.out.println("-download | if the images should be downloaded");
                    System.out.println("-start | The start letters");
                    System.out.println("-stop | The stop letters");
                    System.out.println("-count | The number of links");
                    System.out.println("-save | saves the links in a file");
                    System.out.println("-out, -output | The output file for links");
                    System.out.println("-imgout, -imageoutput | The output folder for images");
                    return;
                case "-rnd":
                case "-random":
                    arguments.setRandomGen(true);
                    break;
                case "-imgout":
                case "-imageoutput":
                    arguments.setOutputFolder(args[i + 1]);
                    break;
                case "-out":
                case "-output":
                    arguments.setOutputFile(args[i + 1]);
                    break;
                case "-download":
                    arguments.setDownload(true);
                    break;
                case "-start":
                    arguments.setStart(args[i + 1]);
                    break;
                case "-stop":
                    arguments.setEnd(args[i + 1]);
                    break;
                case "-count":
                    arguments.setCount(Integer.parseInt(args[i + 1]));
                    break;
                case "-save":
                    arguments.setSave(true);
                    break;
            }
        }
        file = new File(arguments.getOutputFile());
        file.createNewFile();
        if (arguments.isDownload()) {
            folder = new File(arguments.getOutputFolder());
            folder.mkdirs();
        }
        if (arguments.isRandomGen()) {
            for (int i = 0; i < arguments.getCount(); i++) {
                generateRandomLink();
            }
        }
        else {
            current = arguments.getStart();
            generateLink();
        }
        if (arguments.isSave()) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String s : stringList) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            System.out.println("All link was saved in the file:" + file.getAbsolutePath());
        }
        if (arguments.isDownload()) {
            for (int i = 0; i < stringList.size(); i++) {
                BufferedImage image = ImageIO.read(new URL(stringList.get(i)));
                ImageIO.write(image, "png", new File(folder, (i + 1) + ".png"));
            }
            System.out.println("Images saved in: " + folder.getAbsolutePath());
        }
    }

    public void generateRandomLink() {
        current = "";
        for (int i = 0; i < 5; i++) {
            current += (char) (random.nextInt(26) + 'a');
        }
        scanLink("https://prnt.sc/" + current);
    }

    public void generateLink() {
        try { // todo: recreate this algorithm because it isn't working.
            System.out.println(current + ":" + arguments.getEnd());
            if (current.equals(arguments.getEnd())) {
                return;
            }
        } catch (Exception e) {
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

    public void scanLink(String link) {
        try {
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
                            if (arguments.isRandomGen()) {
                                generateRandomLink();
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            if (!arguments.isRandomGen()) {
                generateLink();
            }
        } catch (IOException e) {
            if (e.getMessage().contains("503")) {
                System.out.println("Received an 503 response sleeping...");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                scanLink("https://prnt.sc/" + current);
            }
            else {
                e.printStackTrace();
            }
        }
    }
}
