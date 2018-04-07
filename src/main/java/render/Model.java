package render;

import org.lwjgl.BufferUtils;
import render.utils.Vector3;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Model {
    private float[] vertices;
    private int vaoId;
    private int vboId;

    private int renderMode = GL_TRIANGLES;
    private int matrixSize = 3;

    private String name = "Model";
    private Vector3 position = Vector3.VECTOR3_ZERO;
    private Vector3 rotation = Vector3.VECTOR3_ZERO;
    private Vector3 scale = Vector3.VECTOR3_ONE;

    public Model(float[] vertices) {
        this.vertices = vertices;
    }

    void load() {
        for (int vertex = 0; vertex < vertices.length; vertex += 3) {
            vertices[vertex] = (vertices[vertex] * scale.getX()) + position.getX();
            vertices[vertex + 1] = (vertices[vertex + 1] * scale.getY()) + position.getY();
            vertices[vertex + 2] = (vertices[vertex + 2] * scale.getZ()) + position.getZ();
        }

        try {
            FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
            vertexBuffer.put(vertices);
            vertexBuffer.flip();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            vboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

            glVertexAttribPointer(0, matrixSize, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glBindVertexArray(0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    void render() {
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        glDrawArrays(renderMode, 0, vertices.length);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    void destroy() {
        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);

        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public float[] getVertices() {
        return vertices;
    }

    public int getRenderMode() {
        return renderMode;
    }

    public void setRenderMode(int renderMode) {
        this.renderMode = renderMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public void setMatrixSize(int matrixSize) {
        this.matrixSize = matrixSize;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public Vector3 getScale() {
        return scale;
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
    }
}
