package com.github.brun0xp.esigtodos.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todo {

    private Integer id;
    private String ip;
    private String description;
    private boolean done;

}
