package at.jku.se.diary.database;

import at.jku.se.diary.DiaryEntry;
import at.jku.se.diary.exceptions.DiaryEntryException;
import at.jku.se.diary.TagEntry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private static ArrayList<TagEntry> tagEntryList = new ArrayList<>();

    @BeforeAll
    static void fillTagEntryList(){
        tagEntryList.add(new TagEntry("Bar", "gutes Bier", 4));
        tagEntryList.add(new TagEntry("Hotel", "gutes Bett", 5));
    }

    @Test
    void storeTagInDatabaseTest() {
        String tag = "Hotel";
        Database database = new Database();
        assertDoesNotThrow(() -> {
            database.storeTagInDatabase(tag);
        });
        assertEquals(1, database.getTagEntries().size());
    }

    @Test
    void storeEntryInDatabaseTest() throws IOException {
        DiaryEntry temp = new DiaryEntry();
        Database database = new Database();
        assertDoesNotThrow(() -> {
            database.storeEntryInDatabase(temp);
        });
        assertEquals(1,database.getDiaryEntries().size());

    }

    @Test
    void deleteEntryInDatabaseTest() {
        DiaryEntry temp = new DiaryEntry();
        Database database = new Database();

        assertDoesNotThrow(() -> {
            database.storeEntryInDatabase(temp);
        });
        assertDoesNotThrow(() -> {
            database.deleteEntryInDatabase(temp);
        });
        assertEquals(0,database.getDiaryEntries().size());
    }

    @Test
    void deleteTagInDatabaseTest() {
        String tag = "Hotel";
        Database database = new Database();
        assertDoesNotThrow(() -> {
            database.storeTagInDatabase(tag);
        });
        assertDoesNotThrow(() -> {
            database.deleteTagInDatabase(tag);
        });
        assertEquals(0, database.getTagEntries().size());
    }

    @Test
    void readEntriesFromDatabaseTest() throws IOException{
        LocalDate date = LocalDate.of(2022, 05, 25);
        Database database = new Database();
        assertDoesNotThrow(() -> {
            DiaryEntry e1 = DiaryEntry.createNewEntry("Entry1", "Linz", "notes", date, tagEntryList);
            database.storeEntryInDatabase(e1);
        });
        assertDoesNotThrow(() -> {
            DiaryEntry e1 = DiaryEntry.createNewEntry("Entry2", "Linz", "notes", date, tagEntryList);
            database.storeEntryInDatabase(e1);
        });
        assertDoesNotThrow(() -> {
            database.readEntriesFromDatabase();
        });
        assertThrows(DiaryEntryException.class, () -> {
            DiaryEntry e1 = DiaryEntry.createNewEntry("Entry3", "Linz", "notes", null, tagEntryList);
        });
    }

    @Test
    void readTagsFromDatabaseTest() throws IOException {
        String tag1 = "Hotel";
        String tag2 = "Strand";
        Database database = new Database();
        database.storeTagInDatabase(tag1);
        database.storeTagInDatabase(tag2);
        assertDoesNotThrow(() -> {
            database.readTagsFromDatabase();
        });
    }

    @Test
    void getTitlesOfAllDiaryEntriesTest() {
        LocalDate date = LocalDate.of(2022, 05, 25);
        Database database = new Database();
        assertDoesNotThrow(() -> {
            DiaryEntry e1 = DiaryEntry.createNewEntry("Entry1", "Linz", "notes", date, tagEntryList);
            database.storeEntryInDatabase(e1);
        });
        assertDoesNotThrow(() -> {
            DiaryEntry e2 = DiaryEntry.createNewEntry("Entry2", "Linz", "notes", date, tagEntryList);
            database.storeEntryInDatabase(e2);
        });
        assertEquals(database.getTitlesOfAllDiaryEntries().get(0), "Entry1");
        assertEquals(database.getTitlesOfAllDiaryEntries().get(1), "Entry2");
    }

    @Test
    void getDiaryEntriesTest() throws IOException {
        DiaryEntry e1 = new DiaryEntry();
        DiaryEntry e2 = new DiaryEntry();
        Database database = new Database();
        database.storeEntryInDatabase(e1);
        database.storeEntryInDatabase(e2);
        List<DiaryEntry> entries = database.getDiaryEntries();
        assertEquals(e1, entries.get(0));
        assertEquals(e2, entries.get(1));
    }

    @Test
    void getLocationsOfDiaryEntriesTest() throws DiaryEntryException {
        Database database = new Database();
        LocalDate date = LocalDate.of(2022, 05, 26);
        assertDoesNotThrow(() -> {
            DiaryEntry e = DiaryEntry.createNewEntry("Test1", "Linz", "notes", date, tagEntryList);
            database.storeEntryInDatabase(e);
        });
       assertEquals(database.getLocationsOfDiaryEntries().get(0), "Linz");
    }

    @Test
    void getTagEntriesTest() throws IOException {
        String tag = "Hotel";
        String tag2 = "Strand";
        Database database = new Database();
        database.storeTagInDatabase(tag);
        database.storeTagInDatabase(tag2);
        assertEquals(database.getTagEntries().get(0), "Hotel");
        assertEquals(database.getTagEntries().get(1), "Strand");
    }


}