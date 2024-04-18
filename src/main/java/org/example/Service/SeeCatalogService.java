package org.example.Service;

import org.example.Entity.SeeCatalog;

public interface SeeCatalogService {
    public void info(Long articleId, Long memberId);

    public void info_click(Long articleId, Long memberId);

    public void info_hit(Long articleId, Long memberId);

    public Integer findBySeeInfoClickCount(Long articleId, Long memberId);
    public Integer findBySeeInfoHitCount(Long articleId, Long memberId);
}
