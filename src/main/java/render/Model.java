package render;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Model {
    private float[] vertices;
    private int vao_id;
    private int vbo_id;

    private int render_mode = GL_TRIANGLES;

    public Model(float[] vertices) {
        this.vertices = vertices;
    }

    void load() {
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices);
        vertexBuffer.flip();

        vao_id = glGenVertexArrays();
        glBindVertexArray(vao_id);

        vbo_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);
    }

    void render() {
        glBindVertexArray(vao_id);
        glEnableVertexAttribArray(0);

        glDrawArrays(render_mode, 0, vertices.length);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    void destroy() {
        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vbo_id);

        glBindVertexArray(0);
        glDeleteVertexArrays(vao_id);
    }

//    <editor-fold desc="getters and setters">
    public float[] getVertices() {
        return vertices;
    }

    public int getRenderMode() {
        return render_mode;
    }

    public void setRenderMode(int renderMode) {
        this.render_mode = renderMode;
    }
//    </editor-fold>
}
