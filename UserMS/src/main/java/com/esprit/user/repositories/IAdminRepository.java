package com.esprit.user.repositories;

import com.esprit.user.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepository extends JpaRepository<Admin, Long> {
}
