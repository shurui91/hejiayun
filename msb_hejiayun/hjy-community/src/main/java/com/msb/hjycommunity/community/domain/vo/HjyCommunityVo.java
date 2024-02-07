package com.msb.hjycommunity.community.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

public class HjyCommunityVo implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long communityId;

    /**
     * 小区名称
     */
    private String communityName;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    @Override
    public String toString() {
        return "HjyCommunityVo{" +
                "communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                '}';
    }
}
