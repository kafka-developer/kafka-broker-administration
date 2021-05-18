import java.util.*;

import org.apache.kafka.clients.producer.*;

public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        String topicName = "test-topic";
        String key = "k1";
        String value = "v1";

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.158:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);

        producer.send(record, new AsyncProducerCallback());
        System.out.println("AsyncProducer call completed");
        producer.close();

    }
}

class AsyncProducerCallback implements Callback {

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null)
            System.out.println("AsyncProducer failed with an exception");
        else
            System.out.println("AsyncProducer callback Success:");
    }
}