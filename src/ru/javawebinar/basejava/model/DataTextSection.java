package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class DataTextSection implements Section<List<DataTextSection.DataText>>{
    private final String title;
    private final List<DataText> dataTextList;

    public DataTextSection(String title) {
        this.title = title;
        this.dataTextList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addDataText(String fromTime, String toTime, String header, String text){
        dataTextList.add(new DataText(fromTime, toTime, header, text));
    }

    @Override
    public List<DataText> getInsideData() {
        return dataTextList;
    }

    protected static class DataText {
        private String fromTime;
        private String toTime;
        private String header;
        private String text;

        public DataText(String fromTime, String toTime, String header, String text) {
            this.fromTime = fromTime;
            this.toTime = toTime;
            this.header = header;
            this.text = text;
        }

        public String getFromTime() {
            return fromTime;
        }

        public void setFromTime(String fromTime) {
            this.fromTime = fromTime;
        }

        public String getToTime() {
            return toTime;
        }

        public void setToTime(String toTime) {
            this.toTime = toTime;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

}
