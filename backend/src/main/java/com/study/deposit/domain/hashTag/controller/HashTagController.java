package com.study.deposit.domain.hashTag.controller;

import com.study.deposit.domain.hashTag.dao.HashTagDao;
import com.study.deposit.domain.hashTag.domain.HashTag;
import com.study.deposit.domain.hashTag.dto.MakeHashTagReqDto;
import com.study.deposit.domain.hashTag.service.HashTagService;
import com.study.deposit.domain.studyRoom.dto.StudyRoomMakingReqDto;
import com.study.deposit.global.common.CommonResponse;
import com.study.deposit.global.common.code.CommonCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hashtag")
@Validated
public class HashTagController {
    private final HashTagService hashTagService;

    /**
     * 1. 해시태그 생성 2. 해시태그 조회(글자 입력마다) -> 없는경우는 새로 만들기
     */

    @Operation(summary = "해시태그 생성 api", description = "스터디방 생성시에 세부사항에 해시태그를 추가하는  API'\n")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "정상 생성"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가"),
            }
    )
    @PostMapping
    public ResponseEntity<CommonResponse> createHashTag(@RequestBody MakeHashTagReqDto makeHashTagReqDto) {
        HashTag newHashTag = hashTagService.makeHashTag(makeHashTagReqDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.toResponse(CommonCode.CREATED,newHashTag));
    }

    @Operation(summary = "해시태그 조회 api", description = "사용자 입력마다 해시태그 조회'\n")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 생성"),
                    @ApiResponse(responseCode = "401", description = "사용자 확인 불가"),
                    @ApiResponse(responseCode = "404", description = "해당 입력으로 시작하는 해시태그 존재하지 않음"),
            }
    )
    @GetMapping
    public ResponseEntity<CommonResponse> getHashTags(@RequestParam() String input) {
        List<HashTag> hashTags = hashTagService.getHashTags(input);
        if (hashTags.size() == 0) {
            //검색된 해시태그가 아무것도 없는경우
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CommonResponse.toResponse(CommonCode.NOT_FOUND));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.toResponse(CommonCode.OK, hashTags));


    }


}
