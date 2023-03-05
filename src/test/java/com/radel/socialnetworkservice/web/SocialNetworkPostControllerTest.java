package com.radel.socialnetworkservice.web;

import static com.radel.socialnetworkservice.testData.SocialNetworkPostTestData.buildNotValidSocialNetworkPostDto;
import static com.radel.socialnetworkservice.testData.SocialNetworkPostTestData.getExampleSocialNetworkPost;
import static com.radel.socialnetworkservice.testData.SocialNetworkPostTestData.getExampleSocialNetworkPostDto;
import static com.radel.socialnetworkservice.testData.SocialNetworkPostTestData.getExampleSocialNetworkPostList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radel.socialnetworkservice.api.OperationResult;
import com.radel.socialnetworkservice.api.SocialNetworkPostDto;
import com.radel.socialnetworkservice.model.SocialNetworkPost;
import com.radel.socialnetworkservice.repository.SocialNetworkPostRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SocialNetworkPostControllerTest {

    public static final String POST_URL = "/post";
    public static final String POST_URL_WITH_POST_ID = "/post/{postId}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SocialNetworkPostRepository socialNetworkPostRepository;

    @Test
    void createSocialNetworkPost_ValidRequest_ReturnsOk() throws Exception {

        // given
        SocialNetworkPostDto request = getExampleSocialNetworkPostDto();

        // when
        MvcResult result = mockMvc.perform(post(POST_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        // then
        Long postId = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<OperationResult<Long>>() {
        }).getResult();
        Optional<SocialNetworkPost> post = socialNetworkPostRepository.findById(postId);
        assertTrue(post.isPresent());
        assertEquals(request.getAuthor(), post.get().getAuthor());
        assertEquals(request.getContent(), post.get().getContent());
        assertEquals(request.getPostDate(), post.get().getPostDate());
        assertEquals(request.getViewCount(), post.get().getViewCount());
    }

    @Test
    void createSocialNetworkPost_NotValidRequest_ReturnsBadRequest() throws Exception {

        // when
        mockMvc.perform(post(POST_URL)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateSocialNetworkPost_Success() throws Exception {

        // given
        SocialNetworkPost post = socialNetworkPostRepository.save(getExampleSocialNetworkPost());
        Long postId = post.getId();
        SocialNetworkPostDto updateRequest = getExampleSocialNetworkPostDto();

        // when
        MvcResult result = mockMvc.perform(put(POST_URL_WITH_POST_ID, postId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // then
        OperationResult<Boolean> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertTrue(response.isSuccess());
        assertTrue(response.getResult());

        SocialNetworkPost updatedPost = socialNetworkPostRepository.findById(postId).orElseThrow();
        assertEquals(updateRequest.getContent(), updatedPost.getContent());
        assertEquals(updateRequest.getAuthor(), updatedPost.getAuthor());
        assertEquals(updateRequest.getPostDate(), updatedPost.getPostDate());
        assertEquals(updateRequest.getViewCount(), updatedPost.getViewCount());
    }

    @Test
    public void testUpdateSocialNetworkPost_PostNotFound() throws Exception {
        // given
        Long nonExistingPostId = 999L;
        SocialNetworkPostDto updateRequest = getExampleSocialNetworkPostDto();

        // when
        mockMvc.perform(put(POST_URL_WITH_POST_ID, nonExistingPostId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateSocialNetworkPost_InvalidRequest() throws Exception {
        // given
        SocialNetworkPost post = socialNetworkPostRepository.save(getExampleSocialNetworkPost());
        Long postId = post.getId();
        SocialNetworkPostDto updateRequest = buildNotValidSocialNetworkPostDto();

        // when
        mockMvc.perform(put(POST_URL_WITH_POST_ID, postId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getSocialNetworkPostById_ExistingId_ReturnsPost() throws Exception {
        // given
        SocialNetworkPost post = socialNetworkPostRepository.save(getExampleSocialNetworkPost());
        Long postId = post.getId();

        // when
        MvcResult result = mockMvc.perform(get(POST_URL_WITH_POST_ID, postId))
                .andExpect(status().isOk())
                .andReturn();

        // then
        SocialNetworkPostDto response = objectMapper.readValue(result.getResponse().getContentAsString(), SocialNetworkPostDto.class);
        assertEquals(post.getAuthor(), response.getAuthor());
        assertEquals(post.getContent(), response.getContent());
        assertEquals(post.getPostDate().toInstant(), response.getPostDate().toInstant());
        assertEquals(post.getViewCount(), response.getViewCount());
    }

    @Test
    void getSocialNetworkPostById_NonexistentId_ReturnsNotFound() throws Exception {
        // given
        Long wrongId = 12345L;

        // when
        mockMvc.perform(get("/post/{id}", wrongId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSocialNetworkPost_ExistingId_DeletesPost() throws Exception {
        // given
        SocialNetworkPost post = socialNetworkPostRepository.save(getExampleSocialNetworkPost());
        Long postId = post.getId();

        // when
        MvcResult result = mockMvc.perform(delete(POST_URL_WITH_POST_ID, postId))
                .andExpect(status().isOk())
                .andReturn();

        // then
        OperationResult<Long> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        assertTrue(response.isSuccess());
        assertEquals(postId, response.getResult());
        assertFalse(socialNetworkPostRepository.findById(postId).isPresent());
    }

    @Test
    void deleteSocialNetworkPost_NonExistingId_ReturnsError() throws Exception {
        // given
        Long nonExistingId = 321L;

        // when
        mockMvc.perform(delete(POST_URL_WITH_POST_ID, nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void searchSocialNetworkPosts_WithoutPageable_ReturnSortedByViewCountWithLimit10() throws Exception {

        socialNetworkPostRepository.saveAll(getExampleSocialNetworkPostList());

        // when
        MvcResult result = mockMvc.perform(get(POST_URL))
                .andExpect(status().isOk())
                .andReturn();

        // then
        List<SocialNetworkPostDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertEquals(10, response.size());

        for (int i = 0; i < response.size() - 1; i++) {
            assertThat(response.get(i).getViewCount()).isGreaterThanOrEqualTo(response.get(i + 1).getViewCount());
        }
    }

    @Test
    void searchSocialNetworkPosts_WithPageable_ReturnSortedByPageable() throws Exception {

        socialNetworkPostRepository.saveAll(getExampleSocialNetworkPostList());

        // when
        Integer listSize = 5;
        MvcResult result = mockMvc.perform(get(POST_URL)
                        .param("page", "0")
                        .param("size", listSize.toString())
                        .param("sort", "Author")
                        .param("direction", "ASC")
                )
                .andExpect(status().isOk())
                .andReturn();

        // then
        List<SocialNetworkPostDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertEquals(listSize, response.size());

        for (int i = 0; i < response.size() - 1; i++) {
            assertThat(response.get(i).getAuthor().compareTo(response.get(i + 1).getAuthor())).isLessThanOrEqualTo(0);
        }
    }
}
