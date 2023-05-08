package com.study.deposit.domain.studyRoom.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.studyRoom.domain.AttendanceType;
import com.study.deposit.domain.studyRoom.dto.StudyRoomInfoResDto;
import com.study.deposit.domain.studyRoom.dto.StudyRoomMakingReqDto;
import com.study.deposit.domain.studyRoom.service.StudyRoomService;
import com.study.deposit.domain.user.controller.AuthController;
import com.study.deposit.domain.user.service.AuthService;
import com.study.deposit.global.common.CommonResponse;
import com.study.deposit.global.common.code.CommonCode;
import com.study.deposit.global.config.annotation.WithAuthUser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = StudyRoomController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class StudyRoomControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudyRoomService studyRoomService;

    @Test
    @WithAuthUser(email = "test@naver.com", role = "ROLE_USER")
    @DisplayName("스터디방 생성 api 호출 테스트")
    void createStudyRoom() throws Exception {
        // given
        String ReqDtoStr = "{\n"
                + "  \"title\": \"testRoom\",\n"
                + "  \"attendanceType\": \"AttendanceCheck\",\n"
                + "  \"attendanceTime\": \"13:40:00\",\n"
                + "  \"endDate\": \"2023-04-24\",\n"
                + "  \"personCapacity\": 10,\n"
                + "  \"deposit\": 100,\n"
                + "  \"hashTags\": [\n"
                + "    {\n"
                + "      \"id\": 1,\n"
                + "      \"tagName\": \"string\"\n"
                + "    }\n"
                + "  ]\n"
                + "}";
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(ReqDtoStr);
        StudyRoomMakingReqDto reqDto = createRoomReqDto(jsonObject);
        // when
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/studyroom")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((ReqDtoStr)))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        verify(studyRoomService).makeStudyRoom(reqDto);
    }
    private String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private StudyRoomMakingReqDto createRoomReqDto(JSONObject jsonObject) throws JSONException {
        StudyRoomMakingReqDto dto = new StudyRoomMakingReqDto();
        dto.setPersonCapacity((Long) jsonObject.get("personCapacity"));
        dto.setAttendanceType((AttendanceType) jsonObject.get("attendanceType"));
        dto.setAttendanceTime((LocalTime) jsonObject.get("attendanceTime"));
        dto.setDeposit((Long) jsonObject.get("deposit"));
        dto.setTitle((String) jsonObject.get("title"));
        dto.setHashTags(Arrays.asList(new HashTag(1L, "tag")));
        dto.setEndDate((LocalDate) jsonObject.get("endDate"));
        return dto;
    }

    @Test
    @WithAuthUser(email = "test@naver.com", role = "ROLE_USER")
    @DisplayName("스터디방 전체 조회")
    void getStudyRoomList() throws Exception {
        // Create sample data
        StudyRoomInfoResDto studyRoom1 = new StudyRoomInfoResDto();
        StudyRoomInfoResDto studyRoom2 = new StudyRoomInfoResDto();

        List<StudyRoomInfoResDto> studyRoomList = new ArrayList<>();
        studyRoomList.add(studyRoom1);
        studyRoomList.add(studyRoom2);

        when(studyRoomService.getStudyRoomList()).thenReturn(studyRoomList);

        // Perform GET request to the endpoint
        mockMvc.perform(get("/api/v1/studyroom")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify the expected behavior
        verify(studyRoomService).getStudyRoomList();
    }
}