package renderer;

import org.testng.annotations.Test;
import utils.Parser;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class DisplayTest {

    @Test
    public void DisplayTest1() {
        float[] vertices = Parser.objTris("/home/aliics/IdeaProjects/fish.eyebrow/lwjglrenderer/src/test/resources/SwordTest.obj");

        Display display = new Display();

        display.setTitle("LWJGL ENGINE WINDOW");

        display.addVboRender(new VBORender(vertices));

        display.launch();

        // we only need the below so the test won't quit out

        long window = display.getWindow();

        while (!glfwWindowShouldClose(window)) {
            // basically don't close the app
        }
    }
}