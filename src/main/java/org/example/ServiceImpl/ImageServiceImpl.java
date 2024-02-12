package org.example.ServiceImpl;

import org.example.Entity.Image;
import org.example.Repository.ImageRepository;
import org.example.Repository.MemberRepository;
import org.example.Service.ImageService;
import org.example.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;


}