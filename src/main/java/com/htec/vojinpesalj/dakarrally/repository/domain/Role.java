package com.htec.vojinpesalj.dakarrally.repository.domain;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id @GeneratedValue private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}
