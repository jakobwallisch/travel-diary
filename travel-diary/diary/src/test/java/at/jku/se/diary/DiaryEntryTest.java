/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package at.jku.se.diary;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Florian Schindlbauer
 */
class DiaryEntryTest {

    private final DiaryEntry e = new DiaryEntry();
    /**
     * Test of createDiaryEntry method, of class CreateDiaryEntryController by using method createNewEntry of class DiaryEntry.
     */
    @Test
    void TestCreateDiaryEntry() throws DiaryEntryException {
        assertThrows(DiaryEntryException.class, () -> {
            DiaryEntry.createNewEntry(null, null, null, null);
        });
        LocalDate date = LocalDate.of(2022, 05, 25);
        DiaryEntry e = DiaryEntry.createNewEntry("Test", "Linz", "hallo", date);
        assertEquals(e.getTitle(), "Test");
        assertEquals(e.getLocation(), "Linz");
        assertEquals(e.getNotes(), "hallo");
        assertEquals(e.getDate(), date);
        String picture1 = "pic1";
        e.setPathPicture1(picture1);
        assertEquals(e.getPathPicture1(), "pic1");
        String picture2 = "pic2";
        e.setPathPicture2(picture2);
        assertEquals(e.getPathPicture2(), "pic2");
        String picture3 = "pic3";
        e.setPathPicture3(picture3);
        assertEquals(e.getPathPicture3(), "pic3");
    }

    @Test
    void TestRemoveDiaryEntry(){

    }

    /**
     * Test of setTitle method of class DiaryEntry.
     */
    @Test
    void TestSetTitle() {
        e.setTitle("Visiting Linz");
        assertEquals(e.getTitle(), "Visiting Linz");
        assertNotEquals(e.getTitle(), "visiting linz");
    }
    /**
     * Test of getTitle method of class DiaryEntry.
     */
    @Test
    void TestGetTitle() {
        e.setTitle("Visiting Linz");
        e.getTitle();
        assertEquals(e.getTitle(), "Visiting Linz");
    }
    /**
     * Test of getNotes method of class DiaryEntry.
     */
    @Test
    void TestGetNotes() {
        e.getNotes();
        assertEquals(e.getNotes(), null);
        e.setNotes("Es war sehr cool");
        assertEquals(e.getNotes(), "Es war sehr cool");
    }
    /**
     * Test of setNotes method of class DiaryEntry.
     */
    @Test
    void TestSetNotes() {
        e.setNotes("Es war sehr cool");
        assertEquals(e.getNotes(), "Es war sehr cool");
        assertNotEquals(e.getNotes(), "es war sehr cool");
    }
    /**
     * Test of getLocation method of class DiaryEntry.
     */
    @Test
    void TestGetLocation() {
        e.getLocation();
        assertEquals(e.getLocation(), null);
        e.setLocation("Linz");
        assertEquals(e.getLocation(), "Linz");
        assertNotEquals(e.getLocation(), "linz");
    }
    /**
     * Test of setLocation method of class DiaryEntry.
     */
    @Test
    void TestSetLocation() {
        e.setLocation("Linz");
        assertEquals(e.getLocation(), "Linz");
    }
    /**
     * Test of getDate method of class DiaryEntry.
     */
    @Test
    void TestGetDate() {
        e.getDate();
        assertEquals(e.getDate(), null);
        e.setDate(LocalDate.of(2022, 05, 21));
        assertEquals(e.getDate(), LocalDate.of(2022, 05, 21));
        assertNotEquals(e.getDate(), LocalDate.of(2022, 1, 10));
    }
    /**
     * Test of setDate method of class DiaryEntry.
     */
    @Test
    void TestSetDate() {
        e.setDate(LocalDate.of(2022, 05, 21));
        assertEquals(e.getDate(), LocalDate.of(2022, 05, 21));
    }
    /**
     * Test of getPathPicture1 method of class DiaryEntry.
     */
    @Test
    void TestGetPathPicture1() {
        e.getPathPicture1();
        assertEquals(e.getPathPicture1(), null);
        e.setPathPicture1("img.png");
        assertEquals(e.getPathPicture1(), "img.png");
        assertNotEquals(e.getPathPicture1(), "img2.png");
    }
    /**
     * Test of setPathPicture1 method of class DiaryEntry.
     */
    @Test
    void TestSetPathPicture1() {
        e.setPathPicture1("img.png");
        assertEquals(e.getPathPicture1(), "img.png");
    }
    /**
     * Test of getPathPicture2 method of class DiaryEntry.
     */
    @Test
    void TestGetPathPicture2() {
        e.getPathPicture2();
        assertEquals(e.getPathPicture2(), null);
        e.setPathPicture2("img.png");
        assertEquals(e.getPathPicture2(), "img.png");
        assertNotEquals(e.getPathPicture2(), "img2.png");
    }
    /**
     * Test of setPathPicture2 method of class DiaryEntry.
     */
    @Test
    void TestSetPathPicture2() {
        e.setPathPicture2("img.png");
        assertEquals(e.getPathPicture2(), "img.png");
    }
    /**
     * Test of getPathPicture3 method of class DiaryEntry.
     */
    @Test
    void TestGetPathPicture3() {
        e.getPathPicture3();
        assertEquals(e.getPathPicture3(), null);
        e.setPathPicture3("img.png");
        assertEquals(e.getPathPicture3(), "img.png");
        assertNotEquals(e.getPathPicture3(), "img2.png");
    }
    /**
     * Test of setPathPicture3 method of class DiaryEntry.
     */
    @Test
    void TestSetPathPicture3(){
        e.setPathPicture3("img.png");
        assertEquals(e.getPathPicture3(), "img.png");
    }
}
