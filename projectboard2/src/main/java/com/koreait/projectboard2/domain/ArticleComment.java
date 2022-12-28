package com.koreait.projectboard2.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private long id;
    private long article;   //게시글 (id)
    private String content;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
