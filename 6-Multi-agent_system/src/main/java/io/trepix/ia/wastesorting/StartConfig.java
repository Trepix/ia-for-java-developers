package io.trepix.ia.wastesorting;

import io.trepix.ia.Size;

import java.util.Random;

record StartConfig(Random generator, Size size, GarbageConfig garbageConfig, int agentsNumber) {

    record GarbageConfig(int amountOfGarbage, int types) {}
}
