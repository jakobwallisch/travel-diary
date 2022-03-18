/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.jku.se.diary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author reinhold
 */
class DiaryEntryTest {
    
   private final DiaryEntry e= new DiaryEntry("Visiting Acropolis");

    /**
     * Test of setTitle method, of class DiaryEntry.
     */
    @Test
    void setTitle() {
        e.setTitle("Going San Francisco");
        assertEquals(e.getTitle(), "Going San Francisco");
    }
    
}
