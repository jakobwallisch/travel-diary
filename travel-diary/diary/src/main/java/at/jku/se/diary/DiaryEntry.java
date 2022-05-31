/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.jku.se.diary;

import at.jku.se.diary.exceptions.DiaryEntryException;

import java.time.LocalDate;
import java.util.ArrayList;


/**
 *
 * @author reinhold
 */

public class DiaryEntry {


    private String title;
    private String notes;
    private String location;
    private LocalDate date;

    //Path of the stored Pictures
    private String pathPicture1;
    private String pathPicture2;
    private String pathPicture3;

    public ArrayList<TagEntry> getTagEntryArrayList() {
        return tagEntryArrayList;
    }

    public void setTagEntryArrayList(ArrayList<TagEntry> tagEntryArrayList) {
        this.tagEntryArrayList = tagEntryArrayList;
    }

    private ArrayList<TagEntry> tagEntryArrayList = new ArrayList<>();


    public DiaryEntry() {
    }

    public static DiaryEntry createNewEntry(String title, String location, String notes, LocalDate date, ArrayList<TagEntry> tagEntryArrayList) throws DiaryEntryException {
        if(title == null || title.length() < 1) {
            throw new DiaryEntryException("No title inserted!");
        }

        if(date == null){
            throw new DiaryEntryException("No date inserted!");
        }

        if(location == null || location.length() < 1){
            throw new DiaryEntryException("No location inserted!");
        }

        DiaryEntry newEntry = new DiaryEntry();
        newEntry.setTitle(title);
        newEntry.setLocation(location);
        newEntry.setNotes(notes);
        newEntry.setDate(date);
        newEntry.setTagEntryArrayList(tagEntryArrayList);
        return newEntry;
    }


    //iterates through the tagArrayList for filtering purposes
    public boolean containsTagFilter(ArrayList<TagEntry> arrayList, String filterValue){
        for (TagEntry tag: arrayList) {
            if (tag.getTag().equalsIgnoreCase(filterValue)){
                return true;
            };
        }
        return false;
    }
    //iterates through the tagArrayList for filtering purposes
    public boolean containsTagRatingFilter(ArrayList<TagEntry> arrayList, int rating){
        for (TagEntry tag: arrayList) {
            if (tag.getRating() == rating){
                return true;
            };
        }
        return false;
    }

    public static void setPictures(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPathPicture1() {
        return pathPicture1;
    }

    public void setPathPicture1(String pathPicture1) {
        this.pathPicture1 = pathPicture1;
    }

    public String getPathPicture2() {
        return pathPicture2;
    }

    public void setPathPicture2(String pathPicture2) {
        this.pathPicture2 = pathPicture2;
    }

    public String getPathPicture3() {
        return pathPicture3;
    }

    public void setPathPicture3(String pathPicture3) {
        this.pathPicture3 = pathPicture3;
    }


}