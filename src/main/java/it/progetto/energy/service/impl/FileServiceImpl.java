package it.progetto.energy.service.impl;

import it.progetto.energy.exception.FileException;
import it.progetto.energy.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_INIT_FILE;
import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_LOAD_FILE;
import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_SAVE_FILE;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final Path root = Paths.get("/resources/upload");

    @Override
    public void init() {
        try{
            Files.createDirectories(root);
        }catch (IOException e){
            throw new FileException(ERROR_INIT_FILE);
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e){
            throw new FileException(ERROR_SAVE_FILE);
        }
    }

    //TODO REFACTOR CODE
    @Override
    public Resource load(String filename) {
        try{
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new FileException(ERROR_LOAD_FILE);
            }
        } catch (MalformedURLException e){
            throw new FileException(ERROR_LOAD_FILE);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(root, 1)
                    .filter(path -> !path.equals(root))
                    .map(root::relativize);
        }catch (IOException e){
            throw new FileException(ERROR_LOAD_FILE);

        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        try {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            FileCopyUtils.copy(multipartFile.getBytes(), file);
            return file;
        } catch (IOException e) {
            throw new IOException("ERROR DURING CONVERSION MultipartFile -> File: " + e.getMessage());
        }
    }

}
