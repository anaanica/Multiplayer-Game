package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.chat.service.RemoteChatService;
import hr.algebra.threerp3.tictactoe3rp3.chat.service.RemoteChatServiceImpl;
import hr.algebra.threerp3.tictactoe3rp3.controller.GameController;
import hr.algebra.threerp3.tictactoe3rp3.model.ConfigurationKey;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public final class RmiUtils {
    private RmiUtils() {
    }

    public static void startRmiRemoteChatServer() {
        Integer rmiPort = ConfigurationReader.readIntegerConfigurationValue(ConfigurationKey.RMI_PORT);
        Integer randomPortHint = ConfigurationReader.readIntegerConfigurationValue(ConfigurationKey.RANDOM_PORT_HINT);
        try {
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            GameController.remoteChatService = new RemoteChatServiceImpl();
            RemoteChatService skeleton = (RemoteChatService) UnicastRemoteObject.exportObject(
                    GameController.remoteChatService, randomPortHint);
            registry.rebind(RemoteChatService.REMOTE_CHAT_OBJECT_NAME, skeleton);
            System.err.println("Object registered in RMI registry");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void startRmiRemoteChatClient() {
        Integer rmiPort = ConfigurationReader.readIntegerConfigurationValue(ConfigurationKey.RMI_PORT);
        String host = ConfigurationReader.readStringConfigurationValue(ConfigurationKey.HOST);
        try {
            Registry registry = LocateRegistry.getRegistry(host, rmiPort);
            GameController.remoteChatService = (RemoteChatService) registry.lookup(
                    RemoteChatService.REMOTE_CHAT_OBJECT_NAME);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
