package poc.arch.spring.rest.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import poc.arch.spring.rest.ws.domain.Vessel;

import java.util.List;

@Controller
@RequestMapping("/vesselservice")
public class VesselServiceController {

    private static Logger log = LoggerFactory.getLogger(VesselServiceController.class);


    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Vessel getVessel() {
        Vessel vessel = new Vessel();
        vessel.setVesselCd("MAT");
        vessel.setVesselName("MATSONIA");
        return vessel;
    }
}
