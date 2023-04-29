package com.study.deposit.domain.hashTag.dao;

import static org.assertj.core.api.Assertions.*;

import com.study.deposit.domain.hashTag.domain.HashTag;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HashTagDaoTest {

    @Autowired
    HashTagDao hashTagDao;

    @Test
    @DisplayName("사용자의 입력이 포함된 해시태그")
    void findByNameContaining(){
        //given
        String input = "토익";
        List<HashTag> hashTags = List.of(
                HashTag.builder().tagName("토익공부").build(),
                HashTag.builder().tagName("공부토익").build(),
                HashTag.builder().tagName("공부 토익 하실분").build(),
                HashTag.builder().tagName("공부 하실분").build()
        );
        hashTagDao.saveAll(hashTags);
        //when
        List<HashTag> byNameContaining = hashTagDao.findByTagNameContaining(input);
        //then
        assertThat(byNameContaining.size()).isEqualTo(3);
    }

}