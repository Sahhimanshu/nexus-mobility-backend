package com.nexus.mobility.controller;

import com.nexus.mobility.dto.UserTenantDtos;
import com.nexus.mobility.entity.DomainEnums;
import com.nexus.mobility.entity.UserAccount;
import com.nexus.mobility.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"/api/users", "/api/v1/users"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserAccount> list(@RequestParam UUID tenantId, @RequestParam(required = false) DomainEnums.UserRole role) {
        return userService.list(tenantId, role);
    }

    @PostMapping
    public UserAccount create(@Valid @RequestBody UserTenantDtos.UserRequest request) {
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public UserAccount get(@PathVariable UUID id) {
        return userService.get(id);
    }

    @PatchMapping("/{id}")
    public UserAccount update(@PathVariable UUID id, @Valid @RequestBody UserTenantDtos.UserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}
