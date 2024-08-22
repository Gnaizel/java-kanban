package model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

public class DurationTypeAdapter extends TypeAdapter<Duration> {
    @Override
    public void write(JsonWriter out, Duration duration) throws IOException {
        out.value(duration != null ? duration.toString() : null);
    }

    @Override
    public Duration read(JsonReader in) throws IOException {
        String value = in.nextString();
        return value != null ? Duration.parse(value) : null;
    }
}
