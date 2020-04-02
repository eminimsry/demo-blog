package com.demo.blog.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_webcount")
public class WebCount {
    @Id
    @GeneratedValue
    private Long id;
    private Long onLineNum;  //在线人数
    private Long todayWatchNum;  //今日访问人数

    private Long historyWatchNum;   //历史总访问次数
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

}
