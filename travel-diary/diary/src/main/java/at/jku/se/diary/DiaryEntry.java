/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.jku.se.diary;

/**
 *
 * @author reinhold
 */
public class DiaryEntry {
    
    private String title;
     
    public DiaryEntry(String title) {
        this.title= title;
    }
    
    
    public void setTitle(String title) {
        if (title != null && title.length() > 0)
            this.title= title;
    }  
    
    public String getTitle() {
        return title;
    }

}
