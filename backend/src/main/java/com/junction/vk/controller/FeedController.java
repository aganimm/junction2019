package com.junction.vk.controller;

import java.util.Collection;
import java.util.Collections;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.service.FeedService;

@RestController
@RequestMapping("/api/feed")
public class FeedController {
    public final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/feed?count={count}&offset={offset}")
    @CrossOrigin(origins = "*")
    public Collection<ProductCard> getFeedItemsWith(@PathVariable long count, @PathVariable long offset) {
        return Collections.emptyList();
    }
}
