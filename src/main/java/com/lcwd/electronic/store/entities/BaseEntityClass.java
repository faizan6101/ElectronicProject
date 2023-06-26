package com.lcwd.electronic.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityClass {

    @Column(name="create_Date",updatable = false)
    @CreationTimestamp
    private LocalDate createDate;

    @Column(name = "update_Date",insertable = false)
    @UpdateTimestamp
    private LocalDate updateuser;

    @Column(name="is_Active")
    private String isactive;
}
