package viewerApp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ViewerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ViewerApplication.class).run(args);
    }
}
