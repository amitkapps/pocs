package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by amitkapps on 9/23/15.
 */
@RestController
public class HelloController {

    @RequestMapping("/")
    public String index(){
        return "Message of the day";
    }

}
