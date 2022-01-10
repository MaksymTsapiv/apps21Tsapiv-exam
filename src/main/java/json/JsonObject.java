package json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class JsonObject extends Json {
    private final HashMap<String, Json> jsonPairs = new HashMap<>();

    public JsonObject(JsonPair... jsonPairs) {
        for (JsonPair pair : jsonPairs) {
            this.jsonPairs.put(pair.key, pair.value);
        }
    }

    @Override
    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, Json>> jsonIterator = jsonPairs.entrySet().iterator();
        while (jsonIterator.hasNext()) {
            Map.Entry<String, Json> pair = jsonIterator.next();
            JsonPair jsonPair = new JsonPair(pair.getKey(), pair.getValue());
            stringBuilder.append("'").append(jsonPair.key).append("': ").append(jsonPair.value.toJson());
            if (jsonIterator.hasNext())
                stringBuilder.append(", ");
        }
        return "{" + stringBuilder + "}";
    }

    public void add(JsonPair jsonPair) {
        if (jsonPairs.containsKey(jsonPair.key)) {
            jsonPairs.replace(jsonPair.key, jsonPair.value);
        } else {
            jsonPairs.put(jsonPair.key, jsonPair.value);
        }
    }

    public Json find(String name) {
        return jsonPairs.get(name);
    }

    public JsonObject projection(String... names) {
        JsonObject jsonObject = new JsonObject();
        for (String name : names) {
            Json value = find(name);
            if (value != null) {
                jsonObject.add(new JsonPair(name, value));
            }
        }
        return jsonObject;
    }
}