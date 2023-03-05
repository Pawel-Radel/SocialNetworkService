package com.radel.socialnetworkservice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.radel.socialnetworkservice.api.OperationResult;
import com.radel.socialnetworkservice.api.SocialNetworkPostDto;

public interface SocialNetworkPostService {

    OperationResult<Long> createSocialNetworkPost(SocialNetworkPostDto request);

    OperationResult<Boolean> updateSocialNetworkPost(Long postId, SocialNetworkPostDto request);

    OperationResult<Long> deleteSocialNetworkPost(Long postId);

    SocialNetworkPostDto findById(Long postId);

    List<SocialNetworkPostDto> searchSocialNetworkPosts(Pageable pageable);
}
