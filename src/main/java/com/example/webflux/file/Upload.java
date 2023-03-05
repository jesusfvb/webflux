package com.example.webflux.file;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
@RequestMapping("/upload")
public class Upload {

    final Path root = Paths.get("./src/main/resources/upload");

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    private Mono<Void> upload(@RequestPart("file") Mono<FilePart> file) {

        return file
                .doOnNext(f -> System.out.println(f.filename()))
                .flatMap(f -> {
                    if (!root.toFile().exists()) root.toFile().mkdirs();
                    return f.transferTo(root.resolve(f.filename()));
                })
                .then();
    }

    @GetMapping(value = "/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    private Mono<FileSystemResource> download(@PathVariable String filename) {
        return Mono.just(new FileSystemResource(root.resolve(filename).toFile()));
    }
}
