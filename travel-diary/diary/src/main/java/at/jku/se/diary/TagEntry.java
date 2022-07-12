package at.jku.se.diary;

import at.jku.se.diary.exceptions.TagEntryException;

/**
 * This class is used in order to create tags
 */

public class TagEntry {

    private String tag;
    private String tagText;
    private int rating;
    private String starString;

    /**
     * This is a default constructor with no parameters
     */
    public TagEntry(){}

    /**
     * Here The parameters are getting assigned
     * @param tag
     * name of the tag
     * @param tagText
     * description of the tag
     * @param rating
     * rating of the tag (f.e. Beach 4 Stars)
     */
    public TagEntry(String tag, String tagText, int rating){
        this.tag = tag;
        this.tagText = tagText;
        this.rating = rating;
    }

    /**
     * a new Tag entry will be created by assigning the params and creating a TagEntry object
     * @param tagText
     * description of the tag
     * @param tag
     * name of the tag
     * @param rating
     * rating of the tag (f.e. Beach 4 Stars)
     * @return TagEntry object
     * @throws TagEntryException
     * If no tag is selected an exception will be thrown
     */

    public static TagEntry createNewTagEntry(String tagText, String tag, int rating) throws TagEntryException {
        if(tag == null){
            throw new TagEntryException("No Tag selected!");
        }
        if(rating < 0){
            rating = 0;
        }
        if(rating > 5){
            rating = 5;
        }
        TagEntry tagEntry = new TagEntry();
        tagEntry.setTagText(tagText);
        tagEntry.setTag(tag);
        tagEntry.setRating(rating);
        tagEntry.setStarString("");
        for(int i = 0; i< rating;i++) {
            tagEntry.setStarString(tagEntry.getStarString() + '★');
        }
        return tagEntry;
    }

    /**
     * returns the tag
     * @return
     * tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     * sets the tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * returns the tag text
     * @return
     * return the description of the tag
     */
    public String getTagText() {
        return tagText;
    }

    /**
     * @param tagText
     * sets the tag text
     */
    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    /**
     * returns the amount of rating stars
     * @return
     * returns the amount of rating stars
     */
    public String getStarString() {
        return starString;
    }

    /**
     * @param starString
     * sets the amount of rating stars
     */
    public void setStarString(String starString) {
        this.starString = starString;
    }

    /**
     * returns the rating
     * @return
     * returns the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating
     * sets the rating
     * it must be from 0 to 5
     */
    public void setRating(int rating) {
        if (rating < 0){
            this.rating = 0;
        }
        else if(rating > 5){
            this.rating = 5;
        }
        else{
            this.rating = rating;
        }
    }
}
