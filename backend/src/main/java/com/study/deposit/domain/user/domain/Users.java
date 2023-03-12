package com.study.deposit.domain.user.domain;


import com.study.deposit.global.config.BaseTimeEntity;
import com.sun.istack.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Builder
public class Users extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false, length = 36)
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private String nickName;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

}
