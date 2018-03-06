package renderer;

import org.testng.annotations.Test;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class DisplayTest {

    @Test
    public void DisplayTest1() {
        Display display = new Display();

        display.setTitle("LWJGL Display Test");

        try {
            new Thread(() -> {
                display.addVboRender(new VBORender(new float[] {
                        0.0f, 0.8f,
                        -0.8f, -0.8f,
                        0.8f, -0.8f
                }));
            }).join(display.getLoopThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // we only need the below so the test won't quit out

        long window = display.getWindow();

        while (!glfwWindowShouldClose(window)) {
            // basically don't close the app
        }
    }
}