package com.radel.socialnetworkservice.model;

import static jakarta.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SocialNetworkPost {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @Setter
    @NotNull
    @Past
    private Date postDate;

    @Setter
    @NotBlank
    private String author;

    @Setter
    @NotBlank
    private String content;

    @Setter
    @Min(value = 0, message = "View count must be greater than or equal to 0")
    @NotNull
    private Integer viewCount;
}
