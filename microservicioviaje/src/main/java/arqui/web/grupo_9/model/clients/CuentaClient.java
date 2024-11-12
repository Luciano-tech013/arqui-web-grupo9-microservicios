package arqui.web.grupo_9.model.clients;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CuentaClient {
    private int dni;
    private double credito;
}
