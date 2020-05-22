package com.iqilu.dao.esdao;

import com.iqilu.bean.ddo.EsManagerDO;
import com.iqilu.bean.ddo.EsPostDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 继承 ElasticsearchRepository 实现功能
 * 用作基本的增删改查: ElasticsearchRepository:
 * 相对复杂使用: ElasticsearchTemplate
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
public interface EsPostRepository extends ElasticsearchRepository<EsPostDO, String> {

}
