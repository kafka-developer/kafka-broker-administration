package com.xargspratix;

//##
//Author: xargs-pratix: Prateek Shukla
//##

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class AsyncKafkaProducer {
    public static void main(String... args) throws Exception {
        doRunProducer(200);

    }

    static void doRunProducer(final int sendMessageCount) throws InterruptedException {
        String topicName = "test-topic";
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.158:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        long time = System.currentTimeMillis();

        Producer<Long, String> producer = new KafkaProducer<Long, String>(props);
        final CountDownLatch countDownLatch = new CountDownLatch(sendMessageCount);

        try {
            for (long index = time; index < time + sendMessageCount; index++) {
                final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(topicName, index,
                        "Kafka-Broker-Administration " + index);
                producer.send(record, (metadata, exception) -> {
                    long elapsedTime = System.currentTimeMillis() - time;
                    if (metadata != null) {
                        System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n",
                                record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
                    } else {
                        exception.printStackTrace();
                    }
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await(10, TimeUnit.SECONDS);
        } finally {
            producer.flush();
            producer.close();
        }
    }

}