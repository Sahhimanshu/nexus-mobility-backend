package com.nexus.mobility.service;

import com.nexus.mobility.dto.ApiDtos;
import com.nexus.mobility.dto.EventDtos;
import com.nexus.mobility.entity.Event;
import com.nexus.mobility.entity.EventParticipant;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.EventParticipantRepository;
import com.nexus.mobility.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventParticipantRepository eventParticipantRepository;

    public EventService(EventRepository eventRepository, EventParticipantRepository eventParticipantRepository) {
        this.eventRepository = eventRepository;
        this.eventParticipantRepository = eventParticipantRepository;
    }

    public ApiDtos.PageResponse<Event> list(UUID tenantId, String type, LocalDate from, LocalDate to, Integer page, Integer limit) {
        List<Event> items = eventRepository.findByTenantId(tenantId).stream()
                .filter(event -> type == null || event.getType().name().equalsIgnoreCase(type))
                .filter(event -> from == null || (event.getEventDate() != null && !event.getEventDate().isBefore(from)))
                .filter(event -> to == null || (event.getEventDate() != null && !event.getEventDate().isAfter(to)))
                .sorted(Comparator.comparing(Event::getEventDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
        return PageMapper.page(items, page, limit);
    }

    public Event get(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found."));
    }

    @Transactional
    public Event create(EventDtos.EventRequest request) {
        Event event = new Event();
        apply(event, request);
        return eventRepository.save(event);
    }

    @Transactional
    public Event update(UUID id, EventDtos.EventRequest request) {
        Event event = get(id);
        apply(event, request);
        return eventRepository.save(event);
    }

    @Transactional
    public void delete(UUID id) {
        eventRepository.delete(get(id));
    }

    public List<EventParticipant> participants(UUID eventId) {
        get(eventId);
        return eventParticipantRepository.findByEventId(eventId);
    }

    @Transactional
    public List<EventParticipant> addParticipant(UUID eventId, EventDtos.ParticipantRequest request) {
        EventParticipant participant = new EventParticipant();
        participant.setEventId(eventId);
        participant.setTenantId(request.tenantId());
        participant.setFullName(request.fullName());
        participant.setEmail(request.email());
        participant.setOrganization(request.organization());
        participant.setRole(request.role());
        eventParticipantRepository.save(participant);
        return participants(eventId);
    }

    @Transactional
    public void removeParticipant(UUID eventId, UUID participantId) {
        get(eventId);
        eventParticipantRepository.delete(eventParticipantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found.")));
    }

    private void apply(Event event, EventDtos.EventRequest request) {
        event.setTenantId(request.tenantId());
        event.setName(request.name());
        event.setType(request.type());
        event.setEventDate(request.eventDate());
        event.setCountryCode(request.countryCode());
        event.setLocation(request.location());
        event.setHostInstitution(request.hostInstitution());
        event.setDescription(request.description());
    }
}
