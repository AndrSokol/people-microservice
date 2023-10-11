package com.area.mappers;

import com.area.dtos.AreaDto;
import com.area.entities.AreaEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Component
public class AreaMapper {

    public AreaDto entityToDto(AreaEntity areaEntity) {
        return AreaDto.builder()
                .id(areaEntity.getId())
                .name(areaEntity.getName())
                .color(areaEntity.getColor().name())
                .parent(areaEntity.getParent() == null ? null : areaEntity.getParent().getId())
                .children(toListDto(areaEntity.getChildren()))
                .build();
    }

    public List<AreaDto> toListDto(List<AreaEntity> areaList) {
        return areaList.stream().map(this::entityToDto).toList();
    }
}
