

import edu.eci.arep.microspringboot.HTTPServer;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.file.Files;
public class HttpServerTest {

    private static Thread serverThread;

    /**
     * test that method MimeType returns HTML
     */
    @Test
    void testGuessMimeTypeHtml() {
        assertEquals("text/html", HTTPServer.guessMimeType("index.html"),
                "Los archivos .html deben mapearse a text/html");
    }

    /**
     * test that method MimeType returns png
     */
    @Test
    void testGuessMimeTypePng() {
        assertEquals("image/png", HTTPServer.guessMimeType("logo.png"),
                "Los archivos .png deben mapearse a image/png");
    }

    /**
     * test that method MimeType returns text/plain with unknown extentions
     */
    @Test
    void testGuessMimeTypeUnknown() {
        assertEquals("text/plain", HTTPServer.guessMimeType("archivo.xyz"),
                "Las extensiones desconocidas deben mapearse a text/plain");
    }

    /**
     * test that controllers are loaded correctly at the service Map
     */
    @Test
    void testLoadRestControllers() throws Exception {
        HTTPServer.loadRestControllers("edu.eci.arep.microspringboot");
        Method m = HTTPServer.services.get("/greeting");
        assertNotNull(m, "El endpoint /greeting debería estar registrado");
    }

}
