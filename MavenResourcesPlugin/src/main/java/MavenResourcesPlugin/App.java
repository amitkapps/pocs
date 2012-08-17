package MavenResourcesPlugin;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Hello world!
 *
 */
@Service
public class App{

    public void execute() throws IOException {
        InputStream is = App.class.getClassLoader().getResourceAsStream("build.properties");
        StringBuilder sb = new StringBuilder("BuildTimeStamp=");
        int i;
        while ((i = is.read()) != -1){
            sb.append((char) i);
        }
        System.out.println(sb.toString());
    }
}
