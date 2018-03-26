package render;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {
    private Thread main_thread;
    private long main_thread_id;

    private long w_id;

    private int w_width = 600;
    private int w_height = 480;

    private String w_title = "Window";

    private long w_monitor = 0;

    private int render_mode = IMMEDIATE_RENDER_MODE;

    private List<Model> model_renders;

    private List<Model> models_to_add;

    public Display() {
        init();

        main_thread = new Thread(this::loop);
        main_thread.start();

        main_thread_id = main_thread.getId();

        model_renders = new ArrayList<>();
        models_to_add = new ArrayList<>();
    }

    private void init() {
        if (!glfwInit()) return;

        w_id = glfwCreateWindow(w_width, w_height, w_title, w_monitor, 0);

        if (w_id == NULL) return;

        glfwShowWindow(w_id);
    }

    private void loop() {
        glfwMakeContextCurrent(w_id);
        createCapabilities();

        glClearColor(0.25f, 0.1f, 0.15f, 0);

        while (!glfwWindowShouldClose(w_id)) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (!models_to_add.isEmpty()) {
                model_renders.addAll(models_to_add);

                for (Model model : model_renders) {
                    model.load();
                }

                models_to_add.clear();
            }

            switch (render_mode) {
                case IMMEDIATE_RENDER_MODE:
                    for (Model model : model_renders) {
                        float[] vertices = model.getVertices();

                        glBegin(GL_TRIANGLES);

                        for (int i = 0; i < vertices.length; i+=3) {
                            glVertex3f(vertices[i], vertices[i + 1], vertices[i + 2]);
                        }

                        glEnd();
                    }

                    break;
                case VBO_RENDER_MODE:
                    for (Model model : model_renders) {
                        model.render();
                    }

                    break;
            }

            glfwSwapBuffers(w_id);
        }

        for (Model model : model_renders) {
            model.destroy();
        }

        glfwTerminate();
    }

    public void setFromPropertiesFile(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(path));

            setWidth(Integer.valueOf(properties.getProperty("width")));
            setHeight(Integer.valueOf(properties.getProperty("height")));
            setTitle(properties.getProperty("title"));
            setRenderMode(Integer.valueOf(properties.getProperty("render_mode")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    <editor-fold desc="getters and setters">
    public Thread getLoopThread() {
        return main_thread;
    }

    public long getLoopThreadId() {
        return main_thread_id;
    }

    public long getWindow() {
        return w_id;
    }

    public int getWidth() {
        return w_width;
    }

    public void setWidth(int width) {
        w_width = width;

        glfwSetWindowSize(w_id, w_width, w_height);
    }

    public int getHeight() {
        return w_height;
    }

    public void setHeight(int height) {
        w_height = height;

        glfwSetWindowSize(w_id, w_width, w_height);
    }

    public String getTitle() {
        return w_title;
    }

    public void setTitle(String title) {
        w_title = title;

        glfwSetWindowTitle(w_id, title);
    }

    public long getMonitor() {
        return w_monitor;
    }

    public int getRenderMode() {
        return render_mode;
    }

    public void setRenderMode(int renderMode) {
        render_mode = renderMode;
    }

    public List<Model> getModels() {
        return model_renders;
    }

    public Model getModel(int index) {
        return model_renders.get(index);
    }

    public void addModel(Model model) {
        models_to_add.add(model);
    }
//    </editor-fold>

//    <editor-fold desc="constants">
    public static final int IMMEDIATE_RENDER_MODE = 0;
    public static final int VBO_RENDER_MODE = 1;
//    </editor-fold>
}
