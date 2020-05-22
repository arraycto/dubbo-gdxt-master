package com.iqilu.controller;

import com.iqilu.bean.ddo.EsPostDO;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * ES操作类
 *
 * @author zhangyicheng
 * @date 2020/05/22
 */
@RestController
@RequestMapping(value = "es")
public class EsCaseController {

    /**
     * ES操作模板
     *
     * @date 2020/05/22
     */
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 1.1 单字符串全文查询
     * 单字符串模糊查询，默认排序。将从所有字段中查找包含传来的word分词后字符串的数据集
     *
     * @date 2020/05/22
     */
    @RequestMapping(value = "/singleWord", method = RequestMethod.GET)
    public Object singleTitle(String word, @PageableDefault Pageable pageable) {
        // 使用queryStringQuery完成单字符串查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                // queryStringQuery
                .withQuery(queryStringQuery(word))
                .withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EsPostDO.class);
    }

    /**
     * 1.2 单字符串全文查询
     * 单字符串模糊查询，单字段排序 - 按照Weight大小排序, 比字符串匹配度的权重更大
     *
     * @date 2020/05/22
     */
    @RequestMapping(value = "/singleWord1", method = RequestMethod.GET)
    public Object singlePost(String word, @PageableDefault(sort = "weight",
            direction = Sort.Direction.DESC) Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                // queryStringQuery
                .withQuery(queryStringQuery(word))
                .withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EsPostDO.class);
    }

    /**
     * 2.1 某字段按字符串模糊查询
     * 查询某个字段中模糊包含目标字符串，使用matchQuery
     *
     * @date 2020/05/22
     */
    @RequestMapping(value = "/singleMatch", method = RequestMethod.GET)
    public Object singleMatch(String content, Integer userId, @PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                // matchQuery
                .withQuery(matchQuery("content", content))
                .withPageable(pageable).build();

///        SearchQuery searchQuery = new NativeSearchQueryBuilder()
///                .withQuery(matchQuery("userId", userId))
///                .withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EsPostDO.class);
    }

    /**
     * 3. PhraseMatch查询，短语匹配
     * 单字段对某短语进行匹配查询，短语分词的顺序会影响结果
     *
     * @date 2020/05/22
     */
    @RequestMapping(value = "/singlePhraseMatch", method = RequestMethod.GET)
    public Object singlePhraseMatch(String content, @PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                // matchPhraseQuery
                .withQuery(matchPhraseQuery("content", content))
                .withPageable(pageable).build();

        // 就是分词后，中间能间隔几个位置的也能查出来，可以使用slop参数。
///        SearchQuery searchQuery = new NativeSearchQueryBuilder()
///                .withQuery(matchPhraseQuery("content", content).slop(2))
///                .withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EsPostDO.class);
    }

    /**
     * 4. term查询
     * term最严格的匹配，即不分词匹配，你传来什么值就会拿你传的值去做完全匹配
     *
     * @date 2020/05/22
     */
    @RequestMapping(value = "/singleTerm", method = RequestMethod.GET)
    public Object singleTerm(Integer userId, @PageableDefault Pageable pageable) {
        // 不对传来的值分词，去找完全匹配的
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                // termQuery
                .withQuery(termQuery("userId", userId))
                .withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EsPostDO.class);
    }

    /**
     * 5. multi_match多个字段匹配某字符串
     * 我们希望title，content两个字段去匹配某个字符串，只要任何一个字段包括该字符串即可，就可以使用
     *
     * @date 2020/05/22
     */
    @RequestMapping(value = "/multiMatch", method = RequestMethod.GET)
    public Object singleUserId(String title, @PageableDefault(sort = "weight",
            direction = Sort.Direction.DESC) Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                // multiMatchQuery
                .withQuery(multiMatchQuery(title, "title", "content"))
                .withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EsPostDO.class);
    }

    /**
     * 完全包含查询
     *
     * @date 2020/05/22
     */
    @RequestMapping(value = "/contain", method = RequestMethod.GET)
    public Object contain(String title) {
        // 单字段包含所有输入
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("title", title)
                        .operator(Operator.AND)).build();

        // 单字段包含所有输入(按比例包含)
///        SearchQuery searchQuery = new NativeSearchQueryBuilder()
///                .withQuery(matchQuery("title", title)
///                        .operator(Operator.AND)
///                        .minimumShouldMatch("75%")).build();
        return elasticsearchTemplate.queryForList(searchQuery, EsPostDO.class);
    }


    /**
     * 多字段合并查询
     *
     * @date 2020/05/22
     */
    @RequestMapping(value = "/bool", method = RequestMethod.GET)
    public Object bool(String title, Integer userId, Integer weight) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery()
                        .must(termQuery("userId", userId))
                        .should(rangeQuery("weight")
                                .lt(weight))
                        .must(matchQuery("title", title)))
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, EsPostDO.class);
    }


}
