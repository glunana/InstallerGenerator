package com.example.installer.repository;

import com.example.installer.ExeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExeFileRepository extends JpaRepository<ExeFile, Long> {

}
