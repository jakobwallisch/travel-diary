package at.jku.se.diary;

public class TagEntry {

    private String tag;
    private String tagText;
    private int rating;
    private String starString;

    public static TagEntry createNewTagEntry(String tagText, String tag, int rating) throws TagEntryException{
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public String getStarString() {
        return starString;
    }

    public void setStarString(String starString) {
        this.starString = starString;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 0){ this.rating = 0; }
        else if(rating > 5){ this.rating = 5;}
        else{ this.rating = rating; }
    }
}
