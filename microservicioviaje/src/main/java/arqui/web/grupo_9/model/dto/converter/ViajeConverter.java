package arqui.web.grupo_9.viaje.model.dto.converter;

import arqui.web.grupo_9.viaje.model.dto.ViajeDTO;
import arqui.web.grupo_9.viaje.model.entities.Viaje;
import org.springframework.stereotype.Component;

@Component
public class ViajeConverter extends ConverterDTO<Viaje, ViajeDTO> {

    @Override
    public ViajeDTO fromEntity(Viaje e) {
        if(e == null)
            return null;

        return new ViajeDTO(e.getFechaIniViaje(), e.getFechaFinViaje(), e.getKmsRecorridos(), e.getCostoTotal());
    }

    @Override
    public Viaje fromDTO(ViajeDTO d) {
        return null;
    }
}
