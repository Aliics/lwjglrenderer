package render;

import org.testng.annotations.Test;
import render.utils.Parser;

public class DisplayTest {
    @Test
    public void PrimaryTestCase() {
        Display display = new Display();

        display.setSettingsToPropertiesFile("src/test/resources/display_props_test.properties");
        display.saveToPropertiesOnClose("src/test/resources/display_props_test.properties");

        Model[] models = Parser.loadFromXml("src/test/resources/test_save.xml");

        display.addModels(models);

        while (true) {
            // this is here just to keep it alive :)
        }
    }
}