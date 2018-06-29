package com.example.demo.jpa

import java.time.LocalDateTime
import javax.persistence.*

/* Entities - persistence layer code
 *
 * Optional parameters with default values at the last
 * position(s) makes the parameters optional in models
 */

@Entity
data class Card(
        val title: String,
        val headline: String,
        val content: String,
        @ManyToOne @JoinColumn val author: User,
        @Id @GeneratedValue val id: Long? = null,
        val addedAt: LocalDateTime = LocalDateTime.now()
)

data class User(
        @Id val login: String
)

