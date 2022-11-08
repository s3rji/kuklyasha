package ru.serji.kuklyasha.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull
    private StatusType type;

    @Column(name = "status_modified", nullable = false)
    @NotNull
    private LocalDateTime modified;

    public Status(StatusType type) {
        this.type = type;
        this.modified = LocalDateTime.now();
    }
}