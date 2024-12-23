package com.example.demo.repositoryElastic;

import com.example.demo.entity.Senders;
import org.springframework.data.elasticsearch.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Repository interface for accessing the "senders" table in the database.</p>
 * <p>This interface extends {@link ElasticsearchRepository}, providing basic CRUD operations for the {@link Senders} entity.</p>
 * <p>Additional query methods can be added as needed to perform more complex queries.</p>
 */
public interface SendersRepositoryElastic extends ElasticsearchRepository<Senders, Long>{
}
