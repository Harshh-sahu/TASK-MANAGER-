package com.zosh.task_submission_services.repository;

import com.zosh.task_submission_services.modal.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



public interface SubmissionRepository extends JpaRepository<Submission,Long> {



    List<Submission> findByTaskId(Long taskId);



}
