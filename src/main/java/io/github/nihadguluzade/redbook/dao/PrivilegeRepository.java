package io.github.nihadguluzade.redbook.dao;

import io.github.nihadguluzade.redbook.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Integer> {

    PrivilegeEntity findByPrivilegeName(String privilegeName);

}
