package com.zosh.task_submission_services.service;


import com.zosh.task_submission_services.modal.Submission;

import java.util.List;

public interface SubmissionService {

Submission submitTask(Long taskId,String githubLink,Long userId,String jwt) throws Exception;

Submission getTaskSubmissionById(Long submissionId) throws Exception;

List<Submission> getAllTaskSubmissions();

List<Submission> getTaskSubmissionsByTaskId(Long taskId);

Submission acceptDeclineSubmission(Long id , String status) throws  Exception;




}
