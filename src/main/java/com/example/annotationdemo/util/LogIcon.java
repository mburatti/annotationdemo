package com.example.annotationdemo.util;

public enum LogIcon {
    WARNING("⚠️"),
    ERROR("❌"),
    SUCCESS("✅"),
    INFO("ℹ️"),
    TIP("💡"),
    TOOL("🔧"),
    SEARCH("🔍"),
    NOTE("📌");

    private final String symbol;

    LogIcon(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
