package render.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import render.Model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static final int VERTEX_TYPE_SEARCH = 0;
    public static final int TEXTURE_TYPE_SEARCH = 1;

    public static float[] parseMatrixFromObj(String path, int typeSearch) {
        float[] vertices = new float[0];

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

            List<String> matrixLines = new ArrayList<>();
            List<String> faceLines = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineSplit = line.split("\\s");

                switch (typeSearch) {
                    case VERTEX_TYPE_SEARCH:
                        if (lineSplit[0].equals("v"))
                            matrixLines.add(line);
                        else if (lineSplit[0].equals("f"))
                            faceLines.add(line);
                        break;
                    case TEXTURE_TYPE_SEARCH:
                        break;
                    default:
                        break;
                }
            }

            switch (typeSearch) {
                case VERTEX_TYPE_SEARCH:
                    vertices = new float[(faceLines.size() * 3) * 3];

                    int currentVertexWrite = 0;

                    for (String faceLine : faceLines) {
                        String[] faceLineSplit = faceLine.split("\\s");

                        for (int face = 1; face < faceLineSplit.length; face++) {
                            String[] faceBitSplit = faceLineSplit[face].split("/");
                            int vertexIndex = Integer.valueOf(String.valueOf(faceBitSplit[0])) - 1;
                            String matrixLine = matrixLines.get(vertexIndex);

                            String[] matrixLineSplit = matrixLine.split("\\s");

                            for (int vertex = 1; vertex < matrixLineSplit.length; vertex++) {
                                vertices[currentVertexWrite] = Float.valueOf(matrixLineSplit[vertex]);

                                currentVertexWrite++;
                            }
                        }
                    }

                    break;
                case TEXTURE_TYPE_SEARCH:
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vertices;
    }

    public static Model[] loadFromXml(String path) {
        Model[] modelArray = new Model[0];

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(path);

            NodeList models = document.getElementsByTagName("model");

            modelArray = new Model[models.getLength()];

            for (int node = 0; node < models.getLength(); node++) {
                Node model = models.item(node);

                NamedNodeMap attributes = model.getAttributes();

                String filePath = null;
                String name = null;
                Vector3 position = null;
                Vector3 rotation = null;
                Vector3 scale = null;

                for (int attribute = 0; attribute < attributes.getLength(); attribute++) {
                    Node attributeItem = attributes.item(attribute);
                    String attributeString = attributeItem.getNodeName();
                    String attributeContent = attributeItem.getTextContent();

                    switch (attributeString) {
                        case "file":
                            filePath = attributeContent;
                            break;
                        case "name":
                            name = attributeContent;
                            break;
                        case "position":
                            position = stringAsVector3(attributeContent);
                            break;
                        case "rotation":
                            rotation = stringAsVector3(attributeContent);
                            break;
                        case "scale":
                            scale = stringAsVector3(attributeContent);
                            break;
                        default:
                            break;
                    }
                }

                if (filePath == null) return modelArray;

                Model renderModel = new Model(Parser.parseMatrixFromObj(filePath, Parser.VERTEX_TYPE_SEARCH));
                if (name != null) renderModel.setName(name);
                if (position != null) renderModel.setPosition(position);
                if (rotation != null) renderModel.setRotation(rotation);
                if (scale != null) renderModel.setScale(scale);

                modelArray[node] = renderModel;
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return modelArray;
    }

    public static Vector3 stringAsVector3(String string) {
        Vector3 vector3;

        string = string.trim();

        String[] splitString = string.split(",");

        if (splitString.length <= 1) return null;

        float x = Float.valueOf(splitString[0]);
        float y = Float.valueOf(splitString[1]);
        float z = Float.valueOf(splitString[2]);

        vector3 = new Vector3(x, y, z);

        return vector3;
    }
}
