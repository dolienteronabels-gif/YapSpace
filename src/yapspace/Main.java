
package yapspace;

import yapspace.storage.DataStore;
import yapspace.web.WebServer;

import java.io.IOException;
import java.net.BindException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

/**
 * Starts YapSpace.
 * Run this class, then open http://localhost:8080 in your browser.
 *
 * Optional environment variable:
 *   PORT=8081
 *
 * Optional program arguments:
 *   --port=8081
 *   --data=somefolder
 */
public class Main {

    public static void main(String[] args) {

        int port = 8080;

        // Use the port supplied by the hosting platform.
        String envPort = System.getenv("PORT");

        if (envPort != null && !envPort.isBlank()) {
            try {
                port = Integer.parseInt(envPort);
            } catch (NumberFormatException e) {
                System.err.println("Invalid PORT environment variable.");
                return;
            }
        }

        Path dataDir = Paths.get("data");

        // Allow command-line arguments to override defaults.
        for (String arg : args) {

            if (arg.startsWith("--port=")) {
                try {
                    port = Integer.parseInt(
                            arg.substring("--port=".length())
                    );
                } catch (NumberFormatException e) {
                    System.err.println(
                            "The port must be a number, for example --port=8081"
                    );
                    return;
                }

            } else if (arg.startsWith("--data=")) {
                dataDir = Paths.get(
                        arg.substring("--data=".length())
                );
            }
        }

        DataStore store = new DataStore(dataDir);
        store.load();

        try {
            WebServer server = new WebServer(port, store);

            server.start();

            Runtime.getRuntime().addShutdownHook(
                    new Thread(server::stop)
            );

            System.out.println();
            System.out.println(
                    "  YapSpace is running at http://localhost:" + port
            );
            System.out.println(
                    "  Your data is saved in " + dataDir.toAbsolutePath()
            );
            System.out.println(
                    "  Press Ctrl+C to stop the server."
            );
            System.out.println();

            // Keep the application alive while the web server is running.
            new CountDownLatch(1).await();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } catch (BindException e) {
            System.err.println(
                    "Port " + port + " is already in use. "
                    + "Close the other program or run with --port="
                    + (port + 1)
            );

        } catch (IOException e) {
            System.err.println(
                    "Could not start the server: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }
}
