package com.antonio.apprendrebackend.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_info_id", nullable = false)
    private UserInfo userInfo;

    private Long createdDate;
    private boolean isAnswered;

    public UserRequest(String subject, String message, UserInfo userInfo) {
        this.subject = subject;
        this.message = message;
        this.userInfo = userInfo;
        this.createdDate = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", userInfo=" + userInfo +
                ", createdDate=" + createdDate +
                ", isAnswered=" + isAnswered +
                '}';
    }
}
