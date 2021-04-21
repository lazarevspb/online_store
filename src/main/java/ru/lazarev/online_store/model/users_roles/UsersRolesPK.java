package ru.lazarev.online_store.model.users_roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersRolesPK implements Serializable {
    protected Long userId;
    protected Long roleId;
}
