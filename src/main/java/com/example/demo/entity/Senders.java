package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Entity class representing a sender in the "senders" table in the database.</p>
 * <p>This entity stores information about the sender's email address and a unique identifier for each sender.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "senders")
public class Senders {

    /**
     * <p>The unique identifier of the sender.</p>
     * <p>This field is the primary key for the "senders" table and is generated automatically by the database.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * <p>The sender's email address.</p>
     * <p>This field stores the email address of the sender and cannot be null.</p>
     * <p>The column name is "email_from", as specified in the database schema.</p>
     */
    @Column(name = "email_from", nullable = false)
    private String emailFrom;

}
