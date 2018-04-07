package render;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {
    public static final int VBO_RENDER_MODE = 0;
    public static final int IMMEDIATE_RENDER_MODE = 1;

    private Thread mainThread;
    private long mainThreadId;

    private long window;

    private int width = 600;
    private int height = 480;
    private String title = "Window";
    private int renderMode = VBO_RENDER_MODE;

    private long monitor = 0;

    private String pathToSave = null;

    private List<Model> modelRenders;

    private List<Model> modelsToAdd;

    public Display() {
        init();

        mainThread = new Thread(this::loop);
        mainThread.start();

        mainThreadId = mainThread.getId();

        modelRenders = new ArrayList<>();
        modelsToAdd = new ArrayList<>();
    }

    private void init() {
        if (!glfwInit()) return;

        window = glfwCreateWindow(width, height, title, monitor, 0);

        if (window == NULL) return;

        glfwShowWindow(window);
    }

    private void loop() {
        glfwMakeContextCurrent(window);
        createCapabilities();

        glClearColor(0.25f, 0.1f, 0.15f, 0);

        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                Display.this.width = width;
                Display.this.height = height;
            }
        });

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (!modelsToAdd.isEmpty()) {
                modelRenders.addAll(modelsToAdd);

                for (Model model : modelRenders) {
                    model.load();
                }

                modelsToAdd.clear();
            }

            switch (renderMode) {
                case VBO_RENDER_MODE:
                    for (Model model : modelRenders) {
                        model.render();
                    }

                    break;

                case IMMEDIATE_RENDER_MODE:
                    for (Model model : modelRenders) {
                        float[] vertices = model.getVertices();

                        glBegin(GL_TRIANGLES);

                        for (int vertex = 0; vertex < vertices.length; vertex += 3) {
                            glVertex3f(vertices[vertex], vertices[vertex + 1], vertices[vertex + 2]);
                        }

                        glEnd();
                    }

                    break;
            }

            glfwSwapBuffers(window);
        }

        for (Model model : modelRenders) {
            model.destroy();
        }

        if (pathToSave != null) {
            Properties properties = new Properties();
            try {
                properties.load(new FileReader(pathToSave));

                properties.setProperty("width", String.valueOf(width));
                properties.setProperty("height", String.valueOf(height));
                properties.setProperty("title", title);
                properties.setProperty("render_mode", String.valueOf(renderMode));

                properties.store(new FileWriter(pathToSave), "SETTINGS WERE SAVED ON PROGRAM CLOSE");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        glfwTerminate();
    }

    public void setSettingsToPropertiesFile(String path) {
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

    public void saveToPropertiesOnClose(String path) {
        pathToSave = path;
    }

    public Thread getLoopThread() {
        return mainThread;
    }

    public long getLoopThreadId() {
        return mainThreadId;
    }

    public long getWindow() {
        return window;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;

        glfwSetWindowSize(window, this.width, height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;

        glfwSetWindowSize(window, width, this.height);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

        glfwSetWindowTitle(window, title);
    }

    public long getMonitor() {
        return monitor;
    }

    public int getRenderMode() {
        return renderMode;
    }

    public void setRenderMode(int renderMode) {
        this.renderMode = renderMode;
    }

    public List<Model> getModels() {
        return modelRenders;
    }

    public Model getModel(int index) {
        return modelRenders.get(index);
    }

    public void addModel(Model model) {
        modelsToAdd.add(model);
    }

    public void addModels(Model[] models) {
        for (Model model : models) {
            addModel(model);
        }
    }
}
