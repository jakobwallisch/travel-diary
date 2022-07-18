/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package at.jku.se.diary;

import at.jku.se.diary.exceptions.DiaryEntryException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class DiaryEntryTest {

    private final DiaryEntry e = new DiaryEntry();
    private final ArrayList<TagEntry> tagEntryArrayList= new ArrayList<>();
    private final ArrayList<DiaryEntry> diaryEntries = new ArrayList<>();

    /**
     * Method for testing to create a diary entry (using the createNewEntry method)
     * @throws DiaryEntryException
     * Assertion will be thrown if the title or the date is null
     */
    @Test
    void TestCreateDiaryEntry() throws DiaryEntryException {
        assertThrows(DiaryEntryException.class, () -> DiaryEntry.createNewEntry(null, null, null, null, null));
        tagEntryArrayList.add(new TagEntry("Bar", "Schöne Strandbar", 4));
        tagEntryArrayList.add(new TagEntry("Hotel", "schöne Hotelbar", 5));
        LocalDate date = LocalDate.of(2022, 5, 25);
        DiaryEntry e2 = DiaryEntry.createNewEntry("Test", "Linz", "hallo", date, tagEntryArrayList);
        assertEquals(e2.getTitle(), "Test");
        assertEquals(e2.getLocation(), "Linz");
        assertEquals(e2.getNotes(), "hallo");
        assertEquals(e2.getDate(), date);
        String picture1 = "pic1";
        e2.setPathPicture1(picture1);
        assertEquals(e2.getPathPicture1(), "pic1");
        String picture2 = "pic2";
        e2.setPathPicture2(picture2);
        assertEquals(e2.getPathPicture2(), "pic2");
        String picture3 = "pic3";
        e2.setPathPicture3(picture3);
        assertEquals(e2.getPathPicture3(), "pic3");
    }

    /**
     * Testing to remove an already existing entry
     * @throws DiaryEntryException
     * Exception can be thrown if the method createNewEntry fails
     */

    @Test
    void TestRemoveDiaryEntry() throws DiaryEntryException {
        LocalDate date123 = LocalDate.of(2022, 7, 6);
        DiaryEntry removed = DiaryEntry.createNewEntry("title123", "linz", null, date123, tagEntryArrayList);
        diaryEntries.add(removed);
        assertEquals(1, diaryEntries.size());
        diaryEntries.remove(removed);
        assertTrue(diaryEntries.stream().noneMatch(entry -> entry.equals(removed)));
    }



    private final String testTitle = "Visiting Linz";

    /**
     * Testing to set the title of an entry
     */
    @Test
    void TestSetTitle() {
        e.setTitle(testTitle);
        assertEquals(e.getTitle(), testTitle);
        assertNotEquals(e.getTitle(), "visiting Linz");
    }

    /**
     * Testing to get the title of an entry
     */
    @Test
    void TestGetTitle() {
        e.setTitle(testTitle);
        assertEquals(e.getTitle(), testTitle);
    }

    /**
     * Testing to get the notes of an entry
     */
    @Test
    void TestGetNotes() {
        assertNull(e.getNotes());
        e.setNotes("Es war sehr cool");
        assertEquals(e.getNotes(), "Es war sehr cool");
    }

    /**
     * Testing to set the notes of an entry
     */
    @Test
    void TestSetNotes() {
        String testNotes = "Es war sehr cool!";
        e.setNotes(testNotes);
        assertEquals(e.getNotes(), testNotes);
        assertNotEquals(e.getNotes(), "es war sehr cool");
    }


    private final String testLocation = "Linz";

    /**
     * Testing to get the location of an entry
     */
    @Test
    void TestGetLocation() {
        assertNull(e.getLocation());
        e.setLocation(testLocation);
        assertEquals(e.getLocation(), testLocation);
        assertNotEquals(e.getLocation(), "linz");
    }

    /**
     * Testing to set the location of an entry
     */
    @Test
    void TestSetLocation() {
        e.setLocation(testLocation);
        assertEquals(e.getLocation(), testLocation);
    }

    /**
     * Testing to get the date of an entry
     */
    @Test
    void TestGetDate() {
        assertNull(e.getDate());
        e.setDate(LocalDate.of(2022, 5, 21));
        assertEquals(e.getDate(), LocalDate.of(2022, 5, 21));
        assertNotEquals(e.getDate(), LocalDate.of(2022, 1, 10));
    }

    /**
     * Testing to set the date of an entry
     */
    @Test
    void TestSetDate() {
        e.setDate(LocalDate.of(2022, 5, 21));
        assertEquals(e.getDate(), LocalDate.of(2022, 5, 21));
    }


    private final String testPath = "img.png";
    private final String testPath2 = "img.png2";

    /**
     * Testing to get the path of the first picture that is added to an entry
     */
    @Test
    void TestGetPathPicture1() {
        assertNull(e.getPathPicture1());
        e.setPathPicture1(testPath);
        assertEquals(e.getPathPicture1(), testPath);
        assertNotEquals(e.getPathPicture1(), testPath2);
    }

    /**
     * Testing to set the path of the first picture that is added to an entry
     */
    @Test
    void TestSetPathPicture1() {
        e.setPathPicture1(testPath);
        assertEquals(e.getPathPicture1(), testPath);
    }

    /**
     * Testing to get the path of the second picture that is added to an entry
     */
    @Test
    void TestGetPathPicture2() {
        assertNull(e.getPathPicture2());
        e.setPathPicture2(testPath);
        assertEquals(e.getPathPicture2(), testPath);
        assertNotEquals(e.getPathPicture2(), testPath2);
    }

    /**
     * Testing to set the path of the second picture that is added to an entry
     */
    @Test
    void TestSetPathPicture2() {
        e.setPathPicture2(testPath);
        assertEquals(e.getPathPicture2(), testPath);
    }

    /**
     * Testing to get the path of the third picture that is added to an entry
     */
    @Test
    void TestGetPathPicture3() {
        assertNull(e.getPathPicture3());
        e.setPathPicture3(testPath);
        assertEquals(e.getPathPicture3(), testPath);
        assertNotEquals(e.getPathPicture3(), testPath2);
    }

    /**
     * Testing to set the path of the third picture that is added to an entry
     */
    @Test
    void TestSetPathPicture3(){
        e.setPathPicture3(testPath);
        assertEquals(e.getPathPicture3(), testPath);
    }
}
