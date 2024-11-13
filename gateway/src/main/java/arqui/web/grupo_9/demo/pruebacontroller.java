package arqui.web.grupo_9.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/prueba")
@RestController
public class pruebacontroller {

    @GetMapping
    public String get() {
        return "probandooo";
    }
}
