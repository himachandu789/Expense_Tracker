package com.chandu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name="user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 30)
    private String name;
    @Column(length = 30, nullable = false,unique = true)
    private String email;
    @Column(length = 12,unique = true)
    private String contact;

    @CreationTimestamp
    private Date createdAt ;

    @UpdateTimestamp
    private Date updatedAt;

    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @JsonIgnore
   @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<TxnDetails> txnDetails;


}
