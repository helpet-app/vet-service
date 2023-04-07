package com.helpet.service.vet.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class VetScheduleSlotResponse {
    private UUID id;
    
    private TimeSlotResponse timeSlot;
    
    private Boolean monday;
    
    private Boolean tuesday;
    
    private Boolean wednesday;
    
    private Boolean thursday;
    
    private Boolean friday;
    
    private Boolean saturday;
    
    private Boolean sunday;
}