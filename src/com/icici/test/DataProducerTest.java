package com.icici.test;

import com.icici.demo.DataFilter;
import com.icici.demo.IgniteStoredData;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//import java.util.regex.Pattern;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

public class DataProducerTest {
    
    private static Ignite ignite = null;
    private static long lastTime;
    private static long lastTimems;
    private static long lastMasterTimems;
    private static String IgnitePath = "/home/uno/softwares/apache-ignite-fabric-2.6.0-bin/config/default-config.xml";
//	private static String IgnitePath="/home/chaitanya/Downloads/apache-ignite-fabric-2.6.0-bin/config/default-config.xml";
//    private static String IgnitePath = "/mongodb/apache-ignite/config/default-config.xml";
    private static IgniteStoredData instance;
    
    static {
        ignite = Ignition.start(IgnitePath);
        ignite.cluster().active(true);
        instance = IgniteStoredData.getInstance(ignite);
        
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String kafkaBootstrapServer = "UT-LT-11:9092";
        MasterData mdata = new MasterData();
        MessageData messageData = new MessageData();
//		String kafkaBootstrapServer="localhost:9092";
        try {
//            String kafkaBootstrapServer = "amaze-imnxt-mongo-s3-r3-hyd-nvme:9092";

            Map<String, Object> props = new HashMap<>();
            props.put(StreamsConfig.APPLICATION_ID_CONFIG, "world-count");
            props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
            props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String()
                    .getClass().getName());
            props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String()
                    .getClass().getName());
            StreamsConfig config = new StreamsConfig(props);
            
            KStreamBuilder builder = new KStreamBuilder();
            // builder.stream("test1").mapValues(value ->
            // value.toString()).to("test3");

            // for message
            KStream<String, String> source = builder.stream("test");
//            System.out.println("kafka connected ......" + source);
            System.out.println("Message Data==============");
            // final Pattern pattern = Pattern.compile("\\W+");
            KStream<String, String> counts = source.flatMapValues(value -> Arrays
                    .asList((value.toLowerCase())));
            // .map((key, value) -> new KeyValue<Object, Object>(value, value));
            // .filter((key, value) -> (!value.equals("the")));
            // counts.foreach((key,value)->Test1.sendData(value,ignite));
            
            counts.foreach((key, value) -> {
                // System.out.println("value ="+value);
                long statTime = System.currentTimeMillis();
//                DataFilter.sendData(value, ignite);
                messageData.data(value);
                long endTime = System.currentTimeMillis();
                long timems = endTime - statTime;
                lastTimems = lastTimems + timems;
                long time1 = (lastTimems) / 1000;
//                System.out.println("time in sec :" + (time1));
//                System.out.println("time in msec :" + (lastTimems));
            });
            // counts.to("test3");
            
            MasterData data = new MasterData();

            // for master
            KStream<String, String> master = builder.stream("master");
//            System.out.println("kafka connected ......" + master);
            System.out.println("Master Data==============");
            // final Pattern pattern = Pattern.compile("\\W+");
            KStream<String, String> masterCount = master.flatMapValues(value -> Arrays
                    .asList((value.toLowerCase())));
            // .map((key, value) -> new KeyValue<Object, Object>(value, value));
            // .filter((key, value) -> (!value.equals("the")));
            // counts.foreach((key,value)->Test1.sendData(value,ignite));
            
            masterCount.foreach((key, value) -> {
//                 System.out.println("value ="+value);
                long statTime = System.currentTimeMillis();
//                DataFilter.sendMasterData(value, ignite);
                mdata.data(value);
                long endTime = System.currentTimeMillis();
                long timems = endTime - statTime;
                lastMasterTimems = lastMasterTimems + timems;
                long time1 = (lastMasterTimems) / 1000;
//                System.out.println("master time in sec :" + (time1));
//                System.out.println("master time in msec :" + (lastMasterTimems));
            });
            // counts.to("test3");
            
            System.out.println("starting kafka server ....");
            KafkaStreams streams = new KafkaStreams(builder, config);
//            System.out.println("server ="+streams);
            streams.start();
            mdata.start();
            messageData.start();
            
            System.out.println("end time ====");
            
        } catch (Exception e) {
            System.out.println("data p=" + e);
        }
    }
    
}
