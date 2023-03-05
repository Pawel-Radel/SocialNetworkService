package com.radel.socialnetworkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radel.socialnetworkservice.model.SocialNetworkPost;

public interface SocialNetworkPostRepository extends JpaRepository<SocialNetworkPost, Long> {
}
