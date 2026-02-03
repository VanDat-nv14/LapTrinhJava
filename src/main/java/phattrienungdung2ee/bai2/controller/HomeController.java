package phattrienungdung2ee.bai2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller trả trang HTML (không phải REST JSON).
 * Giống slide: @Controller + return tên view → Spring tìm file trong templates/.
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String index() {
        return "index";
    }
}
