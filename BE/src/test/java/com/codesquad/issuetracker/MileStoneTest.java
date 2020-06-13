package com.codesquad.issuetracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.codesquad.issuetracker.milestone.business.MileStoneService;
import com.codesquad.issuetracker.milestone.data.MileStone;
import com.codesquad.issuetracker.milestone.data.MileStoneRepository;
import com.codesquad.issuetracker.milestone.web.model.MileStoneQuery;
import com.codesquad.issuetracker.milestone.web.model.MileStoneView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("MileStone")
public class MileStoneTest {

  private MileStoneQuery sampleMileStoneQuery;

  @Nested
  @DisplayName("POJO")
  public class PojoTest {

    @BeforeEach
    private void beforeEach() {
      sampleMileStoneQuery = MileStoneQuery
          .of("1차 목표", "1차 목표 상세 설명\n스켈레톤 코드 구성", LocalDate.now().plusDays(7), new ArrayList<>());
    }

    @DisplayName("MileStoneQuery 로 MileStone 을 만듭니다")
    @Test
    void makeMileStoneByMileStoneQuery() {
      // given

      // when
      MileStone mileStone = MileStone.from(sampleMileStoneQuery);

      // then
      assertThat(mileStone.getId()).isNull();
      assertThat(mileStone.getTitle()).isEqualTo(sampleMileStoneQuery.getTitle());
      assertThat(mileStone.getDescription()).isEqualTo(sampleMileStoneQuery.getDescription());
      assertThat(mileStone.getDueDate()).isEqualTo(sampleMileStoneQuery.getDueDate());
      assertThat(mileStone.getIssues()).hasSize(0);
    }
  }

  @Nested
  @DisplayName("Integration")
  @Transactional
  @SpringBootTest
  public class IntegrationTest {

    @Autowired
    private MileStoneService mileStoneService;

    @Autowired
    private MileStoneRepository mileStoneRepository;

    @BeforeEach
    private void beforeEach() {
      sampleMileStoneQuery = MileStoneQuery
          .of("1차 목표", "1차 목표 상세 설명\n스켈레톤 코드 구성", LocalDate.now().plusDays(7), new ArrayList<>());
    }

    @DisplayName("모든 MileStone 을 가져옵니다")
    @Test
    void getMileStones() {
      // given

      // when
      List<MileStoneView> findMileStoneViews = mileStoneService.getMileStones();

      // then
      assertThat(findMileStoneViews.size()).isEqualTo(3); // MileStone 의 초기 값은 3개 입니다
    }

    @DisplayName("MileStone 을 추가합니다")
    @Transactional
    @Test
    void create() {
      // given

      // when
      MileStone savedMileStone = mileStoneService.create(sampleMileStoneQuery);

      // then
      Optional<MileStone> findOptionalMileStone = mileStoneRepository
          .findById(savedMileStone.getId());
      assertThat(findOptionalMileStone.orElseThrow(NoSuchElementException::new).getId())
          .isEqualTo(savedMileStone.getId());
    }

    @DisplayName("특정 MileStone 을 가져옵니다")
    @Test
    void getMileStone() {
      // given
      MileStone savedMileStone = mileStoneService.create(sampleMileStoneQuery);

      // when
      MileStone findMileStone = mileStoneService.getMileStone(savedMileStone.getId());

      // then
      assertThat(findMileStone).isEqualTo(savedMileStone);
    }

    @DisplayName("MileStone 을 삭제합니다")
    @Transactional
    @Test
    void delete() {
      // given
      MileStone savedMileStone = mileStoneService.create(sampleMileStoneQuery);
      Optional<MileStone> findOptionalMileStone = mileStoneRepository
          .findById(savedMileStone.getId());
      assertThat(findOptionalMileStone.orElseThrow(NoSuchElementException::new).getId())
          .isEqualTo(savedMileStone.getId());

      // when
      mileStoneService.delete(savedMileStone.getId());

      // then
      Optional<MileStone> deletedOptionalMileStone = mileStoneRepository
          .findById(savedMileStone.getId());
      assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> {
        deletedOptionalMileStone.orElseThrow(NoSuchElementException::new);
      });
    }
  }
}
