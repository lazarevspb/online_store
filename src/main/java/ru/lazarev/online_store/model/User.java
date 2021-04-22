package ru.lazarev.online_store.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "order_id")
    private Long orderId;

    /*При авторизации ошибка, если поле enabled раскоменчено на фронте в форме логина:
    Null value was assigned to a property [class ru.lazarev.online_store.model.User.enabled]
    of primitive type setter of ru.lazarev.online_store.model.User.enabled; nested exception
    is org.hibernate.PropertyAccessException: Null value was assigned to a property
    [class ru.lazarev.online_store.model.User.enabled] of primitive type setter of
    ru.lazarev.online_store.model.User.enabled */

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;


    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
