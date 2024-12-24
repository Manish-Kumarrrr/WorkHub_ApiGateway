package com.manish.WorkHub.controller;

import com.manish.WorkHub.client.TaskClient;
import com.manish.WorkHub.config.AppConstants;
import com.manish.WorkHub.dto.PaginationResponse;
import com.manish.WorkHub.dto.TaskRequest;
import com.manish.WorkHub.dto.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "${WorkHub_UI}")
@RestController
@RequestMapping("/v1/task")  // Base path for resource-related endpoints
@RequiredArgsConstructor
public class TaskController {
    private final TaskClient taskClient;

    @PostMapping("/add")
    public ResponseEntity<TaskResponse> addTask(@RequestBody TaskRequest taskRequest){
        return taskClient.addTask(taskRequest);
    }

    @PutMapping("/changeStatus/{taskId}/{status}")
    public ResponseEntity<Boolean> changeSatus(@PathVariable String taskId, @PathVariable String status){
        return taskClient.changeSatus(taskId,status);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskResponse>> getAllTask(){
        return taskClient.getAllTask();
    }

//    @GetMapping("/all/{userId}")
//    public ResponseEntity<List<TaskResponse>> getAllTaskByUserId(){
//        return taskClient.getAllTaskByUserId();
//    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<PaginationResponse<TaskResponse>> getAllTaskByUserIdPagination(
            @PathVariable String userId,
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE ,required = false) Integer pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir" , defaultValue = AppConstants.SORT_DIR,required = false) String sortDir)
    {
        return taskClient.getAllTaskByUserId(userId,pageNumber,pageSize,sortBy,sortDir);
    }
}
