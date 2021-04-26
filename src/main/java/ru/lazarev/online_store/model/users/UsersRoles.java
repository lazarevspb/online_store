package ru.lazarev.online_store.model.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@IdClass(UsersRolesPK.class)
@Table(name = "users_roles")
public class UsersRoles {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Id
    @Column(name = "role_id")
    private Long roleId;
}
