package com.tuan.blogs.service.implement;

import com.tuan.blogs.model.Album;
import com.tuan.blogs.model.user.User;
import com.tuan.blogs.payload.PagedResponse;
import com.tuan.blogs.repository.AlbumRepository;
import com.tuan.blogs.repository.UserRepository;
import com.tuan.blogs.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImplement implements AlbumService {

    private static final String CREATED_AT = "createdAt";

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PagedResponse<Album> getAlbums(String username, int page, int size) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new NullPointerException("Not found User"));
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC ,CREATED_AT);
        Page<Album> albums = albumRepository.findAll(pageable);

        return null;
    }
}
