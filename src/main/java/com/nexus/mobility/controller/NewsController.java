package com.nexus.mobility.controller;

import com.nexus.mobility.dto.NewsDtos;
import com.nexus.mobility.entity.NewsItem;
import com.nexus.mobility.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping({"/api/news", "/api/v1/news"})
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public Object list(@RequestParam UUID tenantId,
                       @RequestParam(required = false) String category,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer limit) {
        return newsService.list(tenantId, category, page, limit);
    }

    @GetMapping("/top")
    public Object top(@RequestParam UUID tenantId, @RequestParam(defaultValue = "3") Integer limit) {
        return newsService.top(tenantId, limit);
    }

    @PostMapping
    public NewsItem create(@Valid @RequestBody NewsDtos.NewsRequest request) {
        return newsService.create(request);
    }

    @GetMapping("/{id}")
    public NewsItem get(@PathVariable UUID id) {
        return newsService.get(id);
    }

    @PatchMapping("/{id}")
    public NewsItem update(@PathVariable UUID id, @Valid @RequestBody NewsDtos.NewsRequest request) {
        return newsService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        newsService.delete(id);
    }
}
