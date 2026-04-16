package com.nexus.mobility.repository;

import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByEmail(String email);
    List<UserAccount> findByTenantId(UUID tenantId);
    List<UserAccount> findByTenantIdAndRole(UUID tenantId, DomainEnums.UserRole role);
}
