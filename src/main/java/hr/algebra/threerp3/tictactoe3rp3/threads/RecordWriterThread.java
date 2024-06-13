package hr.algebra.threerp3.tictactoe3rp3.threads;

import hr.algebra.threerp3.tictactoe3rp3.controller.GameController;
import hr.algebra.threerp3.tictactoe3rp3.utils.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class RecordWriterThread implements Runnable {
    @Override
    public void run() {
        while (true) {
            save();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
        }
    }

    public synchronized void save() {
        while (Configuration.xmlUsed) {
            try {
                System.out.println("save failed");
                wait();
            } catch (InterruptedException ex) {

            }
        }

        Configuration.xmlUsed = true;

        try {
            DocumentBuilderFactory documentBuilderFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder
                    = documentBuilderFactory.newDocumentBuilder();
            Document xmlDocument = documentBuilder.newDocument();
            Element rootElement = xmlDocument.createElement("WinRecord");
            xmlDocument.appendChild(rootElement);

            Element mintElement = xmlDocument.createElement("Mint");
            Node mintTextNode = xmlDocument.createTextNode(String.valueOf(
                    GameController.playerOneDetails.getNumberOfWins()));
            mintElement.appendChild(mintTextNode);
            rootElement.appendChild(mintElement);

            Element lavenderElement = xmlDocument.createElement("Lavender");
            Node lavenderTextNode = xmlDocument.createTextNode(String.valueOf(
                    GameController.playerTwoDetails.getNumberOfWins()));
            lavenderElement.appendChild(lavenderTextNode);
            rootElement.appendChild(lavenderElement);

            Transformer transformer
                    = TransformerFactory.newInstance().newTransformer();

            Source xmlSource = new DOMSource(xmlDocument);
            Result xmlResult = new StreamResult(new File(FileUtils.XML_RECORD_FILE_NAME));

            transformer.transform(xmlSource, xmlResult);
            System.out.println("Save happened");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Configuration.xmlUsed = false;

        notifyAll();
    }
}
