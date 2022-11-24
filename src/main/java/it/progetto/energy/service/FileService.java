package it.progetto.energy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
public class FileService implements FileStorageService{

    private final Path root = Paths.get("upload");

    @Override
    public void init() {
        try{
            Files.createDirectories(root);
        }catch (IOException e){
            throw new RuntimeException("Could not initialize folder for upload");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
        } catch (Exception e){
            throw new RuntimeException("Could not store the file. Error " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try{
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new RuntimeException("Could not possible to get or read file");
            }
        }catch (MalformedURLException e){
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(root, 1).filter(path -> !path.equals(root)).map(root::relativize);
        }catch (IOException e){
            throw new RuntimeException("Could not possible to get File");

        }
    }
}
