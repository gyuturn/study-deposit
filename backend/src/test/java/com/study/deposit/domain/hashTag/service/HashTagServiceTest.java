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

    @Test
    @DisplayName("해당 스터디방에 존재하는 해시태그 조회")
    void testGetHashTagsByStudyRoom() {
        // Create a StudyRoom object for testing
        StudyRoom studyRoom = StudyRoom.builder()
                .id(1L)
                .deposit(1000L)
                .attendanceTime(LocalTime.of(13, 0, 0))
                .personCapacity(10L)
                .attendanceType(AttendanceType.AttendanceCheck)
                .title("testRoom")
                .createDate(LocalDateTime.now())
                .endDate(LocalDate.of(2100, 12, 03))
                .build();

        // Create sample data
        HashTag tag1 = HashTag.builder()
                .id(11L)
                .tagName("tag1").build();
        HashTag tag2 = HashTag.builder()
                .id(12L)
                .tagName("tag2").build();
        List<StudyRoomHashTag> studyRoomHashTags = new ArrayList<>();
        studyRoomHashTags.add(new StudyRoomHashTag(100L,studyRoom, tag1));
        studyRoomHashTags.add(new StudyRoomHashTag(101L,studyRoom, tag2));

        // Mock the behavior of studyRoomHashTagDao.findByStudyRoom(studyRoom)
        when(studyRoomHashTagDao.findByStudyRoom(studyRoom)).thenReturn(studyRoomHashTags);

        // Call the method under test
        List<HashTag> result = hashTagService.getHashTagsByStudyRoom(studyRoom);

        // Verify the expected behavior
        verify(studyRoomHashTagDao).findByStudyRoom(studyRoom);
        assertEquals(2, result.size()); // Assert the size of the returned list
        assertEquals("tag1", result.get(0).getTagName()); // Assert the first element in the list
        assertEquals("tag2", result.get(1).getTagName()); // Assert the second element in the list
    }
}