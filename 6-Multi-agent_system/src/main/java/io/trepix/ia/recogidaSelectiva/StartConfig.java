package io.trepix.ia.recogidaSelectiva;

import io.trepix.ia.Size;

import java.util.Random;

record StartConfig(Random generator, Size size, TrashConfig trashConfig, int agentsNumber) {

    record TrashConfig(int amountOfTrash, int types) {}
}
