package com.study.deposit.domain.point.domain;

import com.study.deposit.domain.user.domain.Users;
import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointRecord {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false, length = 36)
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @NotNull
    private Users users;

    @NotNull
    private LocalDateTime chargeDate;

    @Positive
    @NotNull
    private Long chargeAmount;


    public static PointRecord makePointRecord(Users users, Long chargeAmount) {
        return PointRecord.builder()
                .chargeAmount(chargeAmount)
                .users(users)
                .chargeDate(LocalDateTime.now())
                .build();
    }
}
