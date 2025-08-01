package com.hnjee.jemilog.bookmark;

import com.hnjee.jemilog.member.Member;
import com.hnjee.jemilog.mission.Mission;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {
    @Autowired BookmarkRepository bookmarkRepository;

    @Transactional
    public Long save(Member member, Mission mission) {
        Bookmark bookmark = new Bookmark(member, mission);
        return bookmarkRepository.save(bookmark).getId();
    }

    @Transactional
    public void delete(Bookmark bookmark){
        bookmarkRepository.delete(bookmark);
    }

    public List<Bookmark> findByMember(Member member){
        return bookmarkRepository.findByMember(member);
    }

    public Optional<Bookmark> findByMemberAndMission(Member member, Mission mission){
        return findByMemberAndMission(member, mission);
    }
}
