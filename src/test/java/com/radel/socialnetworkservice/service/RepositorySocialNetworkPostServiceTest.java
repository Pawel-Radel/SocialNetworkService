package com.radel.socialnetworkservice.service;

import static com.radel.socialnetworkservice.testData.SocialNetworkPostTestData.buildSocialNetworkPostFromRequest;
import static com.radel.socialnetworkservice.testData.SocialNetworkPostTestData.getExampleSocialNetworkPost;
import static com.radel.socialnetworkservice.testData.SocialNetworkPostTestData.getExampleSocialNetworkPostDto;
import static com.radel.socialnetworkservice.testData.SocialNetworkPostTestData.getExampleSocialNetworkPostRequest;
import static java.util.Calendar.JANUARY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.radel.socialnetworkservice.api.OperationResult;
import com.radel.socialnetworkservice.api.SocialNetworkPostDto;
import com.radel.socialnetworkservice.api.exception.SocialNetworkPostException;
import com.radel.socialnetworkservice.mapper.SocialNetworkPostMapper;
import com.radel.socialnetworkservice.model.SocialNetworkPost;
import com.radel.socialnetworkservice.repository.SocialNetworkPostRepository;

@ExtendWith(MockitoExtension.class)
class RepositorySocialNetworkPostServiceTest {

    @Mock
    private SocialNetworkPostRepository socialNetworkPostRepository;

    @Mock
    private SocialNetworkPostMapper socialNetworkPostMapper;

    @InjectMocks
    private RepositorySocialNetworkPostService socialNetworkPostService;

    @Test
    void shouldCreateSocialNetworkPostWhenRequestIsValid() {
        // given
        SocialNetworkPostDto request = getExampleSocialNetworkPostDto();

        SocialNetworkPost socialNetworkPost = buildSocialNetworkPostFromRequest(request);

        when(socialNetworkPostMapper.mapToModel(request)).thenReturn(socialNetworkPost);
        when(socialNetworkPostRepository.save(socialNetworkPost)).thenReturn(socialNetworkPost);

        // when
        OperationResult<Long> result = socialNetworkPostService.createSocialNetworkPost(request);

        // then
        verify(socialNetworkPostMapper).mapToModel(request);
        verify(socialNetworkPostRepository).save(socialNetworkPost);

        assertNotNull(result);
        assertEquals(socialNetworkPost.getId(), result.getResult());
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldThrowsSocialNetworkPostExceptionIfRequestIsNull() {
        // then
        assertThrows(NullPointerException.class, () -> socialNetworkPostService.createSocialNetworkPost(null));
    }

    @Test
    public void shouldUpdateSocialNetworkPostWhenRequestIsValid() {
        // given
        Long postId = 1L;
        Date updatedDate = new Date();

        SocialNetworkPostDto request = new SocialNetworkPostDto(updatedDate, "Author", "Content", 12);
        SocialNetworkPost existingPost = new SocialNetworkPost(postId, new Date(119, JANUARY, 1), "Original Author", "Original content", 12);
        SocialNetworkPost postWithUpdatedData = new SocialNetworkPost(postId, request.getPostDate(), request.getAuthor(), request.getContent(), request.getViewCount());

        // mock repository behavior
        when(socialNetworkPostRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(socialNetworkPostRepository.save(any(SocialNetworkPost.class))).thenReturn(postWithUpdatedData);

        // when
        OperationResult<Boolean> result = socialNetworkPostService.updateSocialNetworkPost(postId, request);

        // then
        assertTrue(result.isSuccess());
        assertTrue(result.getResult());

        verify(socialNetworkPostRepository).findById(postId);
        verify(socialNetworkPostRepository).save(any(SocialNetworkPost.class));
    }

    @Test
    public void shouldThrowSocialNetworkPostExceptionIfNotFoundPost() {
        // given
        Long postId = 1L;
        SocialNetworkPostDto request = getExampleSocialNetworkPostRequest();

        when(socialNetworkPostRepository.findById(postId)).thenReturn(Optional.empty());

        // then
        assertThrows(SocialNetworkPostException.class, () -> socialNetworkPostService.updateSocialNetworkPost(postId, request));
    }

    @Test
    public void shouldDeletePostById() {
        // given
        Long postId = 1L;
        SocialNetworkPost socialNetworkPost = getExampleSocialNetworkPost();

        when(socialNetworkPostRepository.findById(postId)).thenReturn(Optional.of(socialNetworkPost));

        // when
        OperationResult<Long> result = socialNetworkPostService.deleteSocialNetworkPost(postId);

        // then
        verify(socialNetworkPostRepository).delete(socialNetworkPost);
        assertEquals(postId, result.getResult());
        assertTrue(result.isSuccess());
    }

    @Test()
    public void shouldThrowSocialNetworkPostExceptionWhenDeletingNonExistingPost() {

        // given
        Long postId = 1L;
        when(socialNetworkPostRepository.findById(postId)).thenReturn(Optional.empty());

        // then
        assertThrows(SocialNetworkPostException.class, () -> socialNetworkPostService.deleteSocialNetworkPost(postId));
    }

    @Test
    public void shouldReturnPostById() {
        // given
        Long postId = 1L;
        SocialNetworkPost socialNetworkPost = getExampleSocialNetworkPost();
        SocialNetworkPostDto socialNetworkPostDto = getExampleSocialNetworkPostRequest();

        when(socialNetworkPostRepository.findById(postId)).thenReturn(Optional.of(socialNetworkPost));
        when(socialNetworkPostMapper.mapToDto(socialNetworkPost)).thenReturn(socialNetworkPostDto);

        // when
        SocialNetworkPostDto result = socialNetworkPostService.findById(postId);

        // then
        assertNotNull(result);
        assertEquals(result.getAuthor(), socialNetworkPost.getAuthor());
        assertEquals(result.getContent(), socialNetworkPost.getContent());
        assertEquals(result.getPostDate(), socialNetworkPost.getPostDate());
        assertEquals(result.getViewCount(), socialNetworkPost.getViewCount());

        verify(socialNetworkPostRepository).findById(postId);
        verify(socialNetworkPostMapper).mapToDto(socialNetworkPost);
    }

    @Test
    public void shouldThrowsSocialNetworkPostExceptionWhenSearchingNotExistingPost() {
        // given
        Long postId = 1L;

        when(socialNetworkPostRepository.findById(postId)).thenReturn(Optional.empty());

        // then
        assertThrows(SocialNetworkPostException.class, () -> socialNetworkPostService.findById(postId));
    }
}


