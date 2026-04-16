package com.nexus.mobility.service;

import com.nexus.mobility.dto.ApiDtos;
import com.nexus.mobility.dto.NewsDtos;
import com.nexus.mobility.entity.NewsItem;
import com.nexus.mobility.exception.ResourceNotFoundException;
import com.nexus.mobility.repository.NewsItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {

    private final NewsItemRepository newsItemRepository;

    public NewsService(NewsItemRepository newsItemRepository) {
        this.newsItemRepository = newsItemRepository;
    }

    public ApiDtos.PageResponse<NewsItem> list(UUID tenantId, String category, Integer page, Integer limit) {
        List<NewsItem> items = newsItemRepository.findByTenantId(tenantId).stream()
                .filter(item -> category == null || item.getCategory().name().equalsIgnoreCase(category))
                .sorted(Comparator.comparing(NewsItem::getPublishDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
        return PageMapper.page(items, page, limit);
    }

    public List<NewsItem> top(UUID tenantId, Integer limit) {
        int size = limit == null || limit < 1 ? 3 : limit;
        return newsItemRepository.findByTenantId(tenantId).stream()
                .sorted(Comparator.comparing(NewsItem::getPublishDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(size)
                .toList();
    }

    public NewsItem get(UUID id) {
        return newsItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("News item not found."));
    }

    @Transactional
    public NewsItem create(NewsDtos.NewsRequest request) {
        NewsItem item = new NewsItem();
        apply(item, request);
        return newsItemRepository.save(item);
    }

    @Transactional
    public NewsItem update(UUID id, NewsDtos.NewsRequest request) {
        NewsItem item = get(id);
        apply(item, request);
        return newsItemRepository.save(item);
    }

    @Transactional
    public void delete(UUID id) {
        newsItemRepository.delete(get(id));
    }

    private void apply(NewsItem item, NewsDtos.NewsRequest request) {
        item.setTenantId(request.tenantId());
        item.setTitle(request.title());
        item.setCategory(request.category());
        item.setPublishDate(request.publishDate());
        item.setSourceUrl(request.sourceUrl());
        item.setSummary(request.summary());
    }
}
