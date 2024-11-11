package arqui.web.grupo_9.model.dto.converter;

import arqui.web.grupo_9.model.dto.EstacionDTO;
import arqui.web.grupo_9.model.entities.Estacion;


public class EstacionConverter extends ConverterDTO<Estacion, EstacionDTO> {

    public EstacionDTO fromEntity(Estacion e) {
        if (e == null) {
            return null;
        }

        return new EstacionDTO(e.getIdEstacion(), e.getNombre(), e.getLatitud(), e.getLongitud());
    }

    public Estacion fromDTO(EstacionDTO d) {
        if(d == null)
            return null;

        return new Estacion(d.getIdEstacion(), d.getNombre(), d.getLatitud(), d.getLongitud());
    }
}
