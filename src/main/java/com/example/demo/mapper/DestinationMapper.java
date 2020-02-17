package com.example.demo.mapper;


public interface DestinationMapper<Source, Destination> {
    Destination sourceToDestination(Source source);
    Source destinationToSource(Destination destination);
}
