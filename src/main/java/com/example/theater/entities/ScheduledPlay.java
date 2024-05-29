package com.example.theater.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
public class ScheduledPlay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private TheaterPlay theaterPlay;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduled_play_id")
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
}
