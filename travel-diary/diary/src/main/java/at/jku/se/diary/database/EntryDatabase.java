package at.jku.se.diary.database;

import at.jku.se.diary.DiaryEntry;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.*;
import java.util.ArrayList;

public class EntryDatabase {

    //private static File database = new File("/.database.json");
    private static File database = new File("D:\\travel-diary SE PR\\database.json");

    private FileWriter fileWriter = new FileWriter(database);
    //private JsonWriter jsonWriter = new JsonWriter(fileWriter);


    private static ArrayList<DiaryEntry> diaryEntries = new ArrayList<>();

    public EntryDatabase() throws IOException {
    }

    public void storeEntryInDatabase(DiaryEntry entry) throws IOException {

        //this check doesn't works right
       if (!(getTitlesOfAllDiaryEntries().contains(entry.getTitle()))){
            diaryEntries.add(entry);
        }

        Gson json = new GsonBuilder()
                //.setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        String jsonString = json.toJson(diaryEntries);

        System.out.println(jsonString);

        fileWriter.write(json.toJson(jsonString));
        fileWriter.close();
    }

    //---NOT WORKING--- reads the json objects from the json file and returns a ArrayList with DiaryEntry objects
    public ArrayList<DiaryEntry> readEntriesFromDatabase() throws IOException {
        Gson json = new GsonBuilder()
                //.setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        String jsonString = json.toJson(diaryEntries);


        Type entryALType = new TypeToken<ArrayList<DiaryEntry>>(){}.getType();
        ArrayList<DiaryEntry> result = json.fromJson(jsonString,entryALType);

        System.out.println(result.get(0).getTitle());

        return result;
    }

    //returns the titles of all the entries
    public ArrayList<String> getTitlesOfAllDiaryEntries(){

        ArrayList<String> result= new ArrayList<>();

        for (DiaryEntry e: diaryEntries) {
            result.add(e.getTitle());
        }
        return result;
    }


    //Getters and Setters
    public static ArrayList<DiaryEntry> getDiaryEntries() {
        return diaryEntries;
    }

}
