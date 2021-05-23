package com.googongs.kids.googonskids.repository;

import com.googongs.kids.googonskids.vo.GpsVideo;
import com.googongs.kids.googonskids.vo.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpsVideoRepository extends JpaRepository<GpsVideo, Long> {
}
