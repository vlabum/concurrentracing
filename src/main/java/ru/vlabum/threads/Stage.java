package ru.vlabum.threads;

import org.jetbrains.annotations.NotNull;

public abstract class Stage {

    protected int length;

    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(@NotNull Car c);

}