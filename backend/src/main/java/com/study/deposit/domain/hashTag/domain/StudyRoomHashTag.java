package com.study.deposit.domain.hashTag.domain;

import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import javax.persistence.Entity;
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
public class StudyRoomHashTag {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
    @NotNull
    private StudyRoom studyRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hash_tag_id")
    @NotNull
    private HashTag hashTag;

    //스터디방 생성 시 해당 스터디방에 맞는 해시태그 저장하기 위한 메서드
    public static StudyRoomHashTag toEntity(StudyRoom studyRoom, HashTag hashTag) {
        return StudyRoomHashTag.builder()
                .studyRoom(studyRoom)
                .hashTag(hashTag)
                .build();
    }


}
