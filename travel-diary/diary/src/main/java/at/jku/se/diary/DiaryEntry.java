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
 * This class represents diary entries, which will get stored in the database
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

    /**
     * Sets a tag entry to an tagEntryArrayList
     * @param tagEntryArrayList the arrayList to set
     */
    public void setTagEntryArrayList(ArrayList<TagEntry> tagEntryArrayList) {
        this.tagEntryArrayList = tagEntryArrayList;
    }

    private ArrayList<TagEntry> tagEntryArrayList = new ArrayList<>();

    /**
     * This method creates an DiaryEntry object
     * @param title Title is the name of the entry
     * @param location Location of the entry (Where the journey took place)
     * @param notes Notes about the journey
     * @param date When the journey took place
     * @param tagEntryArrayList
     * This method is the actual method which will be called from the ViewEntryController to actually create a Diary Entry
     * The parameters title, location, notes, date are used for creating the Entry
     * The tagEntryArrayList contains all the tags for this entry
     * @return a created Entry will be returned
     * @throws DiaryEntryException Exception is thrown if the title, the date or the location is null
     */
    public static DiaryEntry createNewEntry(
            String title, String location, String notes, LocalDate date, ArrayList<TagEntry> tagEntryArrayList)
            throws DiaryEntryException {
        if(title == null || title.length() < 1) {
            throw new DiaryEntryException("No title inserted!");
        }

        if(date == null){
            throw new DiaryEntryException("No date inserted!");
        }

        DiaryEntry newEntry = new DiaryEntry();
        newEntry.setTitle(title);
        newEntry.setLocation(location);
        newEntry.setNotes(notes);
        newEntry.setDate(date);
        newEntry.setTagEntryArrayList(tagEntryArrayList);
        return newEntry;
    }

    /**
     * iterates through the tagArrayList for filtering purposes
     * @param arrayList list to iterate through
     * @param filterValue the value to be filtered
     * @return returns true or false if the filter value is true or false
     */
    public boolean containsTagFilter(ArrayList<TagEntry> arrayList, String filterValue){
        for (TagEntry tag: arrayList) {
            if (tag.getTag().equalsIgnoreCase(filterValue)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method filters the rating of entries
     * @param arrayList the list to be filtered
     * @param rating the actual rating
     * @param filterValue the filter value for the rating
     * @return true or false if the filter is correct or false
     */
    public boolean containsTagRatingFilter(ArrayList<TagEntry> arrayList, int rating, String filterValue){
        for (TagEntry tag: arrayList) {
            if ((tag.getRating() == rating) && (tag.getTag().equalsIgnoreCase(filterValue))){
                return true;
            }
        }
        return false;
    }

    /**
     * iterates through the tagArrayList for filtering text purposes
     * @param arrayList the list to be filtered
     * @param filterValue the filter value for the text
     * @return true or false if the filter is correct or false
     */
    public boolean containsTagTextFilter(ArrayList<TagEntry> arrayList,String filterValue){
        if (!arrayList.isEmpty()) {

            for (TagEntry tag : arrayList) {
                if ((tag.getTagText().toLowerCase().contains(filterValue.toLowerCase()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * returns the title of the entry
     * @return the title of the entry
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title of the entry
     * @param title title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * returns the notes of the entry
     * @return returns the notes of the entry
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes
     * set the notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * returns location
     * @return
     * location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location set the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * returns the manually set date of the entry
     * @return date of the entry
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * set the date of the entry
     * @param date date to be set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * return path of the picture
     * @return path of the picture
     */
    public String getPathPicture1() {
        return pathPicture1;
    }

    /**
     * set the path of the picture that shall be added
     * @param pathPicture1 path of the picture to be set
     */

    public void setPathPicture1(String pathPicture1) {
        this.pathPicture1 = pathPicture1;
    }
    /**
     * return path of the picture
     * @return
     * path of the picture
     */
    public String getPathPicture2() {
        return pathPicture2;
    }
    /**
     * @param pathPicture2
     * set the path of the picture that shall be added
     */
    public void setPathPicture2(String pathPicture2) {
        this.pathPicture2 = pathPicture2;
    }
    /**
     * return path of the picture
     * @return
     * path of the picture
     */
    public String getPathPicture3() {
        return pathPicture3;
    }
    /**
     * @param pathPicture3
     * set the path of the picture that shall be added
     */
    public void setPathPicture3(String pathPicture3) {
        this.pathPicture3 = pathPicture3;
    }


}