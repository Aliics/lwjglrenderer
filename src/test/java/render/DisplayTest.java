package render;

import org.testng.annotations.Test;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class DisplayTest {
    @Test
    public void PrimaryTestCase() {
        Model model = new Model(new float[] {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
        });

        Display display = new Display();

        display.setFromPropertiesFile("src/test/resources/display_properties.properties");

        display.setRenderMode(Display.VBO_RENDER_MODE);

        display.addModel(model);

//        <editor-fold>
        while (!glfwWindowShouldClose(display.getWindow())) {
            // this is here just to keep it alive :)
        }
//        </editor-fold>
    }
}