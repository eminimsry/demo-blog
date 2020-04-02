package com.demo.blog.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_speak")
public class Speak {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private Integer agreenum;
    private Integer aginstnum;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private User user;

    public Speak() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAgreenum() {
        return agreenum;
    }

    public void setAgreenum(Integer agreenum) {
        this.agreenum = agreenum;
    }

    public Integer getAginstnum() {
        return aginstnum;
    }

    public void setAginstnum(Integer aginstnum) {
        this.aginstnum = aginstnum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
