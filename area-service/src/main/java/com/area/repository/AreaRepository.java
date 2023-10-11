package com.area.repository;

import com.area.entities.AreaEntity;
import com.area.entities.AreaColor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<AreaEntity, Long> {
    Optional<AreaEntity> findByColor(AreaColor color);
    Optional<AreaEntity> findByName(String name);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "children"
            }
    )
    Optional<AreaEntity> findById(Long id);
}
