package com.manish.WorkHub.client;

import com.manish.WorkHub.config.AppConstants;
import com.manish.WorkHub.dto.PaginationResponse;
import com.manish.WorkHub.dto.TaskRequest;
import com.manish.WorkHub.dto.TaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "WorkHub-Task-Service", url = "${TaskService}", path = "/v1/api/task")
public interface TaskClient {

    @PostMapping("/add")
    public ResponseEntity<TaskResponse> addTask(@RequestBody TaskRequest taskRequest);

    @PutMapping("/changeStatus/{taskId}/{status}")
    public ResponseEntity<Boolean> changeSatus(@PathVariable String taskId, @PathVariable String status);

    @GetMapping("/all")
    public ResponseEntity<List<TaskResponse>> getAllTask();

//    @GetMapping("/all/{userId}")
//    public ResponseEntity<List<TaskResponse>> getAllTaskByUserId();
    @GetMapping("/all/{userId}")
    public ResponseEntity<PaginationResponse<TaskResponse>> getAllTaskByUserId(
            @PathVariable String userId,
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE ,required = false) Integer pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir" , defaultValue = AppConstants.SORT_DIR,required = false) String sortDir);

}