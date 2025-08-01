package com.hnjee.jemilog.mission;

import com.hnjee.jemilog.tag.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query("select m from Mission m where m.name like %:keyword% or m.description like %:keyword%")
    List<Mission> searchByKeyword(@Param("keyword") String keyword);
}
