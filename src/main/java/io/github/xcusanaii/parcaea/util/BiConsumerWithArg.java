package io.github.xcusanaii.parcaea.util;

import java.util.function.BiConsumer;

public abstract class BiConsumerWithArg implements BiConsumer<String, Boolean> {
    public final String[] args;

    public BiConsumerWithArg(String[] args) {
        this.args = args;
    }
}
