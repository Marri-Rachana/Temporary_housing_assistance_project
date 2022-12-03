package com.housebooking.app.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user_security")
public class UserSecurityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String securityQuestion;
    private String securityAnswer;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;


}
