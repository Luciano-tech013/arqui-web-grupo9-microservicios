package arqui.web.grupo_9.model.dto.converter;

import arqui.web.grupo_9.model.dto.MonopatinDTO;
import arqui.web.grupo_9.model.entities.Monopatin;

public class MonopatinConverter extends ConverterDTO<Monopatin, MonopatinDTO> {

    @Override
    public MonopatinDTO fromEntity(Monopatin e) {
        if(e == null)
            return null;

        return new MonopatinDTO(e.getIdMonopatin(), e.getKmsRecorridos(), e.getLatitud(), e.getLongitud(), e.getEstado());
    }

    @Override
    public Monopatin fromDTO(MonopatinDTO d) {
        if(d == null)
            return null;

        return new Monopatin(d.getIdMonopatin(), d.getKmsRecorridos(), d.getLatitud(), d.getLongitud(), d.getEstado());
    }
}
