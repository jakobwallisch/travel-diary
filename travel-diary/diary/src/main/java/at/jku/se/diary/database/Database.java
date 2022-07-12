package at.jku.se.diary.database;

import at.jku.se.diary.DiaryEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to create a Database
 */

public class Database {

    private static final File database = new File("database.json");
    private static final File tags = new File("tags.json");



    private ArrayList<DiaryEntry> diaryEntries = new ArrayList<>();
    private ArrayList<String> tagEntries = new ArrayList<>();

    /**
     *
     * @param tagName
     * A tag with tagName will be saved in the database
     * @throws IOException
     * Throws exception if the Filewriter cannot be created
     */
    public void storeTagInDatabase(String tagName) throws IOException {

        tagEntries.add(tagName);

        Gson json = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (final FileWriter fw = new FileWriter(tags)) {
            json.toJson(tagEntries, fw);
        }
    }

    /**
     * @param entry
     * Stores an entry in the database
     * @throws IOException
     * Throws exception if the FileWriter cannot be created
     */
    public void storeEntryInDatabase(DiaryEntry entry) throws IOException {

        diaryEntries.add(entry);

        Gson json = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        try (final FileWriter fw = new FileWriter(database)) { // make sure FileWriter is closed when leaving scope
            json.toJson(diaryEntries, fw);
        }
   }

    /**
     * @param entry
     * Deletes an entry from the database
     * @throws IOException
     * Throws exception if the FileWriter cannot be created
     */
    public void deleteEntryInDatabase(DiaryEntry entry) throws IOException {

        diaryEntries.remove(entry);

        Gson json = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        try (final FileWriter fw = new FileWriter(database)) { // make sure FileWriter is closed when leaving scope
            json.toJson(diaryEntries, fw);
        }
    }

    /**
     * @param entry
     * Updates a specific entry from the database
     * @throws IOException
     * An exception will be thrown if the entry cannot be updated
     * Throws exception if the FileWriter cannot be created
     */
    public void updateEntryInDatabase(DiaryEntry entry) throws IOException{

        DiaryEntry newEntry = entry;

        for (DiaryEntry e: diaryEntries) {
            if (e.getTitle().equalsIgnoreCase(newEntry.getTitle())){
                deleteEntryInDatabase(e);
                storeEntryInDatabase(newEntry);

                Gson json = new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();
                try (final FileWriter fw = new FileWriter(database)) { // make sure FileWriter is closed when leaving scope
                    json.toJson(diaryEntries, fw);
                }
                return;
            }
        }
    }

    /**
     * @param tag
     * Deletes a specific tag from the database
     * @throws IOException
     * Throws exception if the FileWriter cannot be created
     */
    public void deleteTagInDatabase(String tag) throws IOException {
        tagEntries.remove(tag);

        Gson json = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (final FileWriter fw = new FileWriter(tags)) { // make sure FileWriter is closed when leaving scope
            json.toJson(tagEntries, fw);
        }
    }


    /**
     * This method reads all entries from a database
     * @throws IOException
     * An Exception will be thrown if the FileWriter cannot be created
     */

    public void readEntriesFromDatabase() throws IOException {
        Gson json = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        ArrayList<DiaryEntry> loadedDiaryEntries;

        try (final FileReader fr = new FileReader(database)) { // make sure FileReader is closed when leaving scope
            loadedDiaryEntries = json.fromJson(fr, new TypeToken<ArrayList<DiaryEntry>>() {}.getType());
        }catch (Exception e){
            throw new RuntimeException("Error creating FileReader");
        }

        // if we didn't load anything (empty file or the like), then stick with an empty list
        if (loadedDiaryEntries != null) {
            diaryEntries = loadedDiaryEntries;
        }
    }

    /**
     * This method reads all tags from the database
     * @throws IOException
     * An Exception will be thrown if the FileWriter cannot be created
     */
    public void readTagsFromDatabase() throws IOException {
        Gson json = new GsonBuilder()
                .create();

        ArrayList<String> loadedTags;

        try (final FileReader fr = new FileReader(tags)) { // make sure FileReader is closed when leaving scope
            loadedTags = json.fromJson(fr, new TypeToken<ArrayList<String>>() {}.getType());
        }catch (Exception e){
            throw new RuntimeException("Error creating FileReader");
        }

        // if we didn't load anything (empty file or the like), then stick with an empty list
        if (loadedTags != null) {
            tagEntries = loadedTags;
        }
    }

    /**
     * This method returns all titles of every entry in the database in a list
     * @return
     * returns a list
     */
    //returns the titles of all the entries
    public List<String> getTitlesOfAllDiaryEntries(){
        return diaryEntries.stream().map(DiaryEntry::getTitle).collect(Collectors.toList());
    }

    /**
     * This method returns all Entries in a List
     * @return
     * returns a list
     */
    //Getter
    public List<DiaryEntry> getDiaryEntries() {
        return Collections.unmodifiableList(diaryEntries);
    }

    /**
     * This method returns all locations of the DiaryEntries in a list
     * @return
     * returns a list
     */
    //Get locations of the entries to show them on the map
    public List<String> getLocationsOfDiaryEntries(){

        List<DiaryEntry> allEntries = this.getDiaryEntries();
        List<String> result = new ArrayList<>();

        for (DiaryEntry entry: allEntries) {

            result.add(entry.getLocation());
        }

        return result;
    }

    /**
     * This method returns all tag entries in a list
     * @return
     * returns a list
     */
    public List<String> getTagEntries() {
        return Collections.unmodifiableList(tagEntries);
    }
}
