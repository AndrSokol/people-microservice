package com.area.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name = "areas")
public class AreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    AreaColor color;

    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
//    @Fetch(FetchMode.JOIN)
    AreaEntity parent;

    //    TODO Learn how it works
//    @JsonManagedReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    List<AreaEntity> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AreaColor getColor() {
        return color;
    }

    public void setColor(AreaColor color) {
        this.color = color;
    }

    public AreaEntity getParent() {
        return parent;
    }

    public void setParent(AreaEntity parent) {
        if (this.equals(parent)) {
            throw new RuntimeException("Same area cannot be added as a parent: "
                    + parent.id);
        }
        if (hasSameChildInHierarchy(parent)) {
            throw new RuntimeException("Child object cannot be a parent. Area: "
                    + this.id + ". ParentId: " + parent.id);
        }
        this.parent = parent;
        parent.children.add(this);
    }

    public void addChild(AreaEntity child) {
        if (this.equals(child)) {
            throw new RuntimeException("Same area cannot be added as a child: "
                    + child.getId());
        }

        if (child.getParent() != null) {
            throw new RuntimeException("Area with id " + child.getId() + " already has a parent: "
                    + child.getParent().getId());
        }

        if (getAllParents().contains(child)) {
            throw new RuntimeException("Area with id " + child.getId() + " already exists in parent hierarchy");
        }

        this.children.add(child);
        child.parent = this;
    }

    private boolean hasSameChildInHierarchy(AreaEntity parent) {
        Set<AreaEntity> children = new HashSet<>();
        getAllChildren(this, children);
        return children.contains(parent);
    }

    private void getAllChildren(AreaEntity area, Set<AreaEntity> childrenSet) {
        List<AreaEntity> children = area.getChildren();
        for (AreaEntity area1 : children) {
            if (area1 != null) {

                childrenSet.addAll(children);
                getAllChildren(area1, childrenSet);
            }
        }
    }

    public List<AreaEntity> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public String getPath() {
        List<AreaEntity> parents = getAllParents();
        Collections.reverse(parents);
        return parents.stream()
                .map(a -> a.name)
                .collect(Collectors.joining("/"));
    }

    private List<AreaEntity> getAllParents() {
        List<AreaEntity> parents = new ArrayList<>();
        parents.add(this);
        collectParents(this, parents);
        return parents;
    }

    private void collectParents(AreaEntity area, List<AreaEntity> parents) {
        AreaEntity parent = area.getParent();
        if (parent != null) {
            parents.add(parent);
            collectParents(parent, parents);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaEntity area = (AreaEntity) o;
        return Objects.equals(id, area.id) && Objects.equals(name, area.name) && color == area.color && Objects.equals(parent, area.parent) && Objects.equals(children.size(), area.children.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, parent, children.size());
    }
}
