package renderer;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Display {
    private long window;

    private String title = "Window";
    private int width = 854;
    private int height = 480;
    private int resizable = GL_TRUE;
    private int visible = GL_TRUE;

    public Display() {
        paint();

        new Thread(this::loop).start();
    }

    private void paint() {
        if (!glfwInit()) return;

        glfwDestroyWindow(window);

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, resizable);
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);

        window = glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) return;

        glfwShowWindow(visible == GL_TRUE ? window : 0);

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(
                window,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2
        );
    }

    private void loop() {
        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        glClearColor(0.19f, 0.03f, 0.15f, 0f);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(window);

            glfwPollEvents();
        }

        glfwTerminate();
    }



    /* GETTERS AND SETTERS */



    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;

        paint();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

        paint();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;

        paint();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;

        paint();
    }

    public int isResizable() {
        return resizable;
    }

    public void setResizable(int resizable) {
        this.resizable = resizable;

        paint();
    }

    public int isVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;

        paint();
    }
}
