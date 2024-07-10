package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "movie")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableField("movieId")
    private int movieid;
    @TableField("movieName")
    private String moviename;
    @TableField("movieClass")
    private String movieclass;
    private String img;
    @Column(name = "need_vip")
    private boolean needVip;
    private String director;
    private String region;
    private String actor;
    private String introduce;
    private float grade;
    private int view;

}

