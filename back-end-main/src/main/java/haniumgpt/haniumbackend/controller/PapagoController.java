package haniumgpt.haniumbackend.controller;

import haniumgpt.haniumbackend.service.PapagoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PapagoController {
    @PostMapping("/ShortTranslationTest")
    public String ShortTranslatorController(@Valid @RequestBody String request){
        return PapagoService.sendPapago(request);
    }
}