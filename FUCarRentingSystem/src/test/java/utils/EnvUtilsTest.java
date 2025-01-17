package utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnvUtilsTest {
    private final String DB_URL_DOCKER = "jdbc:mysql://localhost:3307/snake_game_app";
    private Dotenv dotenv;

    @BeforeEach
    public void setUp() {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    @Test
    public void testGet() {
        String envValue = dotenv.get("DB_URL_DOCKER", System.getenv("DB_URL_DOCKER"));
        assertNotNull(envValue, "DB_URL_DOCKER environment variable should be set");
        assertEquals(DB_URL_DOCKER, envValue);
    }
}