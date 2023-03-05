package com.radel.socialnetworkservice.testData;

import java.util.Date;
import java.util.List;

import com.radel.socialnetworkservice.api.SocialNetworkPostDto;
import com.radel.socialnetworkservice.model.SocialNetworkPost;

public class SocialNetworkPostTestData {

    public static SocialNetworkPost buildSocialNetworkPostFromRequest(SocialNetworkPostDto request) {
        return SocialNetworkPost.builder()
                .id(1L)
                .postDate(request.getPostDate())
                .content(request.getContent())
                .viewCount(request.getViewCount())
                .author(request.getAuthor())
                .build();
    }

    public static SocialNetworkPostDto getExampleSocialNetworkPostDto() {
        return new SocialNetworkPostDto(new Date(), "Author", "Content", 3);
    }

    public static SocialNetworkPostDto getExampleSocialNetworkPostRequest() {
        return new SocialNetworkPostDto(new Date(), "Author", "Content", 3);
    }

    public static SocialNetworkPost getExampleSocialNetworkPost() {
        return new SocialNetworkPost(1L, new Date(), "Author", "Content", 3);
    }

    public static SocialNetworkPostDto buildNotValidSocialNetworkPostDto() {
        return SocialNetworkPostDto.builder()
                .author("Author")
                .content("Content")
                .postDate(new Date())
                .viewCount(-1)
                .build();
    }

    public static List<SocialNetworkPost> getExampleSocialNetworkPostList() {
        return List.of(
                new SocialNetworkPost(null, new Date(), "Autor", "Content1", 0),
                new SocialNetworkPost(null, new Date(), "Autor2", "Content2", 1),
                new SocialNetworkPost(null, new Date(), "Autor3", "Content3", 0),
                new SocialNetworkPost(null, new Date(), "Autor4", "Content4", 2),
                new SocialNetworkPost(null, new Date(), "Autor5", "Content5", 11),
                new SocialNetworkPost(null, new Date(), "Autor6", "Content6", 3),
                new SocialNetworkPost(null, new Date(), "Autor7", "Content7", 10),
                new SocialNetworkPost(null, new Date(), "Autor8", "Content8", 4),
                new SocialNetworkPost(null, new Date(), "Autor9", "Content9", 9),
                new SocialNetworkPost(null, new Date(), "Autor10", "Content10", 5),
                new SocialNetworkPost(null, new Date(), "Autor11", "Content11", 0),
                new SocialNetworkPost(null, new Date(), "Autor12", "Content12", 6),
                new SocialNetworkPost(null, new Date(), "Autor13", "Content13", 0),
                new SocialNetworkPost(null, new Date(), "Autor14", "Content14", 7),
                new SocialNetworkPost(null, new Date(), "Autor15", "Content15", 0),
                new SocialNetworkPost(null, new Date(), "Autor16", "Content16", 8),
                new SocialNetworkPost(null, new Date(), "Autor17", "Content17", 0),
                new SocialNetworkPost(null, new Date(), "Autor18", "Content18", 0),
                new SocialNetworkPost(null, new Date(), "Autor19", "Content19", 0)
        );
    }

}
