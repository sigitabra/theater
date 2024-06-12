package com.example.theater.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ScheduledPlay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private TheaterPlay theaterPlay;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "scheduledPlay")
    private List<Reservation> reservations;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Time time;

    private int totalReservedSeats;

    @Column(nullable = false)
    private int totalSeats;

    @CreatedDate
    private Timestamp createdAt;

    @LastModifiedDate
    private Timestamp updatedAt;

    @Override
    public String toString() {
        return "ScheduledPlay{" +
                "id=" + id +
                ", theaterPlay=" + theaterPlay.getTitle() +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", totalReservedSeats=" + totalReservedSeats +
                ", totalSeats=" + totalSeats +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduledPlay that)) return false;
        return Objects.equals(theaterPlay, that.theaterPlay) && Objects.equals(date.toString(),
                that.date.toString()) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theaterPlay, date, time);
    }
}
