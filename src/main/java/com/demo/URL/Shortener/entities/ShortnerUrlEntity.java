package com.demo.URL.Shortener.entities;

import com.demo.URL.Shortener.dtos.ShortnerUrlDto;
import com.demo.URL.Shortener.utils.ShortCodeGeneratorUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortnerUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long task_id;
    private String url;

    @Column(unique = true)
    private String shortCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ShortnerUrlEntity of(ShortnerUrlDto shortnerUrlDto) {
        return ShortnerUrlEntity.builder()
                .url(shortnerUrlDto.getUrl())
                .shortCode(ShortCodeGeneratorUtil.generate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void updateUrl(String url) {
        this.url = url;
        this.updatedAt = LocalDateTime.now();
    }

}
