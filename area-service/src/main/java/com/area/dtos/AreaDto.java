package com.area.dtos;

import com.area.entities.AreaColor;
import com.area.entities.AreaEntity;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AreaDto {
    Long id;
    String name;
    String color;
    Long parent;
    List<AreaDto> children;
}
