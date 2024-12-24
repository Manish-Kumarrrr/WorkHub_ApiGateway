package com.manish.WorkHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private String taskId;
    private String userId;
    private String tag;
    private String pay;
    private String email;
    private String phoneNo;
    private Date createdDate;
    private List<String> images;
    private String status;
    private String description;
    private String lattitude;
    private String longitude;
    private String address;
    private String city;
    private String state;
    private String pincode;
}
