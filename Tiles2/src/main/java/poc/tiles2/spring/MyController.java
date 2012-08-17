package poc.tiles2.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Mar 25, 2010
 * Time: 5:35:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MyController {

    @RequestMapping(value = "/home")
    public String showHomePage(){
        return "homePage";
    }
}