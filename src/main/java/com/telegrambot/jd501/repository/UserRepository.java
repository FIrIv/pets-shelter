package com.telegrambot.jd501.repository;

import com.telegrambot.jd501.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByAdoptedIsTrue ();
}
