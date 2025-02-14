package com.aic.edudemo.vuebackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "verified")
@Builder
public class Verified {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verified_id")
    private Integer requestId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "審核中"; // 預設值：PENDING

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_at", nullable = false, updatable = false)
    private Date requestAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "reviewer_id")
    private Integer reviewerId;

    @Column(name = "reviewer_role", length = 20)
    private String reviewerRole;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    /**
     * `@PrePersist` 確保 `requested_at` 和 `status` 具有預設值
     */
    @PrePersist
    protected void onCreate() {
        if (this.requestAt == null) {
            this.requestAt = new Date(); // 預設申請時間
        }
        if (this.status == null) {
            this.status = "PENDING"; // 預設狀態
        }
    }

    /**
     * `@PreUpdate` 確保 `reviewed_at` 會自動更新
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date(); // 更新時修改 `reviewed_at`
    }

    /**
     * Builder 設定預設值，確保 `builder()` 使用時不會傳 `null`
     */
    public static VerifiedBuilder builder() {
        return new VerifiedBuilder()
                .status("審核中")
                .requestAt(new Date());
    }
}