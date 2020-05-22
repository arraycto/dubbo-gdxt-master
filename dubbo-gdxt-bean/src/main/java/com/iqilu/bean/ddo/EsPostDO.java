package com.iqilu.bean.ddo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Es说因映射
 *
 * @author zhangyicheng
 * @date 2020/05/22
 */
@Data
@Document(indexName="projectname",type="post",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
public class EsPostDO {
    @Id
    private String id;

    private String title;

    private String content;

    private Integer userId;

    private Integer weight;
}