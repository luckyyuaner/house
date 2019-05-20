package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract implements Serializable {
    private static final long serialVersionUID = -5246053609132892817L;
    private Long contractId;
    private Long houseId;
    private Long userId;
    private Timestamp stime;
    private Timestamp etime;
    private Timestamp ctime;
    private Timestamp utime;
    private int landlordOperation;
    private int tenantOperation;
    private int type;
    private int status;
    private String file;
    private String landlordInfo;
    private String tenantInfo;
}
