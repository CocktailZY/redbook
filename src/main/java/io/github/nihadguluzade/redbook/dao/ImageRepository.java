package io.github.nihadguluzade.redbook.dao;

import io.github.nihadguluzade.redbook.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

    @Query("select im from ImageEntity im where im.imageId = 1")
    ImageEntity findPageDefaultIcon();

}
