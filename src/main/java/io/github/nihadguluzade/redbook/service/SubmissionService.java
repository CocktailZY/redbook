package io.github.nihadguluzade.redbook.service;

import io.github.nihadguluzade.redbook.dao.SubmissionRepository;
import io.github.nihadguluzade.redbook.entity.SubmissionEntity;
import io.github.nihadguluzade.redbook.entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {

    private SubmissionRepository submissionRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public void save(SubmissionEntity submission) {
        UsersEntity user = usersService.getUser();
        submission.setUsersByAuthorId(user);
        submissionRepository.save(submission);
    }
}
