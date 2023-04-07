package com.helpet.service.vet.storage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vet_schedule")
public class VetScheduleSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vet_id", nullable = false)
    private Vet vet;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "time_slot_id", nullable = false)
    private TimeSlot timeSlot;

    @Builder.Default
    @NotNull
    @Column(name = "monday", nullable = false)
    private Boolean monday = false;

    @Builder.Default
    @NotNull
    @Column(name = "tuesday", nullable = false)
    private Boolean tuesday = false;

    @Builder.Default
    @NotNull
    @Column(name = "wednesday", nullable = false)
    private Boolean wednesday = false;

    @Builder.Default
    @NotNull
    @Column(name = "thursday", nullable = false)
    private Boolean thursday = false;

    @Builder.Default
    @NotNull
    @Column(name = "friday", nullable = false)
    private Boolean friday = false;

    @Builder.Default
    @NotNull
    @Column(name = "saturday", nullable = false)
    private Boolean saturday = false;

    @Builder.Default
    @NotNull
    @Column(name = "sunday", nullable = false)
    private Boolean sunday = false;
}