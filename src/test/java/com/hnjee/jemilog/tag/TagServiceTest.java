package com.hnjee.jemilog.tag;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class TagServiceTest {
    @Autowired TagRepository tagRepository;
    @Autowired TagService tagService;

    @Test
    void save() {
        // given
        Tag tag = new Tag("비오는날", "imageUrl");

        // when
        Long savedId = tagService.save(tag);

        // then
        Tag found = tagService.findById(savedId);
        assertThat(found.getName()).isEqualTo("비오는날");
        assertThat(found.getImageUrl()).isEqualTo("imageUrl");
    }

    @Test
    void 중복된_태그이름으로_저장하면_예외가_발생한다() {
        //given
        tagService.save(new Tag("비오는날", "img1"));

        // expect
        assertThatThrownBy(() -> tagService.save(new Tag("비오는날", "img2")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 태그명입니다.");
    }

    @Test
    void 태그_수정(){
        // given
        Long savedId = tagService.save(new Tag("비오는날", "img1"));
        //when
        tagService.update(savedId, "눈오는날", "img1_1");
        //then
        Tag updatedTag = tagService.findById(savedId);
        assertThat(updatedTag.getName()).isEqualTo("눈오는날");
        assertThat(updatedTag.getImageUrl()).isEqualTo("img1_1");
    }

    @Test
    void 없는_태그를_수정하면_예외발생() {
        // given
        Long notExistId = 999L;

        // when & then
        assertThatThrownBy(() ->
                tagService.update(notExistId, "newName", "newImage")
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 태그가 없습니다.");
    }

    @Test
    void 태그_삭제처리(){
        // given
        Long savedId = tagService.save(new Tag("비오는날", "img1"));
        //when
        tagService.delete(savedId);
        //then
        Tag deletedTag = tagService.findById(savedId);
        assertThat(deletedTag.isDeleted()).isEqualTo(true);
    }

    @Test
    void 없는_태그를_삭제하면_예외발생() {
        // given
        Long notExistId = 999L;

        // when & then
        assertThatThrownBy(() ->
                tagService.delete(notExistId)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 태그가 없습니다.");
    }

    @Test
    void fingTags() {
        // given
        tagService.save(new Tag("비오는날", "img1"));
        tagService.save(new Tag("맑은날", "img2"));
        tagService.save(new Tag("눈오는날", "img3"));

        // when
        List<Tag> foundTags = tagService.findTags();

        // then
        assertThat(foundTags).isNotEmpty();
        assertThat(foundTags).hasSize(3);
        assertThat(foundTags).extracting("name").contains("비오는날");
        assertThat(foundTags).extracting("name")
                .containsExactlyInAnyOrder("비오는날", "맑은날", "눈오는날");
    }

    @Test
    void findById() {
        // given
        Tag tag = new Tag("비오는날", "imageUrl");
        Long savedId = tagService.save(tag);
        // when
        Tag found = tagService.findById(savedId);
        // then
        assertThat(found).isEqualTo(tag);
        assertThat(found).isSameAs(tag);
        assertThat(found.getId()).isEqualTo(savedId);

    }

    @Test
    void findByName() {
        // given
        Tag tag = new Tag("비오는날", "imageUrl");
        Long savedId = tagService.save(tag);
        // when
        Tag found = tagService.findByName("비오는날");
        // then
        assertThat(found.getName()).isEqualTo("비오는날");
    }
}