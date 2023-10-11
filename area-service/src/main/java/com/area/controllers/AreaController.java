package com.area.controllers;

import com.area.dtos.AreaDto;
import com.area.dtos.CreateAreaRequest;
import com.area.entities.AreaColor;
import com.area.entities.AreaEntity;
import com.area.mappers.AreaMapper;
import com.area.services.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private AreaService areaService;

    @GetMapping
    public ResponseEntity<List<AreaDto>>  getAll() {
        List<AreaEntity> areaList = areaService.findAll();
        return new ResponseEntity<>(areaMapper.toListDto(areaList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDto> getById(@PathVariable Long id) {
        AreaEntity area = areaService.findById(id);
        return new ResponseEntity<>(areaMapper.entityToDto(area), HttpStatus.OK);
    }

    @PostMapping
    public AreaDto create(@RequestBody CreateAreaRequest request) {
        AreaEntity newArea = areaService
                .addNewArea(request.getName(), AreaColor.valueOf(request.getColor()));
        return areaMapper.entityToDto(newArea);
    }

    @PostMapping("/link")
    public ResponseEntity linkAreas(@RequestParam Long parent, @RequestParam Long child) {
        areaService.linkAreas(parent, child);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
