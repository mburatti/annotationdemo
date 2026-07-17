package com.example.annotationdemo;

import com.example.annotationdemo.util.LogIcon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogIconTest {

    @Test
    void eachLogIconShouldExposeASymbol() {
        for (LogIcon icon : LogIcon.values()) {
            assertNotNull(icon.getSymbol(), icon.name() + " should have a symbol");
            assertFalse(icon.getSymbol().isBlank(), icon.name() + " should have a non-empty symbol");
        }
    }

    @Test
    void errorIconSymbolShouldMatchExpectedEmoji() {
        assertEquals("❌", LogIcon.ERROR.getSymbol());
    }
}
