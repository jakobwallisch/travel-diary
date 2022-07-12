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

public class Database {

    public Database() throws IOException {
        dir = this.readPathFromDatabase();
        database = new File(dir,"database.json");
    }

    private static String dir;
    private static final File path = new File("path.json");
    //private static final File database = new File("database.json");
    private static File database = null; //= new File(dir,"database.json");
    private static final File tags = new File("tags.json");



    private ArrayList<DiaryEntry> diaryEntries = new ArrayList<>();
    private ArrayList<String> tagEntries = new ArrayList<>();
    private static String absPath;


    public void storePathInDatabase(String absolutePath) throws IOException {

        absPath=absolutePath;

        Gson json = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (final FileWriter fw = new FileWriter(path)) { // make sure FileWriter is closed when leaving scope
            json.toJson(absPath, fw);
        }
    }

    public String readPathFromDatabase() throws IOException {
        if (!path.exists()) {
            return System.getProperty("user.dir");
        }

        Gson json = new GsonBuilder()
                .create();

        //ArrayList<String> loadedTags;
        String pathForDatabase;

        try (final FileReader fr = new FileReader(path)) { // make sure FileReader is closed when leaving scope
            pathForDatabase = json.fromJson(fr, new TypeToken<String>() {}.getType());
        }catch (Exception e){
            throw new RuntimeException("Error creating FileReader");
        }

        // if we didn't load anything (empty file or the like), then stick with an empty list
        if (pathForDatabase != null) {
            absPath = pathForDatabase;
            System.out.println(absPath+"gelesen");
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            return absPath;
        }
        return "";
    }



    public void storeTagInDatabase(String tagName) throws IOException {

        tagEntries.add(tagName);

        Gson json = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (final FileWriter fw = new FileWriter(tags)) { // make sure FileWriter is closed when leaving scope
            json.toJson(tagEntries, fw);
        }
    }

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

    public void deleteTagInDatabase(String tag) throws IOException {
        tagEntries.remove(tag);

        Gson json = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (final FileWriter fw = new FileWriter(tags)) { // make sure FileWriter is closed when leaving scope
            json.toJson(tagEntries, fw);
        }
    }




    public void readEntriesFromDatabase() throws IOException {
        if (!database.exists()) return;
        System.out.println("dir"+dir);
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


    //returns the titles of all the entries
    public List<String> getTitlesOfAllDiaryEntries(){
        return diaryEntries.stream().map(DiaryEntry::getTitle).collect(Collectors.toList());
    }


    //Getter
    public List<DiaryEntry> getDiaryEntries() {
        return Collections.unmodifiableList(diaryEntries);
    }

    //Get locations of the entries to show them on the map
    public List<String> getLocationsOfDiaryEntries(){

        List<DiaryEntry> allEntries = this.getDiaryEntries();
        List<String> result = new ArrayList<>();

        for (DiaryEntry entry: allEntries) {

            result.add(entry.getLocation());
        }

        return result;
    }

    public List<String> getTagEntries() {
        return Collections.unmodifiableList(tagEntries);
    }
}
