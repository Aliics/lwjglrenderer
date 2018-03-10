package renderer;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class Input {

    private static List<Integer> keys;

    public static void setup(long window) {
        glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scanCode, int action, int mods) {
                switch (action) {
                    case KEY_DOWN:
                        break;
                    case KEY_UP:
                        break;
                }
            }
        });
    }

    public static void pollEvents() {
        glfwPollEvents();
    }

    // <editor-fold desc="global constants">

    public static final int KEY_DOWN = 1;
    public static final int KEY_UP = 0;

    // </editor-fold>
}
