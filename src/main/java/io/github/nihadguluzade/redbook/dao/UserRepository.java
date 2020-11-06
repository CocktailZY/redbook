package io.github.nihadguluzade.redbook.dao;

import io.github.nihadguluzade.redbook.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity, Integer> {

    UsersEntity findByUsername(String username);

}
