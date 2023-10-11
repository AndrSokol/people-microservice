package com.area.services;

import com.area.entities.AreaColor;
import com.area.entities.AreaEntity;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AreaTest {
    private final String AREA_NAME = "areaName";
    private final AreaColor AREA_COLOR = AreaColor.GREY;
    private final Long AREA_ID = 1l;

    @Test
    void shouldAddValidParent() {
        AreaEntity area1 = new AreaEntity();
        area1.setName("Parent");
        area1.setColor(AREA_COLOR);
        area1.setId(1L);

        AreaEntity area2 = new AreaEntity();
        area2.setName("Child");
        area2.setColor(AREA_COLOR);
        area2.setId(2L);

        area2.setParent(area1);

        assertThat(area1.getChildren(), contains(area2));
        assertThat(area2.getParent(), equalTo(area1));
    }

    @Test
    void shouldNotAddSameObjectAsParent() {
        AreaEntity area1 = new AreaEntity();
        area1.setName(AREA_NAME);
        area1.setColor(AREA_COLOR);
        area1.setId(1L);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> area1.setParent(area1));

        assertEquals(exception.getMessage(), "Same area cannot be added as a parent: "
                + area1.getId());
    }

    @Test
    void shouldNotAddChildrenAsParent() {
        AreaEntity area1 = new AreaEntity();
        area1.setName("Parent");
        area1.setColor(AREA_COLOR);
        area1.setId(1L);

        AreaEntity area2 = new AreaEntity();
        area2.setName("Child");
        area2.setColor(AREA_COLOR);
        area2.setId(2L);

        AreaEntity area3 = new AreaEntity();
        area3.setName("grandChild");
        area3.setColor(AREA_COLOR);
        area3.setId(3L);

        area1.addChild(area2);
        area2.addChild(area3);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> area1.setParent(area3));

        assertEquals(exception.getMessage(), "Child object cannot be a parent. Area: "
                + area1.getId() + ". ParentId: " + area3.getId());
    }

    @Test
    void shouldAddValidChildren() {
        AreaEntity area1 = new AreaEntity();
        area1.setName("Parent");
        area1.setColor(AREA_COLOR);
        area1.setId(1L);

        AreaEntity area2 = new AreaEntity();
        area2.setName("Child");
        area2.setColor(AREA_COLOR);
        area2.setId(2L);

        area1.addChild(area2);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> area2.addChild(area1));

        assertEquals(exception.getMessage(), "Area with id "
                + area1.getId() + " already exists in parent hierarchy");

    }

    @Test
    void shouldNotAddSameAsChild() {
        AreaEntity area1 = new AreaEntity();
        area1.setName("Parent");
        area1.setColor(AREA_COLOR);
        area1.setId(1L);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> area1.addChild(area1));

        assertEquals(exception.getMessage(), "Same area cannot be added as a child: "
                + area1.getId());
    }

    @Test
    void shouldNotAddParentAsChild() {
        AreaEntity area1 = new AreaEntity();
        area1.setName("Parent");
        area1.setColor(AREA_COLOR);
        area1.setId(1L);

        AreaEntity area2 = new AreaEntity();
        area2.setName("Child");
        area2.setColor(AREA_COLOR);
        area2.setId(2L);

        AreaEntity area3 = new AreaEntity();
        area3.setName("GrandChild");
        area3.setColor(AREA_COLOR);
        area3.setId(3L);

        area2.setParent(area1);
        area3.setParent(area2);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> area3.addChild(area1));

        assertEquals(exception.getMessage(), "Area with id "
                + area1.getId() + " already exists in parent hierarchy");
    }

    @Test
    void shouldNotAddChildrenOfAnotherArea() {
        AreaEntity area1 = new AreaEntity();
        area1.setName("Parent");
        area1.setColor(AREA_COLOR);
        area1.setId(1L);

        AreaEntity area2 = new AreaEntity();
        area2.setName("Child");
        area2.setColor(AREA_COLOR);
        area2.setId(2L);

        AreaEntity area3 = new AreaEntity();
        area3.setName("GrandChild");
        area3.setColor(AREA_COLOR);
        area3.setId(3L);

        area1.addChild(area2);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> area3.addChild(area2));

        assertEquals(exception.getMessage(), "Area with id "
                + area2.getId() + " already has a parent: " + area1.getId());
    }

    @Test
    void shouldReturnPath() {
        AreaEntity area1 = new AreaEntity();
        area1.setName("Parent");
        area1.setColor(AREA_COLOR);
        area1.setId(1L);

        AreaEntity area2 = new AreaEntity();
        area2.setName("Child");
        area2.setColor(AREA_COLOR);
        area2.setId(2L);

        AreaEntity area3 = new AreaEntity();
        area3.setName("GrandChild");
        area3.setColor(AREA_COLOR);
        area3.setId(3L);

        area2.addChild(area3);
        area3.setParent(area2);

        area1.addChild(area2);
        area2.setParent(area1);

        assertThat(area3.getPath(), equalTo("Parent/Child/GrandChild"));
    }
}
