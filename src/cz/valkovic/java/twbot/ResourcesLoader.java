package cz.valkovic.java.twbot;

import java.io.IOException;
import java.net.URL;

public class ResourcesLoader {

    public static URL getResource(String name) throws IOException {
        URL url = ResourcesLoader.class.getClassLoader().getResource(name);
        if(url == null)
            throw new IOException("Resource " + name + "does not exists");
        return url;
    }

}
