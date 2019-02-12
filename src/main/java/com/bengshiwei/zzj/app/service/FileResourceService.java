package com.bengshiwei.zzj.app.service;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("files")
public class FileResourceService {

    private static final String filepath = "D:/测试文档/小柳哥.txt";
    private static final String serverLocation = "D:/测试文档/";

    /**
     * Constants operating with images
     */
    private static final String ARTICLE_IMAGES_PATH = "d:/Newsportal/article_images/";
    private static final String JPG_CONTENT_TYPE = "image/jpeg";
    private static final String PNG_CONTENT_TYPE = "image/png";
    /**
     * 第一种方式上传
     *
     * @param fileInputStream
     * @param disposition
     * @return
     */
    @POST
    @Path("uploadimage1 ")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadimage1(@FormDataParam("file") InputStream fileInputStream,
                               @FormDataParam("file") FormDataContentDisposition disposition) {
        String imageName = Calendar.getInstance().getTimeInMillis()
                + disposition.getFileName();

        File file = new File(ARTICLE_IMAGES_PATH + imageName);
//        try {
//            //使用common io的文件写入操作
//
//
////            FileUtils.copyInputStreamToFile(fileInputStream, file);
//            //原来自己的文件写入操作
//            //saveFile(fileInputStream, file);
//        } catch (IOException ex) {
//
//            Logger.getLogger(FileResourceService.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return "images/" + imageName;
    }

    /**
     * *
     * 第二种方式上传 使用FormDataMultiPart 获取表单数据
     *
     * @param form
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @POST
    @Path("uploadimage2")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadimage2(FormDataMultiPart form, @Context HttpServletResponse response) throws UnsupportedEncodingException {
        //获取文件流
        FormDataBodyPart filePart = form.getField("file");
        //获取表单的其他数据
        FormDataBodyPart usernamePart = form.getField("username");

        //ContentDisposition headerOfFilePart = filePart.getContentDisposition();
        //把表单内容转换成流
        InputStream fileInputStream = filePart.getValueAs(InputStream.class);

        FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();

        String source = formDataContentDisposition.getFileName();
        String result = new String(source.getBytes("ISO8859-1"), "UTF-8");

        System.out.println("formDataContentDisposition.getFileName()result " + result);

        String filePath = ARTICLE_IMAGES_PATH + result;
        File file = new File(filePath);
        System.out.println("file " + file.getAbsolutePath());
//        try {
//            //保存文件
//            FileUtils.copyInputStreamToFile(fileInputStream, file);
////  saveFile(fileInputStream, file);
//        } catch (IOException ex) {
//            Logger.getLogger(FileResourceService.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("" + "images/" + result);

        response.setCharacterEncoding("UTF-8");
        return "images/" + result;
    }

    /**
     *
     * 不从web服务器去读图片,在磁盘某个目录的文件可以通过流的方式去获取 ,通过 response.getOutputStream()放回数据
     *
     * @param imageName image-name
     * @param type extension of image
     * @param response {@link HttpServletResponse}
     * @throws IOException
     */
    @GET
    @Path("/images/{name}.{type}")
    public void showImg(@PathParam("name") String imageName,
                        @PathParam("type") String type,
                        @Context HttpServletResponse response)
            throws IOException {
        System.out.println("showImg");
//        try (InputStream in = new FileInputStream(ARTICLE_IMAGES_PATH
//                + imageName + "." + type)) {
//            FileUtils.copyFile(new File(ARTICLE_IMAGES_PATH + imageName + "." + type), response.getOutputStream());
////      FileCopyUtils.copy(in, response.getOutputStream());
//        }catch (Exception e){
//
//        }
    }

    // 保存文件信息到磁盘
    private void saveFile(InputStream uploadedInputStream, File file) {
        System.out.println("------saveFile-----");
        try {
            OutputStream outpuStream = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
//      outpuStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 文件下载
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @GET
    @Path("download/{name}.{type}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("name") String imageName,
                                 @PathParam("type") String type) throws UnsupportedEncodingException {

        File file = new File(ARTICLE_IMAGES_PATH+"/"+imageName+"."+type);
        if (file.isFile() && file.exists()) {
            String mt = new MimetypesFileTypeMap().getContentType(file);
            String fileName = file.getName();

            //处理文件名称编码
            fileName = new String(fileName.getBytes("utf-8"),"ISO8859-1");

            return Response
                    .ok(file, mt)
                    .header("Content-disposition",
                            "attachment;filename=" + fileName)
                    .header("ragma", "No-cache")
                    .header("Cache-Control", "no-cache").build();

        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("下载失败，未找到该文件").build();
        }
    }

    /**
     * 文件上传
     *
     * @param fileInputStream
     * @param contentDispositionHeader
     * @return
     * @throws IOException
     */
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    public Response uploadFile(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader)
            throws IOException {

        String fileName = contentDispositionHeader.getFileName();
        //处理文件名称编码
        //fileName = new String(fileName.getBytes("ISO8859-1"), "utf-8");

        File file = new File(serverLocation + fileName);
        File parent = file.getParentFile();
        //判断目录是否存在，不在创建
        if(parent!=null&&!parent.exists()){
            parent.mkdirs();
        }
        file.createNewFile();

        OutputStream outpuStream = new FileOutputStream(file);
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = fileInputStream.read(bytes)) != -1) {
            outpuStream.write(bytes, 0, read);
        }

        outpuStream.flush();
        outpuStream.close();

        fileInputStream.close();

        return Response.status(Response.Status.OK)
                .entity("Upload Success!").build();
    }
}
