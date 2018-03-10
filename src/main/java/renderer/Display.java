package renderer;

import org.lwjgl.glfw.GLFWVidMode;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

public class Display {
    private long window;

    private String title = "Window";
    private int width = 854;
    private int height = 480;
    private int resizable = GL_TRUE;
    private int visible = GL_TRUE;
    private List<VBORender> vboRenders;

    public Display() {
        paint();
    }

    private void paint() {
        if (!glfwInit()) return;

        vboRenders = new ArrayList<>();

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

        glfwMakeContextCurrent(window);

        createCapabilities();

        Input.setup(window);
    }

    /*
        We need to make this the final method to be called after defining everything about the window.
        This is due to the fact that concurrency with lwjgl's contexts is a little janky.
     */
    public void launch() {
        glClearColor(0.19f, 0.03f, 0.15f, 0f);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

            for (VBORender vboRender : vboRenders) {
                vboRender.draw();
            }

            Input.pollEvents();

            glfwSwapBuffers(window);
        }

        for (VBORender vboRender : vboRenders) {
            vboRender.cleanup();
        }

        glfwTerminate();
        System.exit(0);
    }


    // <editor-fold desc="getters-and-setters">


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

    public List<VBORender> getVboRenders() {
        return vboRenders;
    }

    public void addVboRender(VBORender vboRender) {
        vboRenders.add(vboRender);
    }

    // </editor-fold>
}
