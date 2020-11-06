package io.github.nihadguluzade.redbook.dao;

import io.github.nihadguluzade.redbook.entity.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<PageEntity, Integer> {

    PageEntity findById(int id);

    List<PageEntity> findAll();

}
