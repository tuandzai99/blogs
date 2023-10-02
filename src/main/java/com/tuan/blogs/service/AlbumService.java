package com.tuan.blogs.service;

import com.tuan.blogs.model.Album;
import com.tuan.blogs.payload.PagedResponse;

public interface AlbumService {
    PagedResponse<Album> getAlbums(String username, int page, int size);

}
