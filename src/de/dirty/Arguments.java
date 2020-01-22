package de.dirty;

public class Arguments {
    private boolean randomGen = false;
    private boolean download = false;
    private boolean save = false;
    private int count = 6;
    private String start = "zaaaaa";
    private String end = "zzzzzz";
    private String outputFile = "links.txt";
    private String outputFolder = "Images";

    public boolean isRandomGen() {
        return randomGen;
    }

    public void setRandomGen(boolean randomGen) {
        this.randomGen = randomGen;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }
}
