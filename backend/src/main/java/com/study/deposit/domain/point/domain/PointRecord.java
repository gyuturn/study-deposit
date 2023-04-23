package com.study.deposit.domain.point.domain;

import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.domain.user.domain.Users;
import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointRecord {

    //year + month + day;
    @Id
    private String merchant_uid;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @NotNull
    private Users users;

    @NotNull
    private LocalDateTime paymentDate;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @Positive
    @NotNull
    private Long chargeAmount;


    public static PointRecord makePointRecord(Users users, PointRecordPrepareDto pointRecordPrepareDto, PaymentType paymentType) {
        return PointRecord.builder()
                .merchant_uid(pointRecordPrepareDto.getMerchant_uid())
                .chargeAmount(pointRecordPrepareDto.getAmount())
                .users(users)
                .paymentDate(LocalDateTime.now())
                .paymentType(paymentType)
                .build();
    }

    //포인트만을 사용하여 pointRecord만들때 생성 ex) 스터디방 입장,등
    public static PointRecord makePointRecord(Users users,Long paymentAmount, PaymentType paymentType) {
        return PointRecord.builder()
                .merchant_uid(createOrderNum())
                .chargeAmount(paymentAmount)
                .users(users)
                .paymentDate(LocalDateTime.now())
                .paymentType(paymentType)
                .build();
    }

    public static String createOrderNum() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuuMMdd");

        String orderNum = now.format(formatter);
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            orderNum += random.nextInt(8);
        }

        return orderNum;
    }
}
