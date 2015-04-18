package httpd.v2;
/* Reinhard Schiedermeier: Programmieren mit Java II
 * Pearson Studium, 2012, ISBN 978-3-8273-xxxx-x
 * Entwickelt mit: Oracle Java SE 1.7.0 Update 04
 * Listing 7.25: Minimaler Webserver für eine einzige Seite.
 */
import java.io.*;
import java.net.*;

public class NanoHttpServer {
    private static final String responseBody = "<!DOCTYPE html>"
            + "<html>"
            + "    <head>"
            + "          <meta http-equiv=Content-type content=\"text/html; charset=us-ascii\">"
            + "          <title>Meine Homepage</title>"
            + "    </head>"
            + "    <body>"
            + "          <div>"
            + "              <h1>Willkommen auf meiner <em>Homepage!</em></h1>"
            + "              Das bin <em>ich</em>:"
            + "              <reader>"
            + "              <img src=photograph.png alt=\"Bild von mir\">"
            + "          </div>"
            + "          <div>"
            + "              Und hier sind <a href=friends.html>meine Freunde.</a>"
            + "          </div>"
            + "    </body>"
            + "</html>";

    public static void main(String... args) throws IOException {
        int port = Integer.parseInt(args[0]);
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while(true)
                try(Socket socket = serverSocket.accept();
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output))) {
                    for(String line = reader.readLine(); !line.isEmpty(); line = reader.readLine())
                        System.out.println("line: "+line);
                    System.out.println("request from " + socket.getRemoteSocketAddress());

                    writer.println("HTTP/1.0 200 OK");
                    writer.println("Content-Type: text/html; charset=ISO-8859-1");
                    writer.println("Server: NanoHTTPServer");
                    writer.println();
                    
                    writer.println(responseBody);
                }
                catch(IOException iox) {
                }
        }
    }
}