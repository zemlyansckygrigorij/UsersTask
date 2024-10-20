package org.example.userstask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class User for entity from table users
 */
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false , unique=true)
    private Long id;

    @Column(name = "username", nullable = false , unique=true)
    private String username;

    @Column(name = "email", nullable = false, unique=true)
    private String email;

    @Column(name = "password_user", nullable = false)
    private String password;

    @Column(name = "created", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj.getClass()!=this.getClass()) return false;
        if(((User) obj).getId()!=this.getId()) return false;
        if(((User) obj).username!=this.username) return false;
        return true;
    }
    @Override
    public int hashCode() {
        return (this.getId()+this.username).hashCode();
    }
}



