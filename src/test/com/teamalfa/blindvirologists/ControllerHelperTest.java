package test.com.teamalfa.blindvirologists;

import main.com.teamalfa.blindvirologists.ControllerHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerHelperTest {

    @Test
    void textMatchesRegex(){
        assertTrue(ControllerHelper.checkCorrectFormat("n\\d+a\\d+", "n13a9"));
        assertTrue(ControllerHelper.checkCorrectFormat("n\\d+a\\d+", "n1133a129"));
        assertFalse(ControllerHelper.checkCorrectFormat("n\\d+a\\d+", "na10"));
        assertFalse(ControllerHelper.checkCorrectFormat("n\\d+a\\d+", "n10a10n10a10"));
    }
}