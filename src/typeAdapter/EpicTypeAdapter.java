//package typeAdapter;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonSerializationContext;
//import com.google.gson.JsonSerializer;
//import model.Epic;
//
//import java.lang.reflect.Type;
//
//import static model.Task.formatter;
//
//public class EpicTypeAdapter implements JsonSerializer<Epic> {
//    @Override
//    public JsonElement serialize(Epic epic, Type typeOfSrc, JsonSerializationContext context) {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("type", (epic.getType()).toString());
//        jsonObject.addProperty("id", epic.getID());
//        jsonObject.addProperty("name", epic.getTaskName());
//        jsonObject.addProperty("description", epic.getTaskDescription());
//        jsonObject.addProperty("status", epic.getStatus().toString());
//        jsonObject.addProperty("duration", epic.getDuration());
//        jsonObject.addProperty("startTime", epic.getStartTime().format(formatter));
//        return jsonObject;
//    }
//}
