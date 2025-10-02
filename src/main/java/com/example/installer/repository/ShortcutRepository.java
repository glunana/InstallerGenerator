package com.example.installer.repository;

import com.example.installer.Shortcut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortcutRepository extends JpaRepository<Shortcut, Long> {

}
