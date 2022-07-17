package at.jku.se.diary;

import at.jku.se.diary.exceptions.TagEntryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class TagEntryTest {


    private static final String tag = "Strand";
    private static final String text = "Es war sehr schoen";
    private static final String tag2 = "tag";
    private static final String textTest = "Test";
    private static final String textTestLowerCase = "test";

    /**
     * Testing the createNewTagEntry method which is called to create a new tag
     * @throws TagEntryException
     * Exception will be thrown if the tag parameter (title) is null
     */
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
        assertThrows(TagEntryException.class, () -> TagEntry.createNewTagEntry("Es war schön", null, 2));
    }

    /**
     * Testing to return a tag
     * @throws TagEntryException
     * Exception will be thrown if the method createNewTagEntry throws it
     */
    @Test
    void getTag() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry(text, tag, 4);
        assertEquals(t.getTag(), tag);

    }

    /**
     * Testing to set a tag
     * @throws TagEntryException
     * Exception can be thrown if the createNewTagEntry throws it
     */
    @Test
    void setTag() throws TagEntryException {
        assertThrows(TagEntryException.class, () -> TagEntry.createNewTagEntry(tag, null, 3));
        TagEntry t = TagEntry.createNewTagEntry(textTest, tag2, 5);
        t.setTag("neuerTag");
        assertEquals(t.getTag(), "neuerTag");
    }

    /**
     * Testing to get the text of a tag, can be null
     * @throws TagEntryException
     * Exception can be thrown if the createNewTagEntry throws it
     */
    @Test
    void getTagText() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry(textTest, tag2, 5);
        assertEquals(t.getTagText(), textTest);
        TagEntry t2 = TagEntry.createNewTagEntry(null, tag2, 5);
        assertNull(t2.getTagText());
    }

    /**
     * Testing to set the tag text of a tag
     * @throws TagEntryException
     * Exception can be thrown if the createNewTagEntry throws it
     */
    @Test
    void setTagText() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry(textTest, tag2, 5);
        t.setTagText("neuerText");
        assertEquals(t.getTagText(), "neuerText");
    }

    /**
     * Testing to set the amount of rating stars
     * @throws TagEntryException
     * Exception can be thrown if the createNewTagEntry throws it
     */
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
     * @throws TagEntryException
     * Exception can be thrown if the createNewTagEntry throws it
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
     * @throws TagEntryException
     * Exception can be thrown if the createNewTagEntry throws it
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