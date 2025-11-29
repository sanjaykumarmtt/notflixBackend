package com.example.NetfixClone.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.NetfixClone.Entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{
	

}
