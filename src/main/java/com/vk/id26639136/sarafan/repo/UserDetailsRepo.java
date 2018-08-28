package com.vk.id26639136.sarafan.repo;

import com.vk.id26639136.sarafan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}
