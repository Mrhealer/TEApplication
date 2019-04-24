package com.healer.dev.data.models;

import java.io.Serializable;

public class TopicModel implements Serializable {
    public int id;
    public String name, imageUrl, category, color, lastTime;

    public TopicModel(int id, String name, String imageUrl, String category, String color, String lastTime) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.color = color;
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "TopicModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
