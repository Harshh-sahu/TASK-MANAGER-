package com.zosh.task_submission_services.service;

import com.zosh.task_submission_services.modal.Submission;
import com.zosh.task_submission_services.modal.TaskDto;
import com.zosh.task_submission_services.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService{


   @Autowired
 private SubmissionRepository submissionRepository;


   @Autowired
    private  TaskService taskService;


    @Override
    public Submission submitTask(Long taskId, String githubLink, Long userId,String jwt) throws Exception {

        TaskDto task = taskService.getTaskById(taskId,jwt);
        if(task != null){
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithubLink(githubLink);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepository.save(submission);

        }
    throw new Exception("TASK NOT FOUND with ID: "+taskId);
    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {

        return submissionRepository.findById(submissionId).orElseThrow(()->new Exception("Task Submission not found with id"+submissionId));

    }

    @Override
    public List<Submission> getAllTaskSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionsByTaskId(Long taskId) {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {

       Submission submission = getTaskSubmissionById(id);
       submission.setStatus(status);
     if(status.equals("ACCEPT")){
         taskService.completeTask(submission.getTaskId());

     }



        return submissionRepository.save(submission);
    }
}
