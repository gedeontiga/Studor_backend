package com.studor.orientation_student.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PermissionType {
    ADMIN_CREATE_ALL,
    ADMIN_READ_ALL,
    ADMIN_UPDATE_ALL,
    ADMIN_DELETE_ALL,

    MANAGER_CREATE_USER,
    MANAGER_READ_ALL_USERS,
    MANAGER_UPDATED_USER,
    MANAGER_DELETE_USER,

    USER_GET_ALL_SERVICES;

    @Getter
    private String permisson;
}
