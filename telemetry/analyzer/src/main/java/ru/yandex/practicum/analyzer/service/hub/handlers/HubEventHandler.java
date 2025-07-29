package ru.yandex.practicum.analyzer.service.hub.handlers;

public interface HubEventHandler<T> {
    void handle(String hubId, T event);
}