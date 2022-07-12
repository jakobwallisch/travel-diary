package at.jku.se.diary.database;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

/**
 * This class is needed to save the correct format of the datePicker
 */
class LocalDateAdapter extends TypeAdapter<LocalDate> {

    /**
     * This method turns the localDate object in the right format into a string
     * @param jsonWriter this jsonWriter object is needed to convert the date in the right format
     * @param localDate the LocalDate object which will be formatted
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public void write(final JsonWriter jsonWriter, final LocalDate localDate) throws IOException {
        if (localDate == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(localDate.toString());
        }
    }

    /**
     * This method parses a string into the right format
     * @param jsonReader this jsonReader object is needed to parse the object
     * @return returns the date in the right format
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @Override
    public LocalDate read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        } else {
            return LocalDate.parse(jsonReader.nextString());
        }
    }
}