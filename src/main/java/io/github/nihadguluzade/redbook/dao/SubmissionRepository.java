package io.github.nihadguluzade.redbook.dao;

import io.github.nihadguluzade.redbook.entity.SubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<SubmissionEntity, Integer> {
    List<SubmissionEntity> findAllByOrderByPostedDesc();

}
