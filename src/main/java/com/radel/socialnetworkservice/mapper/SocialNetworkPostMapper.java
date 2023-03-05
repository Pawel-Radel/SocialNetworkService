package com.radel.socialnetworkservice.mapper;

import org.springframework.stereotype.Component;

import com.radel.socialnetworkservice.api.SocialNetworkPostDto;
import com.radel.socialnetworkservice.model.SocialNetworkPost;

@Component
public class SocialNetworkPostMapper {

    public SocialNetworkPostDto mapToDto(SocialNetworkPost socialNetworkPost) {

        return SocialNetworkPostDto.builder()
                .author(socialNetworkPost.getAuthor())
                .content(socialNetworkPost.getContent())
                .postDate(socialNetworkPost.getPostDate())
                .viewCount(socialNetworkPost.getViewCount())
                .build();
    }

    public SocialNetworkPost mapToModel(SocialNetworkPostDto socialNetworkPostDto) {

        return SocialNetworkPost.builder()
                .author(socialNetworkPostDto.getAuthor())
                .content(socialNetworkPostDto.getContent())
                .postDate(socialNetworkPostDto.getPostDate())
                .viewCount(socialNetworkPostDto.getViewCount())
                .build();
    }
}
