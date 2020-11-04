package com.azay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class CashlessController {

	@RequestMapping("/")
	public String touchpoints(Map<String, Object> model) {
		return "touchpoints";
	}

	@RequestMapping("/cashless")
	public String cashless(Map<String, Object> model) {
		return "cashless";
	}

}