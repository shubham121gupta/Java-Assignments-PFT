package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class AtomicCounter {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "atomicDB";
    private static final String COLLECTION_NAME = "counters";
    private static final String COUNTER_ID = "counterId";

    private static synchronized long getNextSequenceValue(MongoCollection<Document> collection) {
        Document query = new Document("_id", COUNTER_ID);
        Document update = new Document("$inc", new Document("sequence_value", 1));
        Document result = collection.findOneAndUpdate(query, update);
        if (result == null) {
            throw new RuntimeException("No document found for counterId: " + COUNTER_ID);
        }
        return result.getInteger("sequence_value");
    }

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Runnable task = () -> {
                long nextValue = getNextSequenceValue(collection);
                System.out.println(Thread.currentThread().getName() + ": Next sequence value: " + nextValue);
            };

            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
            Thread thread3 = new Thread(task);

            thread1.start();
            thread2.start();
            thread3.start();

            try {
                thread1.join();
                thread2.join();
                thread3.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
