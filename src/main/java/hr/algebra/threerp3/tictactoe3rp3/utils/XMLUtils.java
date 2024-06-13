package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.controller.GameController;
import hr.algebra.threerp3.tictactoe3rp3.model.GameMove;
import hr.algebra.threerp3.tictactoe3rp3.model.PlayerDetails;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_COLS;

public final class XMLUtils {
    private XMLUtils() {}
    public static void saveXML() {
        try {
            DocumentBuilderFactory documentBuilderFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder
                    = documentBuilderFactory.newDocumentBuilder();
            Document xmlDocument = documentBuilder.newDocument();
            Element rootElement = xmlDocument.createElement("Game");
            xmlDocument.appendChild(rootElement);
            for (int i = 0; i < GameMovesUtils.lastGameMoves.size(); i++) {
                Element gameMoveElement = xmlDocument.createElement("GameMove");
                rootElement.appendChild(gameMoveElement);

                Element playerDetailsElement = xmlDocument.createElement("PlayerDetails");
                playerDetailsElement.setAttribute("id",
                        String.valueOf(GameMovesUtils.lastGameMoves.get(i).getPlayerDetails().getPlayerId()));
                gameMoveElement.appendChild(playerDetailsElement);
                rootElement.appendChild(gameMoveElement);

                Element playerNameElement = xmlDocument.createElement("PlayerName");
                Node playerNameTextNode = xmlDocument.createTextNode(
                        GameMovesUtils.lastGameMoves.get(i).getPlayerDetails().getPlayerName());
                playerNameElement.appendChild(playerNameTextNode);
                playerDetailsElement.appendChild(playerNameElement);
                gameMoveElement.appendChild(playerDetailsElement);
                rootElement.appendChild(gameMoveElement);

                Element countryElement = xmlDocument.createElement("Country");
                Node countryTextNode = xmlDocument.createTextNode(
                        GameMovesUtils.lastGameMoves.get(i).getGeoEntities().get(0));
                countryElement.appendChild(countryTextNode);
                gameMoveElement.appendChild(countryElement);
                rootElement.appendChild(gameMoveElement);

                Element cityElement = xmlDocument.createElement("City");
                Node cityTextNode = xmlDocument.createTextNode(
                        GameMovesUtils.lastGameMoves.get(i).getGeoEntities().get(1));
                cityElement.appendChild(cityTextNode);
                gameMoveElement.appendChild(cityElement);
                rootElement.appendChild(gameMoveElement);

                Element villageElement = xmlDocument.createElement("Village");
                Node villageTextNode = xmlDocument.createTextNode(
                        GameMovesUtils.lastGameMoves.get(i).getGeoEntities().get(2));
                villageElement.appendChild(villageTextNode);
                gameMoveElement.appendChild(villageElement);
                rootElement.appendChild(gameMoveElement);

                Element riverElement = xmlDocument.createElement("River");
                Node riverTextNode = xmlDocument.createTextNode(
                        GameMovesUtils.lastGameMoves.get(i).getGeoEntities().get(3));
                riverElement.appendChild(riverTextNode);
                gameMoveElement.appendChild(riverElement);
                rootElement.appendChild(gameMoveElement);

                Element timestampElement = xmlDocument.createElement("Timestamp");
                Node timestampTextNode = xmlDocument.createTextNode(
                        GameMovesUtils.lastGameMoves.get(i).getLocalDateTime().toString());
                timestampElement.appendChild(timestampTextNode);
                gameMoveElement.appendChild(timestampElement);
                rootElement.appendChild(gameMoveElement);

                Transformer transformer
                        = TransformerFactory.newInstance().newTransformer();

                Source xmlSource = new DOMSource(xmlDocument);
                Result xmlResult = new StreamResult(new File(FileUtils.XML_FILE_NAME));

                transformer.transform(xmlSource, xmlResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadXML() {
        try {
            File movesStream = new File(FileUtils.XML_FILE_NAME);
            DocumentBuilder parser =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDocument = parser.parse(movesStream);
            NodeList nodeList = xmlDocument.getElementsByTagName("GameMove");
            for(int i = 0; i < nodeList.getLength(); i++) {
                Node gameMoveNode = nodeList.item(i);
                if(gameMoveNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element gameMoveElement = (Element) gameMoveNode;

                    Element playerDetailsElement = (Element) gameMoveElement
                            .getElementsByTagName("PlayerDetails")
                            .item(0);

                    Integer playerId = Integer.parseInt(playerDetailsElement.getAttribute("id"));

                    String country = gameMoveElement.getElementsByTagName("Country").item(0).getTextContent();
                    String city = gameMoveElement.getElementsByTagName("City").item(0).getTextContent();
                    String village = gameMoveElement.getElementsByTagName("Village").item(0).getTextContent();
                    String river = gameMoveElement.getElementsByTagName("River").item(0).getTextContent();

                    List<String> geoEntities = Arrays.asList(country, city, village, river);

                    for (int j = 0; j < NUM_OF_COLS; j++) {
                            GameController.gridBoard[playerId][j].setText(geoEntities.get(j));
                    }
                }
            }
            DialogUtils.printXMLLoadSuccessfull();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
