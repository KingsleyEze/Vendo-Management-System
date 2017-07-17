package org.ng.undp.vdms.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.apache.commons.lang3.StringUtils;
import org.ng.undp.vdms.dao.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by emmanuel on 12/6/16.
 */
public class Utility {
    private static Logger logger = LoggerFactory.getLogger(Utility.class);
    /**
     * Convert errors in binding result to a Map
     * @param bindingResult
     * @return
     */
    public static Map<String, String> errors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return errors;
    }

    /**
     * Convert errors  to a Map
     * @param errorsArray
     * @return
     */
    public static Map<String, String> errors( ArrayList<String> errorsArray ) {
        Map<String, String> errors = new HashMap<>();

        for (int i = 0; i< errorsArray.size(); i++
                ) {
            logger.info(errorsArray.get(i));
            errors.put(Integer.toString(i),errorsArray.get(i));

        }


        return errors;
    }
    /**
     * Convert success messages  to a Map
     * @param successArray
     * @return
     */
    public static Map<String, String> success(ArrayList<String> successArray ) {
        Map<String, String> success = new HashMap<>();

        for (int i = 0; i< successArray.size(); i++
                ) {
            logger.info(successArray.get(i));
            success.put(Integer.toString(i),successArray.get(i));

        }



        return success;
    }
    /**
     * Convert success messages  to a Map
     * @param successArray
     * @return
     */
    public static Map<String, String> stickyNotice(ArrayList<String> successArray ) {

        Map<String, String> success = new HashMap<>();

        for (int i = 0; i< successArray.size(); i++
                ) {
            logger.info(successArray.get(i));
            success.put(Integer.toString(i),successArray.get(i));

        }



        return success;
    }



    /**
     * Convert request Parameters in HttpServletRequest object to a map
     * @param r
     * @return
     */
    public static Map<String, String> getRequestParams(HttpServletRequest r){
        Map<String, String> params = new HashMap<>();
        Enumeration<String> e = r.getParameterNames();
        while(e.hasMoreElements()){
            String name = e.nextElement();
            params.put(name, r.getParameter(name));
        }
        return params;
    }

    /**
     * create a db pagination statement from parameters supplied in HttpServletRequest object
     * @param req
     * @return
     */
    public static Param getParam(HttpServletRequest req){
        Param p = new Param(0, 50);
        String page = req.getParameter("page");
        if(StringUtils.isNumeric(page)){
            p.setPage(Integer.valueOf(page));
        }

        String size = req.getParameter("size");
        if(StringUtils.isNumeric(size)){
            p.setSize(Integer.valueOf(size));
        }

        String sort = req.getParameter("sort");
        p.setSort("created_at DESC");//Default;
        if(StringUtils.isNotEmpty(sort)){
            p.setSort(sort);
        }

        String unpagedUrl = UriUtil.getUnPagedUrl(req.getRequestURI(),  req.getQueryString());
        p.setUrl(unpagedUrl);

        return p;
    }

    public  static String generateClassName(String classLevelName, String classArm) throws IllegalArgumentException{
        if(classLevelName == null || StringUtils.isBlank(classArm)){
            throw new IllegalArgumentException("class Type must not be null, classLevel cannot be zero and classArm cannot be empty");
        }

        StringBuilder sb = new StringBuilder(classLevelName);
        sb.append(" ").append(classArm);

        return sb.toString();
    }


    public static String createJsonStringFromObject(Object object){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        return json;
    }


    private static Path buildPath(final Path root, final Path child) {
        if (root == null) {
            return child;
        } else {
            return Paths.get(root.toString(), child.toString());
        }
    }

    /**
     *
     * @param out
     * @param root
     * @param dir
     * @throws IOException
     */
    private static void addZipDir(final ZipOutputStream out, final Path root, final Path dir) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path child : stream) {
                Path entry = buildPath(root, child.getFileName());
                if (Files.isDirectory(child)) {
                    addZipDir(out, entry, child);
                } else {
                    out.putNextEntry(new ZipEntry(entry.toString()));
                    Files.copy(child, out);
                    out.closeEntry();
                }
            }
        }
    }

    /**
     * Compress the given path to a zip
     * @param path
     * @throws IOException
     */
    public static void zipDir(final Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory.");
        }

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path.toString() + ".zip"));

        try (ZipOutputStream out = new ZipOutputStream(bos)) {
            addZipDir(out, path.getFileName(), path);
        }
    }


    /**
     * Unzip it
     * @param zipFile input zip file
     * @param outputFolder zip file output folder
     */
    public static void unZipIt(Path zipFile, Path outputFolder){

        byte[] buffer = new byte[1024];

        //get the zip file content
        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile.toString()))){

            //create output directory is not exists
            File folder = new File(outputFolder.toString());
            if(!folder.exists()){
                folder.mkdir();
            }

            addFromZipToRootFolder(zis, folder, buffer);


        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Add individual zip entry to a destination folder
     * @param zis - ZipInpustream created from a compressed zip file
     * @param outputFolder - File to output zip entry to
     * @param buffer - buffer to hold intermittent file content
     * @throws IOException
     */
    private static void addFromZipToFolder(ZipInputStream zis, File outputFolder, byte[] buffer) throws IOException{
        if(zis == null){
            return;
        }

        //get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();

        while(ze!=null){

            String fileName = ze.getName();
            File newFile = new File(outputFolder + File.separator + fileName);

            System.out.println("file unzip : "+ newFile.getAbsoluteFile());

            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
        }

        System.out.println("Done");


    }


    /**
     * Add individual zip entry to root folder
     * @param zis - ZipInpustream created from a compressed zip file
     * @param outputFolder - File to output zip entry to
     * @param buffer - buffer to hold intermittent file content
     * @throws IOException
     */
    private static void addFromZipToRootFolder(ZipInputStream zis, File outputFolder, byte[] buffer) throws IOException{
        if(zis == null){
            return;
        }

        //get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();

        while(ze!=null){

            String fileName = ze.getName();
            String[] arr = fileName.split("/");
            fileName = arr[arr.length - 1];
            File newFile = new File(outputFolder + File.separator + fileName);

            System.out.println("file unzip : "+ newFile.getAbsoluteFile());

            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
        }

        System.out.println("Done");


    }

    public static boolean isBcryptHashed(String password){

        if( StringUtils.isNotBlank(password) && password.length() == 60 && (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$") )){
            return true;
        }

        return false;
    }

    public static boolean isNetworkAvailable(String uri) {
        try {
            final URL url = new URL(uri);//will switch to studylabs domain when fully hosted
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                return true;
            }else{
                return false;
            }

        } catch (MalformedURLException e) {
            //System.out.println("Malformed Url Exception");
            throw new RuntimeException(e);
        } catch (IOException e) {
            //System.out.println("Network is unavailable via exception");
            return false;
        }
    }


        public static void listFilesInUploadDir(){
        Path extractDestination = Paths.get("uploads");

        try{
            Files.walkFileTree(extractDestination, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("Visiting file: " + file.getFileName());

                    return FileVisitResult.CONTINUE;
                }

            });

        }catch(IOException ioe){
            System.out.println("An Io exception happened, while walking through files ");
            ioe.printStackTrace();
        }
           /* System.out.println("FileName: " + f.getFileName());
        });*/
    }

    /**
     *
     * @param fileName - String representation of the file's name
     * @return - String representing the String's extension e.g doc, png
     */
    public static String getFileExtension(String fileName) {
        if (fileName != null) {
            if (!fileName.trim().equals("")) {
                String[] splits = fileName.split("\\.");
                if (splits.length > 1) {
                    String ext = splits[splits.length - 1];
                    return ext;
                }
            }
        }
        return "";
    }

}
