import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SimpleKafkaProducerAsync
{
    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.158:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", "String Key", "String Value");

        try {
            producer.send(record, (recordMetadata, e) -> {
                if (e != null)
                    e.printStackTrace();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        producer.close();
    }
}