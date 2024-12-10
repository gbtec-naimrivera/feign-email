package com.example.demo.repository;

import com.example.demo.entity.Senders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Repository interface for accessing the "senders" table in the database.</p>
 * <p>This interface extends {@link JpaRepository}, providing basic CRUD operations for the {@link Senders} entity.</p>
 * <p>Additional query methods can be added as needed to perform more complex queries.</p>
 */
@Repository
public interface SendersRepository extends JpaRepository<Senders, Long> {
}