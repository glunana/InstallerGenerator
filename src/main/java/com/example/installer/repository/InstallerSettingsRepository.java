package com.example.installer.repository;

import com.example.installer.InstallerSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallerSettingsRepository extends JpaRepository<InstallerSettings, Long> {

}
