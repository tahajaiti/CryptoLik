package mapper.dto.interfaces;

public interface DTOMapper<Entity, ResponseDTO, RequestDTO> {
    ResponseDTO toResponseDTO(Entity entity);
    void updateFromRequest(Entity entity, RequestDTO requestDTO);
}
