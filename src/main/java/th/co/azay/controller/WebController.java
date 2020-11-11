package th.co.azay.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class WebController {
	private static final Logger logger = LogManager.getLogger(WebController.class);

	@GetMapping("/" )
	public String touchpoints(Map<String, Object> model) {
		return "touchpoints";
	}

	@GetMapping("/cashless")
	public String cashless(@RequestParam String scb, @RequestParam String transactionId, Map<String, Object> model) {
		model.put("scbdeeplink", scb);
		model.put("scbtransactions", "/api/transactions/"+transactionId);
		return "cashless";
	}

	@GetMapping("/redirect")
	@ResponseBody
	public String redirect(@RequestParam String status, Map<String, Object> model){
		logger.info("redirect status = {}", status);
		model.put("status", status);
		return "success";
	}

}