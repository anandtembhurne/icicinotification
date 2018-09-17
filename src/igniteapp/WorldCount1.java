package igniteapp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;




public class WorldCount1 {
	private static Ignite ignite=null;
	private static long lastTime;
	private static long lastTimems;
//	private static String localIgnite="/home/uno/softwares/apache-ignite-fabric-2.6.0-bin/config/default-config.xml";
//	private static String serverIgnite="/mongodb/apache-ignite/config/default-config.xml";
//	static{
//		ignite = Ignition.start(serverIgnite);
//	}
	
	public static void main(String agr[]){
		//String localBootsrapP="UT-LT-11:9092";
//		String kafkaServerBootstrap="amaze-imnxt-mongo-s1-r3-hyd-nvme:9092";
		String kafkaServerBootstrap="192.168.0.24:9092";
		
		Map<String, Object> props = new HashMap<>();
	    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "world-count");
	    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerBootstrap);
	    props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
	    props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
	    StreamsConfig config = new StreamsConfig(props);
            System.out.println("config ="+config);

	    KStreamBuilder builder = new KStreamBuilder();
	   // builder.stream("test1").mapValues(value -> value.toString()).to("test3");
	    
	    KStream<String, String> source = builder.stream("test1");
	   /// source.print();
	    System.out.println("print data ==============");
	    
	 
	    final Pattern pattern = Pattern.compile("\\W+");
	    KStream<String, String> counts  = source.flatMapValues(value-> Arrays.asList((value.toLowerCase())));
           //  .map((key, value) -> new KeyValue<Object, Object>(value, value));
//                .filter((key, value) -> (!value.equals("the")));
	   // counts.foreach((key,value)->Test1.sendData(value,ignite));
	   
	    counts.foreach((key,value)->{
	    	//System.out.println("value ="+value);
	    	long statTime = System.currentTimeMillis();
	    	//Test1.sendData(value, ignite);
	    	          System.out.println("key="+key+" value="+value);
	    }
	    );
	    
	    
               counts.to("test3");
                
               //counts.print();
               
               
	    KafkaStreams streams = new KafkaStreams(builder, config);
	    streams.start();
            
	    System.out.println("end time ====");
     
		
	}

}
