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

    private static final File database = new File("database.json");

    private ArrayList<DiaryEntry> diaryEntries = new ArrayList<>();

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

}
