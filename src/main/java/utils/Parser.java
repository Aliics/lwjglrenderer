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
            List<String> vertexStrings = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineSplit = line.split("\\s");

                switch (lineSplit[0]) {
                    case "v":
                        vertexStrings.add(line);
                        break;
                    case "f":
                        for (int i = 1; i < lineSplit.length; i++) {
                            String[] vertexSplit = lineSplit[i].split("//");
                            int currentVertex = Integer.valueOf(String.valueOf(vertexSplit[0])) - 1;
                            verticesList.add(vertexStrings.get(currentVertex));
                        }
                        break;
                }
            }

            vertices = new float[verticesList.size() * 3];

            int currentVertex = 0;
            for (String vertexString : verticesList) {
                String[] splitVertices = vertexString.split("\\s");

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
