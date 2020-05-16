package com.htec.vojinpesalj.dakarrally.repository;

import com.htec.vojinpesalj.dakarrally.repository.domain.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
