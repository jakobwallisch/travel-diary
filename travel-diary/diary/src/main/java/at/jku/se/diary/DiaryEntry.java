/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.jku.se.diary;

import java.util.Date;

/**
 *
 * @author reinhold
 */

public class DiaryEntry {
    
    private String title;
    private String notes;
    private String location;
    private Date date;


    
    public void setTitle(String title) {
        if (title != null && title.length() > 0)
            this.title= title;
    }  
    
    public String getTitle() {
        return title;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public DiaryEntry(String title, Date date) {
        this.title= title;
        this.date= date;

    }
}