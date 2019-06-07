package com.yuan.house.POJO;

import com.yuan.house.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPOJO implements Serializable{
    private static final long serialVersionUID = 7406514329215340801L;
    private Long commentId;
    private Long userId;
    private Long contractId;
    private double userGrade;
    private double houseGrade;
    private String info;
    private String url;
    private Timestamp ctime;
    private String head;
    private String username;
}
