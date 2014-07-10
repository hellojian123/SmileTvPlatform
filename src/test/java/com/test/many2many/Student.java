package com.test.many2many;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hejian on 2014/7/10.
 *
 */
@Entity
@Table(name="t_student")
public class Student implements Serializable {

    private Integer id;
    private String name;

    private Set<Teacher> teachers = new HashSet<Teacher>(0);

    @Id
    @GeneratedValue(generator = "native_generator")
    @GenericGenerator(name = "native_generator",strategy = "native")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "students" ,fetch = FetchType.LAZY)  //mappedBy = "students":表示对方teacher为主控方
    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}
