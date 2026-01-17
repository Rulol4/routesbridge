package me.Rulol4.RoutesBridge;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RoomDataLoader {

    /*
    Original roomdata.json file from AtomX:
    https://github.com/DocilElm/Atomx/blob/main/api/roomdata.json
    */

    public static Map<String, String> load() {
        Map<String, String> map = new HashMap<>();

        try (InputStream stream = RoomDataLoader.class
                .getClassLoader()
                .getResourceAsStream("roomdata.json")) {

            if (stream == null) {
                throw new RuntimeException("roomdata.json not found in resources");
            }

            JsonArray arr = JsonParser.parseReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8)
            ).getAsJsonArray();

            for (JsonElement e : arr) {
                JsonObject obj = e.getAsJsonObject();
                String name = obj.get("name").getAsString();

                JsonArray ids = obj.getAsJsonArray("id");
                for (JsonElement id : ids) {
                    map.put(id.getAsString(), name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
