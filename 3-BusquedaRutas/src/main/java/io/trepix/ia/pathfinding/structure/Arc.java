package io.trepix.ia.pathfinding.structure;

public record Arc<T extends Node<T>>(T origin, T destination, double cost) { }
