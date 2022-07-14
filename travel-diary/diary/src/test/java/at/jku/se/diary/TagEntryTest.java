package at.jku.se.diary;

import at.jku.se.diary.exceptions.TagEntryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class TagEntryTest {


    private static String tag = "Strand";
    private static String text = "Es war sehr schoen";
    private static String tag2 = "tag";
    private static String textTest = "Test";
    private static String textTestLowerCase = "test";

    @Test
    void createNewTagEntry() throws TagEntryException {
        TagEntry tagEntry = TagEntry.createNewTagEntry(text, tag, 4);
        assertEquals(tagEntry.getTagText(), text);
        assertEquals(tagEntry.getTag(), tag);
        assertEquals(tagEntry.getRating(), 4);
        TagEntry t = TagEntry.createNewTagEntry("Es war sehr schoen!!", "Hotel", -1);
        assertEquals(t.getRating(), 0);
        TagEntry t2 = TagEntry.createNewTagEntry("Es war sehr schoen!!!!", "Bar", 6);
        assertEquals(t2.getRating(), 5);
    }

    @Test
    void getTag() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry(text, tag, 4);
        assertEquals(t.getTag(), tag);

    }


    @Test
    void setTag() throws TagEntryException {
        assertThrows(TagEntryException.class, () ->{
            TagEntry.createNewTagEntry(tag, null, 3);
        });
        TagEntry t = TagEntry.createNewTagEntry(textTest, tag2, 5);
        t.setTag("neuerTag");
        assertEquals(t.getTag(), "neuerTag");
    }

    @Test
    void getTagText() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry(textTest, tag2, 5);
        assertEquals(t.getTagText(), textTest);
        TagEntry t2 = TagEntry.createNewTagEntry(null, tag2, 5);
        assertEquals(t2.getTagText(), null);
    }

    @Test
    void setTagText() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry(textTest, tag2, 5);
        t.setTagText("neuerText");
        assertEquals(t.getTagText(), "neuerText");
    }

    @Test
    void setStarString() throws TagEntryException {
        //getStarString wird hier ebenfalls überprüft
        //Die Funktion, dass die richtige Anzahl an Sternen erscheint,
        // wird bei der Erstellung eines TagEntries autom. überprüft
        TagEntry t = TagEntry.createNewTagEntry(textTest, tag2, 5);
        t.setStarString(textTestLowerCase);
        assertEquals(t.getStarString(), textTestLowerCase);
    }
    /**
     * Test of getRating method of class TagEntry.
     * This method just return the integer number that you gave the created tag entry as parameter in method
     * createNewTagEntry
     */
    @Test
    void getRating() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry(textTest, tag2, 5);
        assertEquals(t.getRating(), 5);
    }
    /**
     * Test of setRating method of class TagEntry.
     * If the rating is less than one it will automatically be 0.
     * In case it is greater than 5 it will automatically be set to 5.
     */
    @Test
    void setRating() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry(textTest, tag2, 5);
        t.setRating(3);
        assertEquals(t.getRating(), 3);
        t.setRating(-1);
        assertEquals(t.getRating(), 0);
        t.setRating(6);
        assertEquals(t.getRating(), 5);
    }
}