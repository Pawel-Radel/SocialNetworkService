package com.radel.socialnetworkservice.api;

import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialNetworkPostDto {

    @NotNull
    @Past(message = "Post date must be in the past")
    private Date postDate;

    @NotBlank
    private String author;

    @NotBlank
    private String content;

    @Min(value = 0, message = "View count must be greater than or equal to 0")
    private Integer viewCount = 0;
}
