package util;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.util.JsonParser;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonParserTest {
    @Test
    public void testResume() {
        Resume R1 = ResumeTestData.getResume();
        String json = JsonParser.write(R1, Resume.class);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(R1, resume);
    }

    @Test
    public void write() {
        TextSection section1 = new TextSection();
        section1.setText("223");
        String json = JsonParser.write(section1, Section.class);
        Section section2 = JsonParser.read(json, Section.class);
        assertEquals(section1, section2);
    }
}