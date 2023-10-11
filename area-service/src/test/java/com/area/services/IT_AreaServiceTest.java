package com.area.services;

import com.area.entities.AreaEntity;
import com.area.entities.AreaColor;
import com.area.repository.AreaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest(classes = {AreaServiceImpl.class, AreaRepository.class})
public class IT_AreaServiceTest {

//    @Autowired
    private AreaService areaService;

    private final String AREA_NAME = "areaName";
    private final AreaColor AREA_COLOR = AreaColor.GREY;
    private final Integer AREA_ID = 1;

//    @Test
    public void shouldSaveArea() {
//        AreaEntity areaToCreate = new AreaEntity(AREA_NAME, AREA_COLOR);
//        areaService.addNewArea(AREA_NAME, AREA_COLOR);
    }
}
