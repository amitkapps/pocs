package arch.samples.hystrix.dashboard;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Created by amitkapps on 9/28/15.
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboard {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixDashboard.class).web(true).run(args);
    }
}
