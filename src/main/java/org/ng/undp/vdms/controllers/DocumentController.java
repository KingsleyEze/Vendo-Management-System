package org.ng.undp.vdms.controllers;

import org.ng.undp.vdms.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by emmanuel on 6/15/17.
 */
@Controller
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private StorageService storageService;

    @RequestMapping(value = "/downloads/{filename:.+}", method = RequestMethod.GET)
    //@ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws IOException {

            Resource file = storageService.loadAsResource(filename);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))

                    .body(file);
        }








}
