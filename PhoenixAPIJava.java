package com.mycompany.phoenixapijava;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;

/**
 *
 * @author Koretta
 */
public class PhoenixAPIJava {

    public static void main(String[] args) {
        // Put the port from the bot title, it should look something like
        // [Lv 99.(+80) CharacterName] - Phoenix Bot: 123123
        int port = 61258;

        try {
            Api api = new Api(port);
            while (api.isWorking()) {
                if (!api.isEmpty()) {
                    String msg = api.getMessage();
                    
                    JsonObject jsonMsg = new Gson().fromJson(msg, JsonObject.class);

                    int messageType = jsonMsg.get("type").getAsInt();

                    if (messageType == Api.Type.packet_send.ordinal()) {
                        System.out.println("[SEND]: " + jsonMsg.get("packet").getAsString());
                    } else if (messageType == Api.Type.packet_recv.ordinal()) {
                        System.out.println("[RECV]: " + jsonMsg.get("packet").getAsString());
                    }
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // Handle InterruptedException if needed
                        e.printStackTrace();
                    }
                }
            }
            api.close();
        } catch (IOException e) {
            // Handle IOException if needed
            e.printStackTrace();
        }
    }
}
