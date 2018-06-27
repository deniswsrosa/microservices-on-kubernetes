package com.cb.springdata.sample.entities;


import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

public class BasicEntity {

    @Getter(PROTECTED)
    @Setter(PROTECTED)
    @Ignore
    protected String _class;
}
