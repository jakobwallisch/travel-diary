package at.jku.se.diary;

public class TagEntry {

    private String tag;
    private String tagText;
    private int rating;
    private String starString;

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
        this.rating = rating;
    }
}
