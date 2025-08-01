package com.hnjee.jemilog.theme;

import com.hnjee.jemilog.tag.Tag;
import com.hnjee.jemilog.tagtheme.TagTheme;
import com.hnjee.jemilog.tagtheme.TagThemeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class ThemeServiceTest {
    @Autowired EntityManager em;
    @Autowired ThemeService themeService;
    @Autowired TagThemeRepository tagThemeRepository;

    @Test
    void 테마를_저장하면_DB에_정상적으로_저장된다() {
        // given
        Theme theme = new Theme("비오는 날", "촉촉한 테마", "url1", "#123456");

        // when
        Long savedId = themeService.save(theme, List.of());

        // then
        Theme found = themeService.findById(savedId);
        assertThat(found).isEqualTo(theme);
        assertThat(found.getName()).isEqualTo("비오는 날");
        assertThat(found.getDescription()).isEqualTo("촉촉한 테마");
        assertThat(found.getImageUrl()).isEqualTo("url1");
        assertThat(found.getColorCode()).isEqualTo("#123456");
    }


    @Test
    void 전체_테마를_조회하면_저장된_테마들이_포함된다() {
        // given
        themeService.save(new Theme("비", "테마1", "img1", "#111111"), List.of());
        themeService.save(new Theme("맑음", "테마2", "img2", "#222222"), List.of());
        themeService.save(new Theme("눈", "테마3", "img3", "#333333"), List.of());

        // when
        List<Theme> themes = themeService.findAll();

        // then
        assertThat(themes).hasSize(3);
        assertThat(themes).extracting("name").containsExactlyInAnyOrder("비", "맑음", "눈");
    }

    @Test
    void 테마를_ID로_조회할_수_있다() {
        // given
        Theme theme = new Theme("테스트", "설명", "url", "#abcdef");
        Long savedId = themeService.save(theme, List.of());

        // when
        Theme found = themeService.findById(savedId);

        // then
        assertThat(found.getId()).isEqualTo(savedId);
    }

    @Test
    void 테마를_이름으로_조회할_수_있다() {
        // given
        Theme theme = new Theme("산책", "테스트용", "url", "#000000");
        themeService.save(theme, List.of());

        // when
        Theme found = themeService.findByName("산책");

        // then
        assertThat(found.getName()).isEqualTo("산책");
        assertThat(found.getDescription()).isEqualTo("테스트용");
    }

    @Test
    void 중복된_테마이름으로_저장하면_예외가_발생한다() {
        // given
        Theme theme1 = new Theme("중복", "설명1", "url1", "#aaaaaa");
        Theme theme2 = new Theme("중복", "설명2", "url2", "#bbbbbb");
        themeService.save(theme1, List.of());

        // expect
        assertThatThrownBy(() -> themeService.save(theme2, List.of()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 테마명입니다.");
    }


    @Test
    void 테마_수정() {
        // given
        Long savedId = themeService.save(new Theme("원래이름", "설명", "img1", "#000000"), List.of());

        // when
        themeService.update(savedId, "수정된이름", "새설명", "img1_1", "#ffffff");

        // then
        Theme updatedTheme = themeService.findById(savedId);
        assertThat(updatedTheme.getName()).isEqualTo("수정된이름");
        assertThat(updatedTheme.getDescription()).isEqualTo("새설명");
        assertThat(updatedTheme.getImageUrl()).isEqualTo("img1_1");
        assertThat(updatedTheme.getColorCode()).isEqualTo("#ffffff");
    }

    @Test
    void 없는_테마를_수정하면_예외발생() {
        // given
        Long notExistId = 999L;

        // when & then
        assertThatThrownBy(() ->
                themeService.update(notExistId, "newName", "newDesc", "newImage", "#ffffff")
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 테마가 없습니다.");
    }

    @Test
    void 테마_삭제처리() {
        // given
        Long savedId = themeService.save(new Theme("삭제할테마", "설명", "img", "#aaaaaa"), List.of());

        // when
        themeService.delete(savedId);

        // then
        Theme deletedTheme = themeService.findById(savedId);
        assertThat(deletedTheme.isDeleted()).isTrue();
    }

    @Test
    void 없는_테마를_삭제하면_예외발생() {
        // given
        Long notExistId = 999L;

        // when & then
        assertThatThrownBy(() ->
                themeService.delete(notExistId)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 테마가 없습니다.");
    }

    @Test
    @Commit
    void 테마_생성시_태그연결_확인(){
        // given
        Tag tag1 = new Tag("아이와함께", "img1");
        Tag tag2 = new Tag("실내활동", "img2");
        em.persist(tag1);
        em.persist(tag2);

        Theme theme = new Theme("비오는 날", "촉촉한 테마", "url1", "#123456");
        List<Tag> tags = List.of(tag1, tag2);
        Long savedId = themeService.save(theme, tags);

        em.flush();  // DB에 insert 쿼리 발생
        em.clear();  // 영속성 컨텍스트 초기화

        // when
        Theme foundTheme = themeService.findById(savedId);

        List<TagTheme> foundTagThemes = em.createQuery(
                        "select tt from TagTheme tt where tt.theme.id = :themeId", TagTheme.class)
                .setParameter("themeId", savedId)
                .getResultList();

        // then
        // 1. TagTheme 테이블에 잘 저장되었는가
        assertThat(foundTagThemes).hasSize(2);
        assertThat(foundTagThemes).extracting(tt -> tt.getTag().getName())
                .containsExactlyInAnyOrder("아이와함께", "실내활동");

        // 2. Theme의 tagThemes 컬렉션에 연관관계 잘 들어갔는가
        assertThat(foundTheme.getTagThemes()).hasSize(2);

        // 3. 각 Tag의 tagThemes에도 잘 연결되었는가
        for (TagTheme tt : foundTagThemes) {
            Tag tag = tt.getTag();
            assertThat(tag.getTagThemes()).anyMatch(t -> t.getTheme().getId().equals(savedId));
        }
    }
}
