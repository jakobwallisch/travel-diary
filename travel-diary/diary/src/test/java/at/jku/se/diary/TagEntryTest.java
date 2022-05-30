package at.jku.se.diary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Florian Schindlbauer
 */
class TagEntryTest {
    /**
     * Test of createNewTagEntry method, of class CreateDiaryEntryController by using method createNewTagEntry of class TagEntry.
     */
    @Test
    void createNewTagEntry() throws TagEntryException {
        TagEntry tagEntry = TagEntry.createNewTagEntry("Es war sehr schoen", "Strand", 4);
        assertEquals(tagEntry.getTagText(), "Es war sehr schoen");
        assertEquals(tagEntry.getTag(), "Strand");
        assertEquals(tagEntry.getRating(), 4);
        TagEntry t = TagEntry.createNewTagEntry("Es war sehr schoen!!", "Hotel", -1);
        assertEquals(t.getRating(), 0);
        TagEntry t2 = TagEntry.createNewTagEntry("Es war sehr schoen!!!!", "Bar", 6);
        assertEquals(t2.getRating(), 5);
    }
    /**
     * Test of getTag method of class TagEntry.
     */
    @Test
    void getTag() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Es war sehr schoen", "Strand", 4);
        assertEquals(t.getTag(), "Strand");

    }
    /**
     * Test of setTag method of class TagEntry.
     */
    @Test
    void setTag() throws TagEntryException {
        assertThrows(TagEntryException.class, () ->{
            TagEntry.createNewTagEntry("Strand", null, 3);
        });
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        t.setTag("neuerTag");
        assertEquals(t.getTag(), "neuerTag");
    }
    /**
     * Test of getTagText method of class TagEntry.
     */
    @Test
    void getTagText() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        assertEquals(t.getTagText(), "Test");
        TagEntry t2 = TagEntry.createNewTagEntry(null, "tag", 5);
        assertEquals(t2.getTagText(), null);
    }
    /**
     * Test of setTagText method of class TagEntry.
     */
    @Test
    void setTagText() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        t.setTagText("neuerText");
        assertEquals(t.getTagText(), "neuerText");
    }
    /**
     * Test of getStarString method of class TagEntry.
     */
    @Test
    void getStarString() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        t.setStarString("test");
        assertEquals(t.getStarString(), "test");
    }
    /**
     * Test of getStarString method of class TagEntry.
     * If the right amount of stars appears is already tested in createNewTagEntry class, as after using this method
     * a for loop prints out the right amount of stars.
     * this for loop is determined in the method createNewTagEntry so it can not be ignored or passed over!
     */
    @Test
    void setStarString() throws TagEntryException {
        //Die Funktion, dass die richtige Anzahl an Sternen erscheint, wird bei der Erstellung eines TagEntries autom. überprüft
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        t.setStarString("test");
        assertEquals(t.getStarString(), "test");
    }
    /**
     * Test of getRating method of class TagEntry.
     * This method just return the integer number that you gave the created tag entry as parameter in method
     * createNewTagEntry
     */
    @Test
    void getRating() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        assertEquals(t.getRating(), 5);
    }
    /**
     * Test of setRating method of class TagEntry.
     * If the rating is less than one it will automatically be 0.
     * In case it is greater than 5 it will automatically be set to 5.
     */
    @Test
    void setRating() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        t.setRating(3);
        assertEquals(t.getRating(), 3);
        t.setRating(-1);
        assertEquals(t.getRating(), 0);
        t.setRating(6);
        assertEquals(t.getRating(), 5);
    }
}