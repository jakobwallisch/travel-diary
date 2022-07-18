package at.jku.se.diary.database;

import at.jku.se.diary.DiaryEntry;
import at.jku.se.diary.exceptions.DiaryEntryException;
import at.jku.se.diary.TagEntry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * In this class the database components will be tested
 */
class DatabaseTest {

    private static final ArrayList<TagEntry> tagEntryList = new ArrayList<>();
    public static final File database = new File("tags.json");
    private static final String tagName = "Hotel";

    /**
     * Testing the method to fill the tag entry list with new tags
     */
    @BeforeAll
    static void fillTagEntryList(){
        tagEntryList.add(new TagEntry("Bar", "gutes Bier", 4));
        tagEntryList.add(new TagEntry(tagName, "gutes Bett", 5));
    }

    /**
     * Here it is tested if the database has been created succesfully (so it has to exist)
     */
    @Test
    void testFileCreation(){
        assertTrue(database.exists());
    }

    /**
     * Testing to store a tag in the database, no exception should be thrown
     */
    @Test
    void storeTagInDatabaseTest() {
        Database database1 = new Database();
        assertDoesNotThrow(() -> database1.storeTagInDatabase(tagName));
        assertEquals(1, database1.getTagEntries().size());
    }

    /**
     * Testing to store an entry in the database
     */
    @Test
    void storeEntryInDatabaseTest() {
        DiaryEntry temp = new DiaryEntry();
        Database database2 = new Database();
        assertDoesNotThrow(() -> database2.storeEntryInDatabase(temp));
        assertEquals(1,database2.getDiaryEntries().size());
    }

    /**
     * Deleting an entry from the database. First it is stored after it is deleted.
     */
    @Test
    void deleteEntryInDatabaseTest() {
        DiaryEntry temp = new DiaryEntry();
        Database database3 = new Database();

        assertDoesNotThrow(() -> database3.storeEntryInDatabase(temp));
        assertDoesNotThrow(() -> database3.deleteEntryInDatabase(temp));
        assertEquals(0,database3.getDiaryEntries().size());
    }

    /**
     * Deleting a tag from the database. First it is stored after it is deleted.
     */
    @Test
    void deleteTagInDatabaseTest() {
        String tag = tagName;
        Database database4 = new Database();
        assertDoesNotThrow(() -> database4.storeTagInDatabase(tag));
        assertDoesNotThrow(() -> database4.deleteTagInDatabase(tag));
        assertEquals(0, database4.getTagEntries().size());
    }

    private static final String notes = "notes";
    private static final String entry1 = "Entry1";
    private static final String entry2 = "Entry2";
    private static final String location = "Linz";
    private static final String strand = "Strand";

    /**
     * Testing if the entries from the database are read right. An assertion is thrown if the date is null.
     */
    @Test
    void readEntriesFromDatabaseTest() {
        LocalDate date = LocalDate.of(2022, 5, 25);
        Database database5 = new Database();
        assertDoesNotThrow(() -> {
            DiaryEntry e1 = DiaryEntry.createNewEntry(entry1, location, notes, date, tagEntryList);
            database5.storeEntryInDatabase(e1);
        });
        assertDoesNotThrow(() -> {
            DiaryEntry e1 = DiaryEntry.createNewEntry(entry2, location, notes, date, tagEntryList);
            database5.storeEntryInDatabase(e1);
        });
        assertDoesNotThrow(() -> database5.readEntriesFromDatabase());
        assertThrows(DiaryEntryException.class, () -> {
            DiaryEntry e1 = DiaryEntry.createNewEntry("Entry3", location, notes, null, tagEntryList);
        });
    }

    /**
     * Testing if the tags for the database are read right.
     * @throws IOException
     * An exception will be thrown if the tags cannot be read right
     */
    @Test
    void readTagsFromDatabaseTest() throws IOException {
        Database database6 = new Database();
        database6.storeTagInDatabase(tagName);
        database6.storeTagInDatabase(strand);
        assertDoesNotThrow(database6::readTagsFromDatabase);
    }

    /**
     * Testing if all the titles aff the diary entries are returned right.
     * An assertion will be thrown if the title is null.
     */
    @Test
    void getTitlesOfAllDiaryEntriesTest() {
        LocalDate date = LocalDate.of(2022, 5, 25);
        Database database7 = new Database();
        assertDoesNotThrow(() -> {
            DiaryEntry e1 = DiaryEntry.createNewEntry(entry1, location, notes, date, tagEntryList);
            database7.storeEntryInDatabase(e1);
        });
        assertDoesNotThrow(() -> {
            DiaryEntry e2 = DiaryEntry.createNewEntry(entry2, location, notes, date, tagEntryList);
            database7.storeEntryInDatabase(e2);
        });
        assertEquals(database7.getTitlesOfAllDiaryEntries().get(0), entry1);
        assertEquals(database7.getTitlesOfAllDiaryEntries().get(1), entry2);
        assertThrows(DiaryEntryException.class, () -> DiaryEntry.createNewEntry(null, null, notes, date, tagEntryList));
    }

    /**
     * Testing the method to return all the diary entries in general (return type is Diary Entry)
     * @throws IOException
     * Exception can be thrown if the entry for the test cannot be stored in the database.
     */
    @Test
    void getDiaryEntriesTest() throws IOException {
        DiaryEntry e1 = new DiaryEntry();
        DiaryEntry e2 = new DiaryEntry();
        Database database8 = new Database();
        database8.storeEntryInDatabase(e1);
        database8.storeEntryInDatabase(e2);
        List<DiaryEntry> entries = database8.getDiaryEntries();
        assertEquals(e1, entries.get(0));
        assertEquals(e2, entries.get(1));
    }

    /**
     * Testing the class to get all the locations of the diary entries.
     */
    @Test
    void getLocationsOfDiaryEntriesTest() {
        Database database9 = new Database();
        LocalDate date = LocalDate.of(2022, 5, 26);
        assertDoesNotThrow(() -> {
            DiaryEntry e = DiaryEntry.createNewEntry("Test1", location, notes, date, tagEntryList);
            database9.storeEntryInDatabase(e);
        });
       assertEquals(database9.getLocationsOfDiaryEntries().get(0), location);
    }

    /**
     * Testing the method to get all the tag entries (Tag is the return type)
     * @throws IOException
     * Exception can be thrown if the tag for the test cannot be stored in the database.
     */
    @Test
    void getTagEntriesTest() throws IOException {
        Database database0 = new Database();
        database0.storeTagInDatabase(tagName);
        database0.storeTagInDatabase(strand);
        assertEquals(database0.getTagEntries().get(0), tagName);
        assertEquals(database0.getTagEntries().get(1), strand);
    }


}