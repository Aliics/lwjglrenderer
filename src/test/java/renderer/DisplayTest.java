package renderer;

import org.testng.annotations.Test;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class DisplayTest {

    @Test
    public void DisplayTest1() {
        Display display = new Display();

        display.setTitle("LWJGL Display Test");

        // we only need the below so the test won't quit out

        long window = display.getWindow();



        while (!glfwWindowShouldClose(window)) {
            // basically don't close the app
        }
    }
}