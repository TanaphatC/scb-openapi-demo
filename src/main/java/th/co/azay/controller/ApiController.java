package th.co.azay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.co.azay.model.DeeplinkResponse;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger logger = LogManager.getLogger(ApiController.class);

    @PostMapping("/deeplink")
    public ResponseEntity deeplink(@RequestBody String jsonReq) {
        logger.info("json request: {}", jsonReq);
        String scbDeeplink = "scbeasysim://purchase/dad96674-ce9d-4830-88c5-8aa11cb953d4";
        String redirect = "/cashless?scb="+scbDeeplink;
        return ResponseEntity.ok(new DeeplinkResponse(redirect));
    }


}
