package typeAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Subtask;

import java.lang.reflect.Type;

import static model.Task.formatter;

public class SubtaskAdapter implements JsonSerializer<Subtask> {
    @Override
    public JsonElement serialize(Subtask subtask, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", subtask.getId());
        jsonObject.addProperty("name", subtask.getTaskName());
        jsonObject.addProperty("status", subtask.getStatus().toString());
        jsonObject.addProperty("description", subtask.getTaskDescription());
        jsonObject.addProperty("duration", subtask.getDuration());
        jsonObject.addProperty("startTime", subtask.getStartTime().format(formatter));
        jsonObject.addProperty("epicId", subtask.getEpicId());

        return jsonObject;
    }
}

