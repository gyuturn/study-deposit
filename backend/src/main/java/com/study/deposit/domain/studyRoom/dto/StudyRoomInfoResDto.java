package com.study.deposit.domain.studyRoom.dto;

import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.studyRoom.domain.StudyRoom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class StudyRoomInfoResDto {
    @NotNull
    private String title;
    @NotNull
    private List<String> hashTags;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotNull
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime attendanceTime;
    @NotNull
    @Positive
    private Long capacity;
    @NotNull
    @Positive
    private Long currentOccupancy;
    @NotNull
    @Positive
    private Long deposit;

    public static StudyRoomInfoResDto getEntity(StudyRoom studyRoom, List<HashTag> hashTags, Long currentOccupancy) {
        StudyRoomInfoResDto studyRoomInfoResDto = new StudyRoomInfoResDto();
        studyRoomInfoResDto.setTitle(studyRoom.getTitle());
        studyRoomInfoResDto.setCapacity(studyRoom.getPersonCapacity());
        studyRoomInfoResDto.setAttendanceTime(studyRoom.getAttendanceTime());
        studyRoomInfoResDto.setEndDate(studyRoom.getEndDate());
        //해시태그
        for (HashTag hashTag : hashTags) {
            List<String> ownHashTags = new ArrayList<>();
            ownHashTags.add(hashTag.getTagName());
            studyRoomInfoResDto.setHashTags(ownHashTags);
        }
        studyRoomInfoResDto.setCurrentOccupancy(currentOccupancy);
        studyRoomInfoResDto.setStartDate(studyRoom.getCreateDate().toLocalDate());
        studyRoomInfoResDto.setDeposit(studyRoom.getDeposit());
        return studyRoomInfoResDto;
    }

}
