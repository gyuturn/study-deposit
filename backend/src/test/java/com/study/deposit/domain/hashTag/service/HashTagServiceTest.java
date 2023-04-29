package com.study.deposit.domain.hashTag.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import com.study.deposit.domain.hashTag.dao.HashTagDao;
import com.study.deposit.domain.hashTag.dao.StudyRoomHashTagDao;
import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.hashTag.domain.StudyRoomHashTag;
import com.study.deposit.domain.hashTag.dto.MakeHashTagReqDto;
import com.study.deposit.domain.studyRoom.domain.AttendanceType;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HashTagServiceTest {
    @Mock
    private HashTagDao hashTagDao;
    @Mock
    private StudyRoomHashTagDao studyRoomHashTagDao;

    @InjectMocks
    private HashTagService hashTagService;

    @Test
    @DisplayName("해시태그 생성 로직")
    void makeHashTag() {
        MakeHashTagReqDto dto = new MakeHashTagReqDto();
        dto.setTagName("hashTag");

        hashTagService.makeHashTag(dto);

        verify(hashTagDao).save(any(HashTag.class));
    }

    @Test
    @DisplayName("스터디방 생성시 해당 해시태그 매핑 및 DB저장")
    void hashTagsMapStudyRoom() {
        HashTag tag1 = HashTag.builder()
                .id(1L)
                .tagName("tag1")
                .build();
        HashTag tag2 = HashTag.builder()
                .id(2L)
                .tagName("tag2")
                .build();
        List<HashTag> hashTags = new ArrayList<>();
        hashTags.add(tag1);
        hashTags.add(tag2);

        StudyRoom studyRoom = StudyRoom.builder()
                .id(3L)
                .endDate(LocalDate.of(2024, 04, 01))
                .attendanceTime(LocalTime.of(13, 00))
                .personCapacity(10L)
                .deposit(1000L)
                .createDate(LocalDateTime.of(2023, 04, 01, 3, 30))
                .attendanceType(AttendanceType.AttendanceCheck)
                .title("testStudyRoom")
                .build();

        hashTagService.hashTagsMapStudyRoom(hashTags, studyRoom);

        verify(studyRoomHashTagDao, times(1)).saveAll(Mockito.any(List.class));

    }
}