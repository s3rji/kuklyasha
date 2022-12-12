package ru.serji.kuklyasha.model;

import lombok.*;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Column(name = "notice_email", nullable = false, columnDefinition = "bool default false")
    private boolean email;

    @Column(name = "notice_phone", nullable = false, columnDefinition = "bool default false")
    private boolean phone;
}