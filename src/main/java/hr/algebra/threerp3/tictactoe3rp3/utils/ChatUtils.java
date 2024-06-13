package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.CCVRApplication;
import hr.algebra.threerp3.tictactoe3rp3.controller.GameController;
import javafx.scene.control.TextArea;

import java.rmi.RemoteException;
import java.util.List;

public final class ChatUtils {
    private ChatUtils() {
    }

    public static void sendChatMessage(String chatMessage) {
        if (!chatMessage.isBlank()) {
            try {
                GameController.remoteChatService.sendMessage(CCVRApplication.activeUserRole.name().toLowerCase() + ": " + chatMessage);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void refreshChatMessages(TextArea taChatMessages) {
        taChatMessages.clear();
        try {
            List<String> chatMessages = GameController.remoteChatService.getAllChatMessages();

            for(String message : chatMessages) {
                taChatMessages.appendText(message + "\n");
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
