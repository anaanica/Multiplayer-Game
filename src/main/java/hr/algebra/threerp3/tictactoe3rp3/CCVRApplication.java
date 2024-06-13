package hr.algebra.threerp3.tictactoe3rp3;

import hr.algebra.threerp3.tictactoe3rp3.chat.service.RemoteChatService;
import hr.algebra.threerp3.tictactoe3rp3.chat.service.RemoteChatServiceImpl;
import hr.algebra.threerp3.tictactoe3rp3.controller.GameController;
import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import hr.algebra.threerp3.tictactoe3rp3.model.*;
import hr.algebra.threerp3.tictactoe3rp3.utils.ConfigurationReader;
import hr.algebra.threerp3.tictactoe3rp3.utils.GameStateUtils;
import hr.algebra.threerp3.tictactoe3rp3.utils.SceneUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

@Author(name = "Ana")
public class CCVRApplication extends Application {
    public static UserRole activeUserRole;
    private static Stage mainStage;
    private static PlayerDetails playerOneDetails;
    private static PlayerDetails playerTwoDetails;
    public static Boolean lavenderAvailable = false;
    public static Boolean mintAvailable = false;

    @Override
    public void start(Stage stage) throws IOException {
        playerOneDetails = new PlayerDetails("mint");
        playerTwoDetails = new PlayerDetails("lavender");
        mainStage = stage;
        SceneUtils.setNewSceneToStage("view/game-screen.fxml",
                "country city village river " +
                        activeUserRole.name().toLowerCase());
    }

    public static PlayerDetails getPlayerOneDetails() {
        return playerOneDetails;
    }

    public static PlayerDetails getPlayerTwoDetails() {
        return playerTwoDetails;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    private static void startMint() {
        acceptRequestsOnServer();
    }

    private static void acceptRequestsOnServer() {
        Integer serverPort = ConfigurationReader.readIntegerConfigurationValue(ConfigurationKey.SERVER_PORT);
        try (ServerSocket serverSocket = new ServerSocket(serverPort)){
            System.err.println("Server listening on port: " + serverSocket.getLocalPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.err.println("Client connected from port: " + clientSocket.getPort());
                //lavenderAvailable = true;
                Platform.runLater(() ->  processSerializableClient(clientSocket));
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startLavender() {
        acceptRequestsOnClient();
    }

    private static void acceptRequestsOnClient() {
        Integer clientPort = ConfigurationReader.readIntegerConfigurationValue(ConfigurationKey.CLIENT_PORT);
        try (ServerSocket serverSocket = new ServerSocket(clientPort)){
            System.err.println("Server listening on port: " + serverSocket.getLocalPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.err.println("Client connected from port: " + clientSocket.getPort());
                //mintAvailable = true;
                Platform.runLater(() ->  processSerializableClient(clientSocket));
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processSerializableClient(Socket clientSocket) {
        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());){
            GameState gameState = (GameState)ois.readObject();
            GameStateUtils.loadGameState(gameState);
            System.out.println("Game state received");
            oos.writeObject("Confirmation");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args[0].equals(UserRole.BLUE.name())) {
            activeUserRole = UserRole.BLUE;
            launch();
        } else {
            new Thread(Application::launch).start();

            String userRoleLoggedIn = args[0];
            Boolean userLoggedIn = false;
            activeUserRole = UserRole.LAVENDER;

            for (UserRole userRole : UserRole.values()) {
                if (userRole.name().equals(userRoleLoggedIn)) {
                    userLoggedIn = true;
                    activeUserRole = userRole;
                }
            }

            if (userLoggedIn) {
                if (UserRole.MINT.name().equals(activeUserRole.name())) {
                    startMint();
                } else {
                    startLavender();
                }
            }
        }
    }

    public static Boolean isLavenderAvailable() {
        return lavenderAvailable;
    }

    public static Boolean isMintAvailable() {
        return mintAvailable;
    }

}