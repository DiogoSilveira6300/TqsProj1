package ua.tqs.AirQuality;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    @GetMapping("/home")
    public String all(Model model){
        return "index.html";
    }


}
