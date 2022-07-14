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

    @Test
    void TestCreateDiaryEntry() throws DiaryEntryException {
        assertThrows(DiaryEntryException.class, () -> {
            DiaryEntry.createNewEntry(null, null, null, null, null);
        });
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



    @Test
    void TestRemoveDiaryEntry() throws DiaryEntryException {
        LocalDate date123 = LocalDate.of(2022, 7, 6);
        DiaryEntry removed = DiaryEntry.createNewEntry("title123", "linz", null, date123, tagEntryArrayList);
        diaryEntries.add(removed);
        assertEquals(1, diaryEntries.size());
        diaryEntries.remove(removed);
        assertTrue(diaryEntries.stream().noneMatch(entry -> entry.equals(removed)));
    }



    private String testTitle = "Visiting Linz";
    @Test
    void TestSetTitle() {
        e.setTitle(testTitle);
        assertEquals(e.getTitle(), testTitle);
        assertNotEquals(e.getTitle(), "visiting Linz");
    }

    @Test
    void TestGetTitle() {
        e.setTitle(testTitle);
        e.getTitle();
        assertEquals(e.getTitle(), testTitle);
    }

    @Test
    void TestGetNotes() {
        e.getNotes();
        assertEquals(e.getNotes(), null);
        e.setNotes("Es war sehr cool");
        assertEquals(e.getNotes(), "Es war sehr cool");
    }


    private String testNotes = "Es war sehr cool!";
    @Test
    void TestSetNotes() {
        e.setNotes(testNotes);
        assertEquals(e.getNotes(), testNotes);
        assertNotEquals(e.getNotes(), "es war sehr cool");
    }


    private String testLocation = "Linz";

    @Test
    void TestGetLocation() {
        e.getLocation();
        assertEquals(e.getLocation(), null);
        e.setLocation(testLocation);
        assertEquals(e.getLocation(), testLocation);
        assertNotEquals(e.getLocation(), "linz");
    }

    @Test
    void TestSetLocation() {
        e.setLocation(testLocation);
        assertEquals(e.getLocation(), testLocation);
    }

    @Test
    void TestGetDate() {
        e.getDate();
        assertEquals(e.getDate(), null);
        e.setDate(LocalDate.of(2022, 5, 21));
        assertEquals(e.getDate(), LocalDate.of(2022, 5, 21));
        assertNotEquals(e.getDate(), LocalDate.of(2022, 1, 10));
    }

    @Test
    void TestSetDate() {
        e.setDate(LocalDate.of(2022, 5, 21));
        assertEquals(e.getDate(), LocalDate.of(2022, 5, 21));
    }


    private String testPath = "img.png";
    private String testPath2 = "img.png2";


    @Test
    void TestGetPathPicture1() {
        e.getPathPicture1();
        assertEquals(e.getPathPicture1(), null);
        e.setPathPicture1(testPath);
        assertEquals(e.getPathPicture1(), testPath);
        assertNotEquals(e.getPathPicture1(), testPath2);
    }

    @Test
    void TestSetPathPicture1() {
        e.setPathPicture1(testPath);
        assertEquals(e.getPathPicture1(), testPath);
    }

    @Test
    void TestGetPathPicture2() {
        e.getPathPicture2();
        assertEquals(e.getPathPicture2(), null);
        e.setPathPicture2(testPath);
        assertEquals(e.getPathPicture2(), testPath);
        assertNotEquals(e.getPathPicture2(), testPath2);
    }

    @Test
    void TestSetPathPicture2() {
        e.setPathPicture2(testPath);
        assertEquals(e.getPathPicture2(), testPath);
    }

    @Test
    void TestGetPathPicture3() {
        e.getPathPicture3();
        assertEquals(e.getPathPicture3(), null);
        e.setPathPicture3(testPath);
        assertEquals(e.getPathPicture3(), testPath);
        assertNotEquals(e.getPathPicture3(), testPath2);
    }

    @Test
    void TestSetPathPicture3(){
        e.setPathPicture3(testPath);
        assertEquals(e.getPathPicture3(), testPath);
    }
}
