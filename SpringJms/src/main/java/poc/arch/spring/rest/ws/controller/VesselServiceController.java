package poc.arch.spring.rest.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import poc.arch.spring.jms.MessageSender;
import poc.arch.spring.rest.ws.domain.Vessel;

@Controller
@RequestMapping("/terminalService")
public class VesselServiceController {

    private static Logger log = LoggerFactory.getLogger(VesselServiceController.class);

    @Autowired
    MessageSender messageSender;

    @RequestMapping(method = RequestMethod.GET, value = "{terminalId}/vesselList")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Vessel[] getVesselList(@PathVariable int terminalId) {
        log.info("Request vessel list for terminalId={}", terminalId);
        Vessel vessel1 = new Vessel();
        vessel1.setVesselCd("MAT");
        vessel1.setVesselName("MATSONIA");

        Vessel vessel2 = new Vessel();
        vessel2.setVesselCd("RJP");
        vessel2.setVesselName("R J PFEIFER");

        messageSender.sendMessage(String.valueOf(terminalId));

        return new Vessel[]{vessel1, vessel2};
    }
}
