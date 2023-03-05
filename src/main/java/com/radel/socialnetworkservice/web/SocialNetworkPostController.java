package com.radel.socialnetworkservice.web;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radel.socialnetworkservice.api.OperationResult;
import com.radel.socialnetworkservice.api.SocialNetworkPostDto;
import com.radel.socialnetworkservice.service.SocialNetworkPostService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/post")
public class SocialNetworkPostController {

    private final SocialNetworkPostService socialNetworkPostService;

    @PostMapping
    OperationResult<Long> createSocialNetworkPost(@RequestBody @Valid SocialNetworkPostDto request) {
        log.info("Create social network post request received with request: {}", request);
        return socialNetworkPostService.createSocialNetworkPost(request);
    }

    @PutMapping("/{postId}")
    OperationResult<Boolean> updateSocialNetworkPost(@PathVariable Long postId, @RequestBody @Valid SocialNetworkPostDto request) {
        log.info("Update social network post request received with id: {} and request: {}", postId, request);
        return socialNetworkPostService.updateSocialNetworkPost(postId, request);
    }

    @DeleteMapping("/{postId}")
    OperationResult<Long> deleteSocialNetworkPost(@PathVariable Long postId) {
        log.info("Delete social network post request received with id: {}", postId);
        return socialNetworkPostService.deleteSocialNetworkPost(postId);
    }

    @GetMapping("/{postId}")
    SocialNetworkPostDto findById(@PathVariable Long postId) {
        log.info("Find social network post request received with id: {}", postId);
        return socialNetworkPostService.findById(postId);
    }

    @GetMapping
    List<SocialNetworkPostDto> searchSocialNetworkPosts(@PageableDefault(page = 0, size = 10, sort = "viewCount", direction = DESC) Pageable pageable) {
        log.info("Search social network post request received with pageable: {}", pageable);
        return socialNetworkPostService.searchSocialNetworkPosts(pageable);
    }
}
