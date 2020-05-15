package com.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
@Api(tags = { "接口测试" })
public class TestController {

    @Autowired
    private RestHighLevelClient rhlClient;

    @ApiOperation(value = "es测试查询", notes = "")
    @PostMapping(value = "/test/info/select")
    public void getRequest() throws IOException {
        GetRequest getRequest = new GetRequest("lib3", "1");
        GetResponse response = rhlClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
//        rhlClient.close();
    }

    @ApiOperation(value = "es测试增加", notes = "")
    @PostMapping(value = "/test/info/insert")
    public void insertRequest() throws IOException {
        XContentBuilder doc = XContentFactory.jsonBuilder()
                .startObject()
                .field("id", "1")
                .field("title", "JAVA设计模式之装饰设计模式")
                .field("content", "在不必改变原类文件和使用继承的情况下，动态地扩展一个对象的功能。")
                .field("postdate", "2018-05-20")
                .field("url", "csdn.net/79239072")
                .endObject();
        IndexResponse response = rhlClient.index(new IndexRequest("index3").id("10").source(doc), RequestOptions.DEFAULT);
        System.out.println(response.status());
//        rhlClient.close();
    }

    @ApiOperation(value = "es测试删除", notes = "")
    @PostMapping(value = "/test/info/delete")
    public void deleteRequest() throws IOException {
        DeleteResponse response = rhlClient.delete(new DeleteRequest("index3", "10"), RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    @ApiOperation(value = "es测试更新", notes = "")
    @PostMapping(value = "/test/info/update")
    public void updateRequest() throws IOException {
        UpdateRequest request = new UpdateRequest();
        request.index("index3")
                .id("10")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "java设计模式之单例模式。")
                        .endObject()
                );
        UpdateResponse update = rhlClient.update(request, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }

    @ApiOperation(value = "es测试upsert", notes = "")
    @PostMapping(value = "/test/info/upsert")
    public void upsertRequest() throws IOException {
        XContentBuilder doc = XContentFactory.jsonBuilder()
                .startObject()
                .field("id", "2")
                .field("title", "JAVA设计模式之工厂模式")
                .field("content", "分为静态工厂、实例工厂")
                .field("postdate", "2018-05-20")
                .field("url", "csdn.net/79239072")
                .endObject();
        IndexRequest insert = new IndexRequest("index3").id("8").source(doc);
        //更新
        UpdateRequest update = new UpdateRequest()
                .index("index3")
                .id("2")
                .doc(XContentFactory.jsonBuilder()
                .startObject()
                .field("title", "java设计模式")
                .endObject()).upsert(insert);
        UpdateResponse response = rhlClient.update(update, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    @ApiOperation(value = "es测试_mget", notes = "")
    @PostMapping(value = "/test/info/mget")
    public void mutiGetRequest() throws IOException {
        MultiGetRequest request = new MultiGetRequest()
                .add("index3", "10")
                .add("index3", "8")
                .add("lib3", "1")
                .add("lib3", "2")
                .add("lib3", "3");
        MultiGetResponse response = rhlClient.mget(request, RequestOptions.DEFAULT);
        for(MultiGetItemResponse item: response.getResponses()){
            GetResponse getResponse = item.getResponse();
            if(getResponse != null && getResponse.isExists()){
                System.out.println(getResponse.getSourceAsString());
            }
        }

    }

    @ApiOperation(value = "es测试_bulk批量操作", notes = "")
    @PostMapping(value = "/test/info/bulk")
    public void bulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest()
                .add(new IndexRequest("lib2").id("8")
                        .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "python")
                        .field("price", 99)
                        .endObject()))
                .add(new IndexRequest("lib2").id("9")
                        .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "VR")
                        .field("price", 29)
                        .endObject()))
                .add(new UpdateRequest().index("index3").id("10")
                        .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "javaee")
                        .endObject()))
                .add(new DeleteRequest("lib8").id("1"));
        BulkResponse response = rhlClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
        if(response.hasFailures()){
            System.out.println("failed");
        }
    }

    @ApiOperation(value = "es测试查询删除操作", notes = "")
    @PostMapping(value = "/test/info/deleteByQuery")
    public void deleteByQueryRequest() throws IOException {
        DeleteByQueryRequest dbr = new DeleteByQueryRequest("index3").setQuery(
                new MatchQueryBuilder("content", "工厂")).setRefresh(true);
        BulkByScrollResponse response = rhlClient.deleteByQuery(dbr, RequestOptions.DEFAULT);
        long count = response.getDeleted();
        System.out.println(count);
    }

    @ApiOperation(value = "es测试match_all操作", notes = "")
    @PostMapping(value = "/test/info/match_all")
    public void matchAllRequest() throws IOException {
        SearchSourceBuilder query = new SearchSourceBuilder()
                .query(QueryBuilders.matchAllQuery()).size(3);
        SearchRequest request = new SearchRequest("lib3").source(query);
        SearchResponse response = rhlClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
//            Map<String, Object> map = hit.getSourceAsMap();
//            for(Map.Entry<String, Object> entry: map.entrySet()){
//                System.out.println("key="+entry.getKey()+", value="+entry.getValue());
//            }
        }
    }

    @ApiOperation(value = "es测试match操作", notes = "")
    @PostMapping(value = "/test/info/match")
    public void matchRequest() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchQuery("interests", "changge")).size(3);
        SearchResponse response = rhlClient.search(new SearchRequest().source(builder), RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for(SearchHit hit: hits){
            System.out.println(hit.getSourceAsString());
//            for(Map.Entry<String, Object> map: hit.getSourceAsMap().entrySet()){
//                System.out.println("key="+map.getKey()+", value="+map.getValue());
//            }
        }
    }

    @ApiOperation(value = "es测试multi_match操作", notes = "")
    @PostMapping(value = "/test/info/multi_match")
    public void mutiMatchRequest() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery("changge", "address", "interests")).size(3);
        SearchResponse response = rhlClient.search(new SearchRequest().source(builder), RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for(SearchHit hit: hits){
            System.out.println(hit.getSourceAsString());
//            for(Map.Entry<String, Object> map: hit.getSourceAsMap().entrySet()){
//                System.out.println("key="+map.getKey()+", value="+map.getValue());
//            }
        }
    }

    @ApiOperation(value = "es测试term操作", notes = "")
    @PostMapping(value = "/test/info/term")
    public void termRequest() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.termQuery("interests", "changge")).size(3);
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(builder), RequestOptions.DEFAULT);
        for(SearchHit hit: response.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }

    @ApiOperation(value = "es测试terms操作", notes = "")
    @PostMapping(value = "/test/info/terms")
    public void termsRequest() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.termsQuery("interests", "changge", "lvyou"))
                .size(3);
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(builder), RequestOptions.DEFAULT);
        for(SearchHit hit: response.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }

    @ApiOperation(value = "es测试range操作", notes = "")
    @PostMapping(value = "/test/info/range")
    public void rangeRequest() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.rangeQuery("birthday").from("1990-12-12").to("2000-12-12"));
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(builder), RequestOptions.DEFAULT);
        for(SearchHit hit: response.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }

    @ApiOperation(value = "es测试prefix操作", notes = "")
    @PostMapping(value = "/test/info/prefix")
    public void prefixRequest() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.prefixQuery("name", "zhao"));
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(builder), RequestOptions.DEFAULT);
        for(SearchHit hit: response.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }

    @ApiOperation(value = "es测试wildcard通配符操作", notes = "")
    @PostMapping(value = "/test/info/wildcard")
    public void wildCardRequest() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.wildcardQuery("name", "zhao*"));
        SearchResponse response = rhlClient.search(
                new SearchRequest("lib3").source(builder), RequestOptions.DEFAULT);
        for(SearchHit hit: response.getHits()){
            System.out.println(hit.getSourceAsString());
        }

    }

    @ApiOperation(value = "es测试fuzzy模糊操作", notes = "")
    @PostMapping(value = "/test/info/fuzzy")
    public void fuzzyRequest() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.fuzzyQuery("interests", "chagge"));
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(builder), RequestOptions.DEFAULT);
        for(SearchHit hit: response.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }

    @ApiOperation(value = "es测试ids操作", notes = "")
    @PostMapping(value = "/test/info/ids")
    public void idsRequest() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.idsQuery().addIds("1", "3"));
        SearchResponse response = rhlClient.search(new SearchRequest("index1").source(builder), RequestOptions.DEFAULT);
        for(SearchHit hit: response.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }

    @ApiOperation(value = "es测试query_string操作", notes = "")
    @PostMapping(value = "/test/info/query_string")
    public void queryStringRequest() throws IOException {
        //queryStringQuery+表示必须包含 -表示不包含  必须满足这两个条件
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.queryStringQuery("+changge -hejiu"));
        //simpleQueryStringQuery 满足任意一个条件
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.simpleQueryStringQuery("+changge -hejiu"));
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(builder), RequestOptions.DEFAULT);
        for(SearchHit hit: response.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }
    //组合查询
    @ApiOperation(value = "es测试complex查询操作", notes = "")
    @PostMapping(value = "/test/info/complex")
    public void complexRequest() throws IOException {
//        SearchSourceBuilder builder = new SearchSourceBuilder()
//                .query(
//                        QueryBuilders.boolQuery()
//                        .must(QueryBuilders.matchQuery("interests", "changge"))
//                        .mustNot(QueryBuilders.matchQuery("interests", "lvyou"))
//                        .should(QueryBuilders.matchQuery("address", "bei jing"))
//                        .filter(QueryBuilders.rangeQuery("birthday").gte("1990-01-01").format("yyyy-MM-dd"))
//                );
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(
                        //不计算相关度分数
                        QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("name", "zhaoliu"))
                );
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(builder), RequestOptions.DEFAULT);
        for(SearchHit hit: response.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }

    @ApiOperation(value = "es测试aggs聚合操作", notes = "")
    @PostMapping(value = "/test/info/aggs")
    public void aggsRequest() throws IOException {
        //最大值
//        SearchSourceBuilder sbuild = new SearchSourceBuilder()
//                .aggregation(AggregationBuilders.max("aggMax").field("age"));
        //SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(sbuild), RequestOptions.DEFAULT);
        //Max max = response.getAggregations().get("aggMax");
        // 求最小值
//        SearchSourceBuilder sbuild = new SearchSourceBuilder()
//                .aggregation(AggregationBuilders.min("aggMin").field("age"));
//        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(sbuild), RequestOptions.DEFAULT);
//        Min min = response.getAggregations().get("aggMin");
        //求平均值
//        SearchSourceBuilder sbuild = new SearchSourceBuilder()
//                .aggregation(AggregationBuilders.avg("aggAvg").field("age"));
//        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(sbuild), RequestOptions.DEFAULT);
//        Avg min = response.getAggregations().get("aggAvg");
        //求总和
//        SearchSourceBuilder sbuild = new SearchSourceBuilder()
//                .aggregation(AggregationBuilders.sum("aggSum").field("age"));
//        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(sbuild), RequestOptions.DEFAULT);
//        Sum sum = response.getAggregations().get("aggSum");
        //求基数
        SearchSourceBuilder sbuild = new SearchSourceBuilder()
                .aggregation(AggregationBuilders.cardinality("aggCardinality").field("age"));
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(sbuild), RequestOptions.DEFAULT);
        Cardinality car = response.getAggregations().get("aggCardinality");
        System.out.println(car.getValue());
    }

    @ApiOperation(value = "es测试terms分组聚合操作", notes = "")
    @PostMapping(value = "/test/info/aggs_terms")
    public void aggsTermsRequest() throws IOException {
        //按年龄分组
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .aggregation(AggregationBuilders.terms("terms").field("age"));
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(builder), RequestOptions.DEFAULT);
        Terms terms = response.getAggregations().get("terms");
        for(Terms.Bucket bucket: terms.getBuckets()){
            System.out.println(bucket.getKey()+":"+bucket.getDocCount());
        }
    }

    @ApiOperation(value = "es测试filter聚合操作", notes = "")
    @PostMapping(value = "/test/info/aggs_filter")
    public void aggsFilterRequest() throws IOException {
        //filter过滤
        SearchSourceBuilder source = new SearchSourceBuilder()
                .aggregation(AggregationBuilders.filter("filter",
                        QueryBuilders.termQuery("age", "20")));
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(source), RequestOptions.DEFAULT);
        Filter filter = response.getAggregations().get("filter");
        System.out.println(filter.getDocCount());
    }

    @ApiOperation(value = "es测试filters聚合操作", notes = "")
    @PostMapping(value = "/test/info/aggs_filters")
    public void aggsFiltersRequest() throws IOException {
        SearchSourceBuilder source = new SearchSourceBuilder()
                .aggregation(AggregationBuilders.filters("filters",
                        new FiltersAggregator.KeyedFilter("changge", QueryBuilders.termQuery("interests", "changge")),
                        new FiltersAggregator.KeyedFilter("hejiu", QueryBuilders.termQuery("interests", "hejiu"))));
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(source), RequestOptions.DEFAULT);
        Filters filters = response.getAggregations().get("filters");
        for(Filters.Bucket bucket: filters.getBuckets()){
            System.out.println(bucket.getKey()+":"+bucket.getDocCount());
        }
    }

    @ApiOperation(value = "es测试range范围操作", notes = "")
    @PostMapping(value = "/test/info/aggs_range")
    public void aggsRangeRequest() throws IOException {
        SearchSourceBuilder source = new SearchSourceBuilder()
                .aggregation(AggregationBuilders.range("range").field("age")
                        .addUnboundedFrom(25)
                        .addRange(25, 50)
                        .addUnboundedTo(50));
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(source), RequestOptions.DEFAULT);
        Range range = response.getAggregations().get("range");
        for(Range.Bucket bucket: range.getBuckets()){
            System.out.println(bucket.getKey()+":"+bucket.getDocCount());
        }
    }
    @ApiOperation(value = "es测试missing某个字段上值为null的统计", notes = "")
    @PostMapping(value = "/test/info/aggs_missing")
    public void aggsMissing() throws IOException {
        SearchSourceBuilder source = new SearchSourceBuilder()
                .aggregation(AggregationBuilders.missing("missing").field("price"));
        SearchResponse response = rhlClient.search(new SearchRequest("lib3").source(source), RequestOptions.DEFAULT);
        Missing missing = response.getAggregations().get("missing");
        System.out.println(missing.getDocCount());

    }
}