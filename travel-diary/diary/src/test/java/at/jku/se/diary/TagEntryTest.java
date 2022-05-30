package at.jku.se.diary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Florian Schindlbauer
 */
class TagEntryTest {

    @Test
    void createNewTagEntry() throws TagEntryException {
        TagEntry tagEntry = TagEntry.createNewTagEntry("Es war sehr schoen", "Strand", 4);
        assertEquals(tagEntry.getTagText(), "Es war sehr schoen");
        assertEquals(tagEntry.getTag(), "Strand");
        assertEquals(tagEntry.getRating(), 4);
        TagEntry t = TagEntry.createNewTagEntry("Es war sehr schoen!!", "Hotel", -1);
        assertEquals(t.getRating(), 0);
    }

    @Test
    void getTag() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Es war sehr schoen", "Strand", 4);
        assertEquals(t.getTag(), "Strand");

    }

    @Test
    void setTag() throws TagEntryException {
        assertThrows(TagEntryException.class, () ->{
            TagEntry.createNewTagEntry("Strand", null, 3);
        });
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        t.setTag("neuerTag");
        assertEquals(t.getTag(), "neuerTag");
    }

    @Test
    void getTagText() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        assertEquals(t.getTagText(), "Test");
        TagEntry t2 = TagEntry.createNewTagEntry(null, "tag", 5);
        assertEquals(t2.getTagText(), null);
    }

    @Test
    void setTagText() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        t.setTagText("neuerText");
        assertEquals(t.getTagText(), "neuerText");
    }

    @Test
    void getStarString() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        t.setStarString("test");
        assertEquals(t.getStarString(), "test");
    }

    @Test
    void setStarString() throws TagEntryException {
        //Die Funktion, dass die richtige Anzahl an Sternen erscheint, wird bei der Erstellung eines TagEntries autom. überprüft
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        t.setStarString("test");
        assertEquals(t.getStarString(), "test");
    }

    @Test
    void getRating() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        assertEquals(t.getRating(), 5);
    }

    @Test
    void setRating() throws TagEntryException {
        TagEntry t = TagEntry.createNewTagEntry("Test", "tag", 5);
        t.setRating(3);
        assertEquals(t.getRating(), 3);
        t.setRating(-1);
        assertEquals(t.getRating(), 0);
        //rating wird bei negativ gesetztem Wert automatisch auf 0 gesetzt
    }
}