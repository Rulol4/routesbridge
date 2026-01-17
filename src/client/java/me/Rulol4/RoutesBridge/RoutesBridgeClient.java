package me.Rulol4.RoutesBridge;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RoutesBridgeClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(
					ClientCommandManager.literal("route")
							.executes(ctx -> {
								String room = getCurrentRoomName();

								//if (MinecraftClient.getInstance().player == null) return 1; // Used for debugging

								if (room == null) {
                                    //MinecraftClient.getInstance().player.sendMessage(Text.literal("No room found at this time."), false); // Used for debugging
									return 1;
								}

								//MinecraftClient.getInstance().player.sendMessage(Text.literal("Current room: " + room), false); // Used for debugging
								startTcpServer(room.toLowerCase().replace("dino site", "dino dig site").replace("black flag", "black flags"));

								try {
                                    new ProcessBuilder("mods/routes/RoutesMod.exe").start();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                return 1;
							})
			);
		});

		SidebarTracker.init();
		System.out.println("[RoutesBridge] Loaded successfully");
	}

	public static String getCurrentRoomName() {
		return SidebarTracker.getCurrentRoomName();
	}

	public void startTcpServer(String text) {
		new Thread(() -> {
			try (ServerSocket server = new ServerSocket(8080)) {
				Socket client = server.accept();
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);

				out.println(text);
				out.println("ender_pearl");
				out.flush();

				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}