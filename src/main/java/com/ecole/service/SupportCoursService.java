package com.ecole.service;

import com.ecole.model.SupportCours;
import com.ecole.repository.SupportCoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.List;

@Service
public class SupportCoursService {
    @Autowired private SupportCoursRepository repository;

    public List<SupportCours> findByAffectationId(Long affectationId) {
        return repository.findByAffectationIdOrderByCreatedAtDesc(affectationId);
    }

    public void save(SupportCours support, MultipartFile file) throws Exception {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/supports/" + fileName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        
        support.setUrlFichier("/uploads/supports/" + fileName);
        repository.save(support);
    }
}