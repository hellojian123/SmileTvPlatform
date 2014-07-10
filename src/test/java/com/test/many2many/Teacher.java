package com.test.many2many;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hejian on 2014/7/10.
 */
@Entity
@Table(name = "t_teacher")
public class Teacher implements Serializable {

    private Integer id;
    private String name;

    private Set<Student> students = new HashSet<Student>(0);

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_teacher_student",                          //name:第三方表名称
            joinColumns = {@JoinColumn(name = "teacher_id")},       //joinColumns:当前实体类在第三方表中的字段名称并指向当前对象
            inverseJoinColumns = {@JoinColumn(name="student_id")})  //inverseJoinColumns：当前实体类持有引用对象在第三方表中的字段名称并指向被引用对象表
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
