package arqui.web.grupo_9.model.dto.converters;

import arqui.web.grupo_9.model.dto.CuentaMpDTO;
import arqui.web.grupo_9.model.entities.CuentaMP;
import org.springframework.stereotype.Component;

@Component
public class CuentaMPConverter extends ConverterDTO<CuentaMP, CuentaMpDTO> {
    @Override
    public CuentaMpDTO fromEntity(CuentaMP e) {
        if(e == null)
            return null;

        return new CuentaMpDTO(e.getIdCuentaMP(), e.getSaldo());
    }

    @Override
    public CuentaMP fromDTO(CuentaMpDTO d) {
        if(d == null)
            return null;

        return new CuentaMP(d.getIdCuentaMP(), d.getSaldo());
    }
}
