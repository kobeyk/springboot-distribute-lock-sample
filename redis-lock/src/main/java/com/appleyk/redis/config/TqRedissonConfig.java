//package com.appleyk.redis.config;
//
//import io.micrometer.core.instrument.util.StringUtils;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.codec.JsonJacksonCodec;
//import org.redisson.config.ClusterServersConfig;
//import org.redisson.config.Config;
//import org.redisson.config.SentinelServersConfig;
//import org.redisson.config.SingleServerConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class TqRedissonConfig {
//
//    @Autowired
//    private RedisProperties redisProperties;
//
//    /**
//     * 哨兵模式自动装配
//     */
//    @Bean(destroyMethod = "shutdown")
//    @ConditionalOnProperty(name="spring.redis.strategy", havingValue = "sentinel")
//    public RedissonClient redissonSentinel() {
//        Config config = new Config();
//        List<String> sentinelNodes = redisProperties.getSentinel().getNodes();
//        if(sentinelNodes.isEmpty()){
//            throw new RuntimeException("Redisson哨兵节点空！");
//        }
//        String[] nodes = sentinelNodes.toString()
//                .replace("[","")
//                .replace("]","")
//                .split(",");
//        List<String> newNodes = new ArrayList<>();
//        Arrays.stream(nodes).forEach((index) -> newNodes.add(
//                index.startsWith("redis://") ? index : "redis://" + index));
//        SentinelServersConfig sentinelServersConfig = config.useSentinelServers()
//                .addSentinelAddress(newNodes.toArray(new String[0]))
//                .setMasterName(redisProperties.getSentinel().getMaster())
//                .setMasterConnectionPoolSize(250)
//                .setSlaveConnectionPoolSize(250);
//        if (StringUtils.isNotBlank(redisProperties.getSentinel().getPassword())) {
//            sentinelServersConfig.setPassword(redisProperties.getSentinel().getPassword());
//        }
//        try{
//            RedissonClient redissonClient = Redisson.create(config);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        return Redisson.create(config);
//    }
//
//    /**
//     * 集群模式自动装配
//     */
//    @Bean(destroyMethod = "shutdown")
//    @ConditionalOnProperty(name="spring.redis.strategy", havingValue = "cluster")
//    public RedissonClient redissonCluster() {
//        Config config = new Config();
//        List<String> nodes = redisProperties.getCluster().getNodes();
//
//        List<String> newNodes = new ArrayList<>(nodes.size());
//        nodes.forEach((index) -> newNodes.add(
//                index.startsWith("redis://") ? index : "redis://" + index));
//        ClusterServersConfig clusterServersConfig = config.useClusterServers()
//                .addNodeAddress(newNodes.toArray(new String[0]));
//        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
//            clusterServersConfig.setPassword(redisProperties.getPassword());
//        }
//        config.setCodec(new JsonJacksonCodec(TqJsonUtils.MAPPER));
//        return Redisson.create(config);
//    }
//
//    /**
//     * 单机模式自动装配
//     */
//    @Bean(destroyMethod = "shutdown")
//    @ConditionalOnProperty(name="spring.redis.strategy", havingValue = "single")
//    public RedissonClient redissonSingle() {
//        Config config = new Config();
//        String redisAddress=String.format("redis://%s:%d",redisProperties.getHost(),redisProperties.getPort());
//        SingleServerConfig singleServerConfig = config.useSingleServer()
//                .setAddress(redisAddress);
//        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
//            singleServerConfig.setPassword(redisProperties.getPassword());
//        }
//        config.setCodec(new JsonJacksonCodec(TqJsonUtils.MAPPER));
//        return Redisson.create(config);
//    }
//
////    public ObjectMapper objectMapper() {
////        ObjectMapper mapper = new ObjectMapper();
////        //大小写不敏感
////        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
////        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
////        mapper.setDateFormat(new SimpleDateFormat(GlobalConstant.DEFAULT_DATE_TIME_FORMAT));
////        JavaTimeModule javaTimeModule = new JavaTimeModule();
////        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(GlobalConstant.DEFAULT_DATE_TIME_FORMAT)));
////        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(GlobalConstant.DEFAULT_DATE_TIME_FORMAT)));
////        mapper.registerModule(javaTimeModule);
////        return mapper;
////    }
//}
