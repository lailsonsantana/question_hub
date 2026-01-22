package com.example.questifysharedapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "TB_USER")
@Data // Generate getters, setters, constructors, equals, hashcode
//@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder // It's like a constructor more simplified
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(unique = true, nullable = true)
    private String email;

    @Column
    private String password;

    @CreatedDate
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Classification> classifications = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        if(this.role == UserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ADMIN") , 
            new SimpleGrantedAuthority("TEACHER"),
            new SimpleGrantedAuthority("STUDENT"));
        }
        else if(this.role == UserRole.TEACHER){
            return List.of(new SimpleGrantedAuthority("TEACHER"));
        }
        else if(this.role == UserRole.STUDENT){
            return List.of(new SimpleGrantedAuthority("STUDENT"));
        }

        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public String getUsername() {
        return "";
        //throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }
}
