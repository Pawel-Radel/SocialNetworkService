package com.radel.socialnetworkservice.mapper;

import static com.radel.socialnetworkservice.testData.SocialNetworkPostTestData.getExampleSocialNetworkPost;
import static com.radel.socialnetworkservice.testData.SocialNetworkPostTestData.getExampleSocialNetworkPostDto;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import com.radel.socialnetworkservice.api.SocialNetworkPostDto;
import com.radel.socialnetworkservice.model.SocialNetworkPost;

class SocialNetworkPostMapperTest {

    private final SocialNetworkPostMapper mapper = new SocialNetworkPostMapper();

    @Test
    public void testMapToDto() {
        // given
        SocialNetworkPost model = getExampleSocialNetworkPost();

        // when
        SocialNetworkPostDto dto = mapper.mapToDto(model);

        // then
        assertEquals(model.getAuthor(), dto.getAuthor());
        assertEquals(model.getContent(), dto.getContent());
        assertEquals(model.getPostDate(), dto.getPostDate());
        assertEquals(model.getViewCount(), dto.getViewCount());
    }

    @Test
    public void testMapToModel() {
        // given
        SocialNetworkPostDto dto = getExampleSocialNetworkPostDto();

        // when
        SocialNetworkPost model = mapper.mapToModel(dto);

        // then
        assertEquals(model.getAuthor(), dto.getAuthor());
        assertEquals(model.getContent(), dto.getContent());
        assertEquals(model.getPostDate(), dto.getPostDate());
        assertEquals(model.getViewCount(), dto.getViewCount());
    }
}
