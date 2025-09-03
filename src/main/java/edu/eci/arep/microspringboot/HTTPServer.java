/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arep.microspringboot;
import edu.eci.arep.microspringboot.annotations.GetMapping;
import edu.eci.arep.microspringboot.annotations.RequestParam;
import edu.eci.arep.microspringboot.annotations.RestController;
import java.net.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.security.Provider.Service;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class HTTPServer {
    public static Map<String,Method> services = new HashMap<String,Method>();
    private static String staticFilesDir = "src/main/public";
    
    public static void startServer(String args) throws IOException, URISyntaxException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, Exception {
        loadRestControllers(args);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        boolean running = true;
        while (running){
            try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        boolean isFirstLine = true;
        URI requestUri = null;
        while ((inputLine = in.readLine()) != null) {
            if(isFirstLine){
                requestUri = new URI(inputLine.split(" ")[1]);
                System.out.println("Path: " + requestUri.getPath());
                isFirstLine = false;
            }
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }
        OutputStream rawOut = clientSocket.getOutputStream();
        if(requestUri.getPath().startsWith("/app")){
            String response = processRequest(requestUri);
            rawOut.write(response.getBytes());
            rawOut.flush();
        }else{
            sendStaticFile(rawOut, requestUri.getPath());
        }
        
        
        out.close();
        in.close();
        clientSocket.close();
        
        }
        serverSocket.close();
    }
    
    private static String processRequest(URI requestUri) throws IllegalAccessException, InvocationTargetException {
        String servicePath = requestUri.getPath().substring(4);
        //Service service = services.get(serviceRoute);
        HttpRequest req = new HttpRequest(requestUri);
        HttpResponse res = new HttpResponse();
        Method m = services.get(servicePath); // -> reflexion, se guarda (greeting, metodo "/greeting")
        RequestParam rp = (RequestParam) m.getParameterAnnotations()[0][0];  // -> obtenemos @RequestParam, tomamos la primera notacion del arreglo 
        String[] argsValues = new String[]{};
        if(requestUri.getQuery() == null){
            argsValues = new String[]{rp.defaultValue()}; // si no hay query, define el valor default
        }else{
            String queryParamName = rp.value();
            argsValues = new String[]{req.getValue(req.getValue(queryParamName))}; // hay query, extrae el valor del query
        }
        
        String header = "HTTP/1.1 200 OK\n\r"
                    + "content-type: application/json\n\r"
                    + "\n\r";
        return header + m.invoke(null, argsValues); // reflexion para invocar el metodo
    }
    
    public static void staticfiles(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        staticFilesDir = path;
    }
    
    private static void sendStaticFile(OutputStream out, String requestPath) throws IOException {
        String relativePath = requestPath.startsWith("/") ? requestPath.substring(1) : requestPath;
        File file = new File(staticFilesDir, relativePath);

        if (file.exists() && !file.isDirectory()) {
            String mimeType = guessMimeType(file.getName());
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            String header = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: " + mimeType + "\r\n"
                    + "Content-Length: " + fileBytes.length + "\r\n\r\n";

            out.write(header.getBytes());
            out.write(fileBytes);   // aquí mandas el binario tal cual
            out.flush();
        } else {
            String error = "HTTP/1.1 404 Not Found\r\n\r\nFile not found";
            out.write(error.getBytes());
            out.flush();
        }
    }

    public static String guessMimeType(String filename) {
        if (filename.endsWith(".html")) {
            return "text/html";
        }
        if (filename.endsWith(".css")) {
            return "text/css";
        }
        if (filename.endsWith(".js")) {
            return "application/javascript";
        }
        if (filename.endsWith(".png")) {
            return "image/png";
        }
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        return "text/plain";
    }
    
    public static void loadRestControllers(String basePackage) throws Exception {
        String path = basePackage.replace('.', '/');
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File dir = new File(resource.toURI());
            for (File file : dir.listFiles()) {
                if (file.getName().endsWith(".class")) {
                    //Cargar la clase por reflexión
                    String className = basePackage + "." + file.getName().replace(".class", "");
                    Class<?> c = Class.forName(className);
                    //Verificar si está anotada con @RestController
                    if (c.isAnnotationPresent(RestController.class)) {
                        for (Method m : c.getDeclaredMethods()) {
                            if (m.isAnnotationPresent(GetMapping.class)) {
                                String mapping = m.getAnnotation(GetMapping.class).value();
                                services.put(mapping, m);  // Guarda el endpoint y el método
                                System.out.println("Registrado endpoint: " + mapping + " -> " + m);
                            }
                        }
                    }
                }
            }
        }
    }
    
    
}
