package com.msb.hjycommunity.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.hjycommunity.community.domain.HjyCommunity;
import com.msb.hjycommunity.community.domain.dto.HjyCommunityDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HjycommunityMapper extends BaseMapper<HjyCommunity> {
    /*
    SELECT
        *,
        s1.`name` AS community_province_name,
        s2.`name` AS community_city_name,
        s3.`name` AS community_town_name
    FROM hjy_community hc
    LEFT JOIN sys_area s1 ON hc.`community_province_code` = s1.`code`
    LEFT JOIN sys_area s2 ON hc.`community_city_code` = s2.`code`
    LEFT JOIN sys_area s3 ON hc.`community_town_code` = s3.`code`
    WHERE
        (community_name IS NOT NULL AND community_name != '') AND hc.community_name LIKE CONCAT('%', community_name, '%')
        OR
        (community_code IS NOT NULL AND community_code != '') AND hc.community_code = community_code;
    */
    @Select("<script>SELECT \n" +
            "    *,\n" +
            "    s1.`name` AS communityProvinceName,\n" +
            "    s2.`name` AS communityCityName,\n" +
            "    s3.`name` AS communityTownName\n" +
            "FROM hjy_community hc \n" +
            "LEFT JOIN sys_area s1 ON hc.`community_province_code` = s1.`code`\n" +
            "LEFT JOIN sys_area s2 ON hc.`community_city_code` = s2.`code`\n" +
            "LEFT JOIN sys_area s3 ON hc.`community_town_code` = s3.`code`" +
            "<where>" +
            "<if test=\"communityName !=null and communityName != ''\">" +
            "hc.community_name like concat('%',#{communityName},'%')" +
            "</if> " +

            "<if test=\"communityCode !=null and communityCode != ''\">" +
            "and hc.community_code = #{communityCode}" +
            "</if> " +
            "</where>" +
            "</script>")
    List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity);
}
