package typeAdapter;

import com.google.gson.*;
import model.Epic;
import model.Status;
import model.Subtask;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EpicTypeAdapter implements JsonSerializer<Epic>, JsonDeserializer<Epic> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final SubtaskAdapter subtaskAdapter = new SubtaskAdapter();

    @Override
    public JsonElement serialize(Epic epic, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", epic.getId());
        jsonObject.addProperty("name", epic.getTaskName());
        jsonObject.addProperty("description", epic.getTaskDescription());
        jsonObject.addProperty("status", epic.getStatus().toString());
        jsonObject.addProperty("duration", epic.getDuration());
        jsonObject.addProperty("startTime", epic.getStartTime().format(dateTimeFormatter));
        jsonObject.addProperty("endTime", epic.getEndTime() != null ? epic.getEndTime().format(dateTimeFormatter) : null);

        JsonArray subTasksArray = new JsonArray();
        for (Subtask subtask : epic.getSubTasks()) {
            // Использование сериализатора подзадач
            JsonElement subtaskJson = context.serialize(subtask, Subtask.class);
            subTasksArray.add(subtaskJson);
        }
        jsonObject.add("subTasks", subTasksArray);

        return jsonObject;
    }

    @Override
    public Epic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        String statusString = jsonObject.get("status").getAsString();
        Status status = Status.valueOf(statusString);

        long durationMinutes = jsonObject.get("duration").getAsLong();
        Duration duration = Duration.ofMinutes(durationMinutes);

        LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(), dateTimeFormatter);
        LocalDateTime endTime = jsonObject.has("endTime") && !jsonObject.get("endTime").isJsonNull()
                ? LocalDateTime.parse(jsonObject.get("endTime").getAsString(), dateTimeFormatter)
                : null;

        Epic epic = new Epic(status, name, description, duration, startTime, id);
        epic.setEndTime(endTime != null ? endTime : LocalDateTime.now()); // set the end time if provided

        // Deserialize subtasks
        JsonArray subTasksArray = jsonObject.getAsJsonArray("subTasks");
        List<Subtask> subTasks = new ArrayList<>();
        for (JsonElement subtaskElem : subTasksArray) {
            // Используем контекст для десериализации подзадач
            Subtask subtask = context.deserialize(subtaskElem, Subtask.class);
            subTasks.add(subtask);
        }
        epic.getSubTasks().addAll(subTasks);

        return epic;
    }
}

