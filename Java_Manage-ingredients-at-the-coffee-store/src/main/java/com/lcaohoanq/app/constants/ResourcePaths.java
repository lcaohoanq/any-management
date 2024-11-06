package com.lcaohoanq.app.constants;

import java.util.Objects;

public class ResourcePaths {
    public static final String URL_INGREDIENT_DAT = getFilePath("/data/Ingredients.dat");
    public static final String URL_MENU_DAT = getFilePath("/data/Menu.dat");
    public static final String URL_ORDER_DAT = getFilePath("/data/Order.dat");

    private static String getFilePath(String resourcePath) {
        try {
            return Objects.requireNonNull(ResourcePaths.class.getResource(resourcePath)).getPath();
        } catch (NullPointerException e) {
            throw new RuntimeException("File not found: " + resourcePath);
        }
    }
}