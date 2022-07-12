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

class DatabaseTest {

    private static ArrayList<TagEntry> tagEntryList = new ArrayList<>();
    public static final File database = new File("tags.json");
    private static String tagName = "Hotel";

    @BeforeAll
    static void fillTagEntryList(){
        tagEntryList.add(new TagEntry("Bar", "gutes Bier", 4));
        tagEntryList.add(new TagEntry(tagName, "gutes Bett", 5));
    }

    @Test
    void testFileCreation(){
        assertTrue(database.exists());
    }

    @Test
    void storeTagInDatabaseTest() throws IOException{
        String tag = tagName;
        Database database1 = new Database();
        assertDoesNotThrow(() -> {
            database1.storeTagInDatabase(tag);
        });
        assertEquals(1, database1.getTagEntries().size());
    }

    @Test
    void storeEntryInDatabaseTest() throws IOException {
        DiaryEntry temp = new DiaryEntry();
        Database database2 = new Database();
        assertDoesNotThrow(() -> {
            database2.storeEntryInDatabase(temp);
        });
        assertEquals(1,database2.getDiaryEntries().size());

    }

    @Test
    void deleteEntryInDatabaseTest() throws IOException {
        DiaryEntry temp = new DiaryEntry();
        Database database3 = new Database();

        assertDoesNotThrow(() -> {
            database3.storeEntryInDatabase(temp);
        });
        assertDoesNotThrow(() -> {
            database3.deleteEntryInDatabase(temp);
        });
        assertEquals(0,database3.getDiaryEntries().size());
    }

    @Test
    void deleteTagInDatabaseTest() throws IOException{
        String tag = tagName;
        Database database4 = new Database();
        assertDoesNotThrow(() -> {
            database4.storeTagInDatabase(tag);
        });
        assertDoesNotThrow(() -> {
            database4.deleteTagInDatabase(tag);
        });
        assertEquals(0, database4.getTagEntries().size());
    }

    private static String notes = "notes";
    private static String entry1 = "Entry1";
    private static String entry2 = "Entry2";
    private static String location = "Linz";
    private static String strand = "Strand";

    @Test
    void readEntriesFromDatabaseTest() throws IOException{
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
        assertDoesNotThrow(() -> {
            database5.readEntriesFromDatabase();
        });
        assertThrows(DiaryEntryException.class, () -> {
            DiaryEntry e1 = DiaryEntry.createNewEntry("Entry3", location, notes, null, tagEntryList);
        });
    }

    @Test
    void readTagsFromDatabaseTest() throws IOException {
        String tag1 = tagName;
        String tag2 = strand;
        Database database6 = new Database();
        database6.storeTagInDatabase(tag1);
        database6.storeTagInDatabase(tag2);
        assertDoesNotThrow(() -> {
            database6.readTagsFromDatabase();
        });
    }

    @Test
    void getTitlesOfAllDiaryEntriesTest() throws IOException {
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
    }

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

    @Test
    void getLocationsOfDiaryEntriesTest() throws DiaryEntryException, IOException {
        Database database9 = new Database();
        LocalDate date = LocalDate.of(2022, 5, 26);
        assertDoesNotThrow(() -> {
            DiaryEntry e = DiaryEntry.createNewEntry("Test1", location, notes, date, tagEntryList);
            database9.storeEntryInDatabase(e);
        });
       assertEquals(database9.getLocationsOfDiaryEntries().get(0), location);
    }

    @Test
    void getTagEntriesTest() throws IOException {
        String tag = tagName;
        String tag2 = strand;
        Database database0 = new Database();
        database0.storeTagInDatabase(tag);
        database0.storeTagInDatabase(tag2);
        assertEquals(database0.getTagEntries().get(0), tagName);
        assertEquals(database0.getTagEntries().get(1), strand);
    }


}