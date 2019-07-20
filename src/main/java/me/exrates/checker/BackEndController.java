package me.exrates.checker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackEndController {

    @GetMapping
    public String shalom(){
        return "Shalom";
    }
}
