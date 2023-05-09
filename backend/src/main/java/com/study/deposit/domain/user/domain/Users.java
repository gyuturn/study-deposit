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
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false, length = 36)
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String nickName;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

    public Users updateNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    //basetimeEntity때문에 overrride가 안됨
    public  boolean isEqualUser(Users o) {
        return this.getId().equals(o.getId());
    }


}
