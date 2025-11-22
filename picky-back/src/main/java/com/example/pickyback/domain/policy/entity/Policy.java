package com.example.pickyback.domain.policy.entity;

import com.example.pickyback.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "policy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(length = 500)
    private String url;

    @Column(name = "limit_income_bracket")
    private Long limitIncomeBracket;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(nullable = false, length = 100)
    private String host;

    @Column(length = 255)
    private String qualifications;

    @Column(nullable = false)
    private boolean always;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;


}
