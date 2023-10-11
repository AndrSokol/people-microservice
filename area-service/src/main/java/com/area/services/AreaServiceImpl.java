package com.area.services;

import com.area.entities.AreaColor;
import com.area.entities.AreaEntity;
import com.area.repository.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    public AreaServiceImpl(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public List<AreaEntity> findAll() {
        return areaRepository.findAll();
    }

    @Override
    public AreaEntity addNewArea(String name, AreaColor color) {
        AreaEntity area = new AreaEntity();
        area.setName(name);
        area.setColor(color);

        return areaRepository.save(area);
    }

    @Override
    public AreaEntity findById(Long id) {
        Optional<AreaEntity> area = areaRepository.findById(id);
        return area.orElseThrow(() -> new RuntimeException("Not found with id: " + id));
    }

    @Override
    public AreaEntity findByName(String name) {
        Optional<AreaEntity> area = areaRepository.findByName(name);
        return area.orElseThrow(() -> new RuntimeException("Not found with name: " + name));
    }

    @Override
    public AreaEntity findByColor(AreaColor color) {
        Optional<AreaEntity> area = areaRepository.findByColor(color);
        return area.orElseThrow(() -> new RuntimeException("Not found with color" + color));
    }

    @Override
    public String getPath(Long areaId) {
        Optional<AreaEntity> byId = areaRepository.findById(areaId);
        AreaEntity area = byId.orElseThrow(() -> new RuntimeException("Not found" + areaId));

        // TODO
        return "";
    }

    @Override
    public void linkAreas(Long parentId, Long childId) {
        AreaEntity parent = findById(parentId);
        AreaEntity child = findById(childId);
        child.setParent(parent);

        areaRepository.save(child);
    }
}
