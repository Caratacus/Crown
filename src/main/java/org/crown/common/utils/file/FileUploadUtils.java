package org.crown.common.utils.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.crown.common.utils.DateUtils;
import org.crown.common.utils.Md5Utils;
import org.crown.common.utils.StringUtils;
import org.crown.framework.exception.Crown2Exception;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传工具类
 *
 * @author Crown
 */
@Slf4j
public class FileUploadUtils {

    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    private static int counter = 0;

    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws IOException 比如读写文件出错时
     */
    public static String upload(String baseDir, MultipartFile file, String[] allowedExtension) throws IOException {
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "文件名过长，文件名最大长度为：" + DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);
        String fileName = extractFilename(file);
        File desc = getAbsoluteFile(baseDir, fileName);
        BufferedOutputStream stream = null;
        try {
            byte[] bytes = file.getBytes();
            stream = new BufferedOutputStream(new FileOutputStream(desc));
            stream.write(bytes);
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "上传文件出现错误！", e);
        } finally {
            IOUtils.closeQuietly(stream);
        }
        return fileName;
    }

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String extension = getExtension(file);
        filename = DateUtils.datePath() + "/" + encodingFilename(filename) + "." + extension;
        return filename;
    }

    private static File getAbsoluteFile(String uploadDir, String filename) throws IOException {
        File desc = new File(uploadDir + File.separator + filename);

        if (!desc.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            //noinspection ResultOfMethodCallIgnored
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 编码文件名
     */
    private static String encodingFilename(String filename) {
        filename = filename.replace("_", " ");
        filename = Md5Utils.hash(filename + System.nanoTime() + counter++);
        return filename;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     */
    public static void assertAllowed(MultipartFile file, String[] allowedExtension) {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "上传的文件大小超出限制的文件大小！允许的文件最大大小是：" + DEFAULT_MAX_SIZE / 1024 / 1024 + "MB！");
        }

        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "不支持该类型文件上传，当前文件类型为：[" + extension + "]！允许的文件类型为：" + Arrays.toString(allowedExtension));
        }

    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypes.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

}
