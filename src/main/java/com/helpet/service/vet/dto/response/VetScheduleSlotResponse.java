package com.helpet.service.vet.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class VetScheduleSlotResponse {
    private UUID id;
    
    private TimeSlotResponse timeSlot;
    
    private Boolean monday = false;
    
    private Boolean tuesday = false;
    
    private Boolean wednesday = false;
    
    private Boolean thursday = false;
    
    private Boolean friday = false;
    
    private Boolean saturday = false;
    
    private Boolean sunday = false;
}