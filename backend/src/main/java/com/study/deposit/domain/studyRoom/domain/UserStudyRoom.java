package com.study.deposit.domain.studyRoom.domain;

import com.study.deposit.domain.user.domain.Users;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStudyRoom {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
    @NotNull
    private StudyRoom studyRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    @NotNull
    private Users users;

    @Enumerated(EnumType.STRING)
    private StudyRoomRole studyRoomRole;

    //호스트가 방을 만들때만 사용가능
    public static UserStudyRoom toEntityForHost(StudyRoom studyRoom, Users host,StudyRoomRole studyRoomRole) {
        return UserStudyRoom.builder()
                .studyRoom(studyRoom)
                .users(host)
                .studyRoomRole(studyRoomRole)
                .build();
    }

}
