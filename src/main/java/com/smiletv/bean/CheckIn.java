package com.smiletv.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hejian on 2014/7/4.
 * 用户签到信息
 */
@Entity
@Table(name="t_checkin")
public class CheckIn implements Serializable {

    private Integer id;
    private Date checkinTime;           //签到具体时间
    private User user;                  //签到的用户

    @Id
    @GeneratedValue(generator = "native_generrator")
    @GenericGenerator(name = "native_generrator",strategy = "native")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Date checkinTime) {
        this.checkinTime = checkinTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
