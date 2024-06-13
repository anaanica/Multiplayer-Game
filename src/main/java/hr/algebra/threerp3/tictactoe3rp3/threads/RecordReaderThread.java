package hr.algebra.threerp3.tictactoe3rp3.threads;

import hr.algebra.threerp3.tictactoe3rp3.utils.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class RecordReaderThread implements Runnable{
    @Override
    public void run() {
        while (true) {
            load();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
        }
    }

    public synchronized void load() {
        while (Configuration.xmlUsed) {
            try {
                System.out.println("load failed");
                wait();
            } catch (InterruptedException ex) {

            }
        }

        Configuration.xmlUsed = true;

        try {
            File movesStream = new File(FileUtils.XML_RECORD_FILE_NAME);
            DocumentBuilder parser =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDocument = parser.parse(movesStream);
            Element rootElement = xmlDocument.getDocumentElement();

            Node mintNode = rootElement.getElementsByTagName("Mint").item(0);
            Configuration.mintValue = mintNode.getTextContent();
            System.out.println(Configuration.mintValue);

            Node lavenderNode = rootElement.getElementsByTagName("Lavender").item(0);
            Configuration.lavenderValue = lavenderNode.getTextContent();
            System.out.println(Configuration.lavenderValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Configuration.xmlUsed = false;

        notifyAll();
    }
}
