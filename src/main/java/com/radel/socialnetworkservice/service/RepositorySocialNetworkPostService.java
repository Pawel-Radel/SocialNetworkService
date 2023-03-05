package com.radel.socialnetworkservice.service;

import static com.radel.socialnetworkservice.api.exception.SocialNetworkPostException.postNotFound;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.radel.socialnetworkservice.api.OperationResult;
import com.radel.socialnetworkservice.api.SocialNetworkPostDto;
import com.radel.socialnetworkservice.mapper.SocialNetworkPostMapper;
import com.radel.socialnetworkservice.model.SocialNetworkPost;
import com.radel.socialnetworkservice.repository.SocialNetworkPostRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class RepositorySocialNetworkPostService implements SocialNetworkPostService {

    private final SocialNetworkPostRepository socialNetworkPostRepository;

    private final SocialNetworkPostMapper socialNetworkPostMapper;

    @Override
    public OperationResult<Long> createSocialNetworkPost(SocialNetworkPostDto request) {

        log.info("Attempt to create social network post with request: {}", request);

        SocialNetworkPost socialNetworkPost = socialNetworkPostMapper.mapToModel(request);

        SocialNetworkPost savedNetworkPost = socialNetworkPostRepository.save(socialNetworkPost);

        log.info("Saved social network post: {}", socialNetworkPost);

        return new OperationResult<>(savedNetworkPost.getId());
    }

    @Override
    public OperationResult<Boolean> updateSocialNetworkPost(Long postId, SocialNetworkPostDto request) {

        log.info("Attempt to update social network post with id: {}. Request data: {}", postId, request);

        SocialNetworkPost socialNetworkPost = socialNetworkPostRepository.findById(postId).orElseThrow(() -> postNotFound(postId));

        SocialNetworkPost postWithUpdatedData = updateSocialNetworkPost(request, socialNetworkPost);

        SocialNetworkPost updatedSocialNetworkPost = socialNetworkPostRepository.save(postWithUpdatedData);

        log.info("Updated social network post : {}", updatedSocialNetworkPost);

        return new OperationResult<>(true);
    }

    @Override
    public OperationResult<Long> deleteSocialNetworkPost(Long postId) {

        log.info("Attempt to delete social network post with id: {}", postId);

        SocialNetworkPost socialNetworkPost = socialNetworkPostRepository.findById(postId).orElseThrow(() -> postNotFound(postId));

        socialNetworkPostRepository.delete(socialNetworkPost);

        log.info("Successfully deleted post with id: {}", postId);

        return new OperationResult<>(postId);
    }

    @Override
    public SocialNetworkPostDto findById(Long postId) {

        log.info("Attempt to find social network post with id: {}", postId);

        SocialNetworkPost socialNetworkPost = socialNetworkPostRepository.findById(postId).orElseThrow(() -> postNotFound(postId));

        SocialNetworkPostDto socialNetworkPostDto = socialNetworkPostMapper.mapToDto(socialNetworkPost);

        log.info("Returning social network post Dto: {}", socialNetworkPostDto);

        return socialNetworkPostDto;
    }

    @Override
    public List<SocialNetworkPostDto> searchSocialNetworkPosts(Pageable pageable) {

        log.info("Attempt to search social network posts with pageable: {}", pageable);

        Page<SocialNetworkPost> page = socialNetworkPostRepository.findAll(pageable);
        List<SocialNetworkPost> posts = page.getContent();

        return posts.stream().map(socialNetworkPostMapper::mapToDto).toList();
    }

    private static SocialNetworkPost updateSocialNetworkPost(SocialNetworkPostDto request, SocialNetworkPost socialNetworkPost) {
        socialNetworkPost.setPostDate(request.getPostDate());
        socialNetworkPost.setAuthor(request.getAuthor());
        socialNetworkPost.setContent(request.getContent());
        socialNetworkPost.setViewCount(request.getViewCount());

        return socialNetworkPost;
    }

}

