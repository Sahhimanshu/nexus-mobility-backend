package com.nexus.mobility.service;

import com.nexus.mobility.dto.UserTenantDtos;
import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.entity.UserAccount;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserAccount> list(UUID tenantId, DomainEnums.UserRole role) {
        List<UserAccount> users = role == null ? userAccountRepository.findByTenantId(tenantId) : userAccountRepository.findByTenantIdAndRole(tenantId, role);
        return users.stream().sorted(Comparator.comparing(UserAccount::getFullName)).toList();
    }

    public UserAccount get(UUID id) {
        return userAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Transactional
    public UserAccount create(UserTenantDtos.UserRequest request) {
        UserAccount user = new UserAccount();
        apply(user, request, true);
        return userAccountRepository.save(user);
    }

    @Transactional
    public UserAccount update(UUID id, UserTenantDtos.UserRequest request) {
        UserAccount user = get(id);
        apply(user, request, false);
        return userAccountRepository.save(user);
    }

    @Transactional
    public void delete(UUID id) {
        userAccountRepository.delete(get(id));
    }

    private void apply(UserAccount user, UserTenantDtos.UserRequest request, boolean creating) {
        user.setTenantId(request.tenantId());
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setActive(request.active() == null || request.active());
        if (creating || (request.password() != null && !request.password().isBlank())) {
            user.setPasswordHash(passwordEncoder.encode(request.password() == null || request.password().isBlank() ? "ChangeMe123!" : request.password()));
        }
    }
}
