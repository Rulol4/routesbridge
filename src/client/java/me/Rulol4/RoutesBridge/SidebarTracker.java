package me.Rulol4.RoutesBridge;

import net.minecraft.client.MinecraftClient;

import net.minecraft.text.Text;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SidebarTracker {

    public static final Pattern ROOM_ID_PATTERN = Pattern.compile("^\\d{2}/\\d{2}/\\d{2} \\w+ ([-\\d,]+)$");
    private static String currentRoomId;

    static Map<String, String> roomIdToName;

    public static void init() {
        roomIdToName = RoomDataLoader.load();
        currentRoomId = "";
    }

    public static void capture(Text text) {
        String line = text.getString().replaceAll("§.", "");

        Matcher m = ROOM_ID_PATTERN.matcher(line);
        if (m.matches() && MinecraftClient.getInstance().player != null) {
            currentRoomId = m.group(1).trim();
        }
    }

    public static String getCurrentRoomName() {
        if (currentRoomId == null) return null;
        return roomIdToName.getOrDefault(currentRoomId, null);
    }
}


