package com.study.deposit.domain.hashTag.dao;

import com.study.deposit.domain.hashTag.domain.HashTag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagDao extends JpaRepository<HashTag, Long> {
    Optional<HashTag> findByTagName(String tagName);
    List<HashTag> findByTagNameContaining(String tagName);
}
