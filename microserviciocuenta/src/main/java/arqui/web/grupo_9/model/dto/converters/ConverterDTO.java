package arqui.web.grupo_9.model.dto.converters;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ConverterDTO<Entity, DTO> {
    public abstract DTO fromEntity(Entity e);
    public abstract Entity fromDTO(DTO d);

    public List<DTO> fromEntity(List<Entity> entities) {
        return entities.stream().map(e -> fromEntity(e)).collect(Collectors.toList());
    }

    public List<Entity> fromDTO(List<DTO> dtos) {
        return dtos.stream().map(d -> fromDTO(d)).collect(Collectors.toList());
    }
}
