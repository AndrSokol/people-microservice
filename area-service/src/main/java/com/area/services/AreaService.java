package com.area.services;

import com.area.entities.AreaEntity;
import com.area.entities.AreaColor;

import java.util.List;

public interface AreaService {
    List<AreaEntity> findAll();
    AreaEntity addNewArea(String name, AreaColor color);
    AreaEntity findById(Long id);
    AreaEntity findByName(String name);
    AreaEntity findByColor(AreaColor color);
    String getPath(Long areaId);

    void linkAreas(Long parent, Long child);
}
