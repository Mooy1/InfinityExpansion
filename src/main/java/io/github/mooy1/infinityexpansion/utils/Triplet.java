package io.github.mooy1.infinityexpansion.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Triplet<A, B, C> {

    private A a;
    private B b;
    private C c;
    
}
