package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static float[] objTris(String path) {
        float[] vertices = new float[0];

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

            List<String> verticesList = new ArrayList<>();
            List<String> verticeStrings = new ArrayList<>();

            String line;
            int currentFace = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineSplit = line.split("\\s");

                switch (lineSplit[0]) {
                    case "v":
                        verticeStrings.add(line);
                        break;
                    case "f":
                        for (int i = 1; i < lineSplit.length; i++) {
                            int currentVertice = Integer.valueOf(String.valueOf(lineSplit[i].charAt(0))) - 1;
                            verticesList.add(verticeStrings.get(currentVertice));
                        }
                        currentFace++;
                        break;
                }
            }

            vertices = new float[verticesList.size() * 3];

            int currentVertex = 0;
            for (String verticeString : verticesList) {
                String[] splitVertices = verticeString.split("\\s");

                for (int v = 1; v < splitVertices.length; v++) {
                    vertices[currentVertex] = Float.valueOf(splitVertices[v]);
                    currentVertex++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vertices;
    }
}
