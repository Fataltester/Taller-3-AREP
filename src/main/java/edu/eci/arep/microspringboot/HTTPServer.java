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
import java.security.Provider.Service;
import java.util.HashMap;
import java.util.Map;


public class HTTPServer {
    public static Map<String,Method> services = new HashMap<String,Method>();
    
    public static void loadServices(String[] args) throws ClassNotFoundException{
        Class c = Class.forName(args[0]);
        System.out.println("aaaaaaaaaaaa:"+c);
        if(c.isAnnotationPresent(RestController.class)){
            Method[] methods = c.getDeclaredMethods();
            for(Method m: methods){
                if(m.isAnnotationPresent(GetMapping.class)){
                    String mapping = m.getAnnotation(GetMapping.class).value();
                    services.put(mapping,m);
                }
            }
        }
    }
    public static void startServer(String[] args) throws IOException, URISyntaxException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        loadServices(args);
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
        
        if(requestUri.getPath().startsWith("/app")){
            outputLine = processRequest(requestUri);
        }else{
            outputLine = "";
        }
        
        out.println(outputLine);
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
        Method m = services.get(servicePath);
        RequestParam rp = (RequestParam) m.getParameterAnnotations()[0][0];
        String[] argsValues = new String[]{};
        if(requestUri.getQuery() == null){
            argsValues = new String[]{rp.defaultValue()};
        }else{
            String queryParamName = rp.value();
            argsValues = new String[]{req.getValue(req.getValue(queryParamName))};
        }
        
        String header = "HTTP/1.1 200 OK\n\r"
                    + "content-type: application/json\n\r"
                    + "\n\r";
        return header + m.invoke(null, argsValues);
    }


    

    public static void staticfiles(String path){
        
    }
    
}
